package de.androidcrypto.talktoyourcreditcard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.devnied.emvnfccard.enums.CommandEnum;
import com.github.devnied.emvnfccard.exception.CommunicationException;
import com.github.devnied.emvnfccard.iso7816emv.EmvTags;
import com.github.devnied.emvnfccard.iso7816emv.impl.DefaultTerminalImpl;
import com.github.devnied.emvnfccard.utils.CommandApdu;
import com.github.devnied.emvnfccard.utils.TlvUtil;
import com.payneteasy.tlv.BerTag;
import com.payneteasy.tlv.BerTlv;
import com.payneteasy.tlv.BerTlvParser;
import com.payneteasy.tlv.BerTlvs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class MainActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private final String TAG = "MainAct";
    private com.google.android.material.textfield.TextInputEditText etData, etLog;
    private View loadingLayout;
    private NfcAdapter mNfcAdapter;

    private String outputString = ""; // used for the UI output
    private String exportString = ""; // used for exporting the log to a text file
    private String exportStringFileName = "emv.html";
    private final String stepSeparatorString = "*********************************";
    private final String lineSeparatorString = "---------------------------------";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        etData = findViewById(R.id.etData);
        etLog = findViewById(R.id.etLog);
        loadingLayout = findViewById(R.id.loading_layout);

        context = getApplicationContext();

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    /**
     * section for NFC
     */

    /**
     * This method is run in another thread when a card is discovered
     * This method cannot cannot direct interact with the UI Thread
     * Use `runOnUiThread` method to change the UI from this method
     *
     * @param tag discovered tag
     */

    @Override
    public void onTagDiscovered(Tag tag) {
        clearData();
        Log.d(TAG, "NFC tag discovered");
        writeToUiAppend("NFC tag discovered");
        playSinglePing();
        setLoadingLayoutVisibility(true);
        byte[] tagId = tag.getId();
        writeToUiAppend("TagId: " + bytesToHexNpe(tagId));
        String[] techList = tag.getTechList();
        writeToUiAppend("TechList found with these entries:");
        boolean isoDepInTechList = false;
        for (String s : techList) {
            writeToUiAppend(s);
            if (s.equals("android.nfc.tech.IsoDep")) isoDepInTechList = true;
        }
        // proceed only if tag has IsoDep in the techList
        if (isoDepInTechList) {
            IsoDep nfc = null;
            nfc = IsoDep.get(tag);
            if (nfc != null) {
                try {
                    nfc.connect();
                    Log.d(TAG, "connection with card success");
                    writeToUiAppend("connection with card success");
                    // here we are going to start our journey through the card

                    printStepHeader(0, "our journey begins");
                    writeToUiAppend(etData, "00 reading of the card started");

                    writeToUiAppend("increase IsoDep timeout for long lasting reading");
                    writeToUiAppend("timeout old: " + nfc.getTimeout() + " ms");
                    nfc.setTimeout(10000);
                    writeToUiAppend("timeout new: " + nfc.getTimeout() + " ms");


                    /**
                     * step 1 code start
                     */

                    printStepHeader(1, "select PPSE");
                    byte[] PPSE = "2PAY.SYS.DDF01".getBytes(StandardCharsets.UTF_8); // PPSE
                    byte[] selectPpseCommand = selectApdu(PPSE);
                    byte[] selectPpseResponse = nfc.transceive(selectPpseCommand);
                    writeToUiAppend("01 select PPSE command  length " + selectPpseCommand.length + " data: " + bytesToHexNpe(selectPpseCommand));
                    writeToUiAppend("01 select PPSE response length " + selectPpseResponse.length + " data: " + bytesToHexNpe(selectPpseResponse));
                    writeToUiAppend(etData, "01 select PPSE completed");
                    writeToUiAppend(prettyPrintDataToString(selectPpseResponse));

                    byte[] selectPpseResponseOk = checkResponse(selectPpseResponse);
                    // proceed only when te do have a positive read result = 0x'9000' at the end of response data
                    if (selectPpseResponseOk != null) {

                        /**
                         * step 2 code start
                         */

                        //writeToUiAppend("");
                        printStepHeader(2, "search applications on card");
                        writeToUiAppend("02 analyze select PPSE response and search for tag 0x4F (applications on card)");

                        BerTlvParser parser = new BerTlvParser();
                        BerTlvs tlv4Fs = parser.parse(selectPpseResponseOk);
                        // find all entries for tag 0x4f
                        List<BerTlv> tag4fList = tlv4Fs.findAll(new BerTag(0x4F));
                        if (tag4fList.size() < 1) {
                            writeToUiAppend("there is no tag 0x4F available, stopping here");
                            startEndSequence(nfc);
                        }
                        writeToUiAppend("Found tag 0x4F " + tag4fList.size() + (tag4fList.size() == 1 ? " time:" : " times:"));
                        ArrayList<byte[]> aidList = new ArrayList<>();
                        for (int i4f = 0; i4f < tag4fList.size(); i4f++) {
                            BerTlv tlv4f = tag4fList.get(i4f);
                            byte[] tlv4fBytes = tlv4f.getBytesValue();
                            aidList.add(tlv4fBytes);
                            writeToUiAppend("application Id (AID): " + bytesToHexNpe(tlv4fBytes));
                        }
                        writeToUiAppend(etData, "02 analyze select PPSE response completed");

                        /**
                         * step 2 code end
                         */

                    } else {
                        // if (isoDepInTechList) {
                        writeToUiAppend("The discovered NFC tag does not have an IsoDep interface.");
                    }

                    /**
                     * step 1 code end
                     */

                    printStepHeader(99, "our journey ends");
                    writeToUiAppend(etData, "99 reading of the card completed");
                    vibrate();
                } catch (IOException e) {
                    writeToUiAppend("connection with card failure");
                    writeToUiAppend(e.getMessage());
                    // throw new RuntimeException(e);
                    startEndSequence(nfc);
                    return;
                }
            }
        } else {
            // if (isoDepInTechList) {
            writeToUiAppend("The discovered NFC tag does not have an IsoDep interface.");
        }
        // final cleanup
        playDoublePing();
        writeToUiFinal(etLog);
        setLoadingLayoutVisibility(false);
    }


    /**
     * section for emv reading
     */

    /**
     * step 1 code start
     */

    /**
     * build a select apdu command
     *
     * @param data
     * @return
     */
    private byte[] selectApdu(@NonNull byte[] data) {
        byte[] commandApdu = new byte[6 + data.length];
        commandApdu[0] = (byte) 0x00;  // CLA
        commandApdu[1] = (byte) 0xA4;  // INS
        commandApdu[2] = (byte) 0x04;  // P1
        commandApdu[3] = (byte) 0x00;  // P2
        commandApdu[4] = (byte) (data.length & 0x0FF);       // Lc
        System.arraycopy(data, 0, commandApdu, 5, data.length);
        commandApdu[commandApdu.length - 1] = (byte) 0x00;  // Le
        return commandApdu;
    }

    /**
     * step 1 code end
     */

    /**
     * add blanks to a string on right side up to a length of len
     * if the data.length >= len one character is deleted to get minimum one blank
     *
     * @param data
     * @param len
     * @return
     */
    private String trimStringRight(String data, int len) {
        if (data.length() >= len) {
            data = data.substring(0, (len - 1));
        }
        while (data.length() < len) {
            data = data + " ";
        }
        return data;
    }

    /**
     * checks if the response has an 0x'9000' at the end means success
     * and the method returns the data without 0x'9000' at the end
     * if any other trailing bytes show up the method returns NULL
     *
     * @param data
     * @return
     */
    private byte[] checkResponse(@NonNull byte[] data) {
        // simple sanity check
        if (data.length < 5) {
            return null;
        } // not ok
        int status = ((0xff & data[data.length - 2]) << 8) | (0xff & data[data.length - 1]);
        if (status != 0x9000) {
            return null;
        } else {
            return Arrays.copyOfRange(data, 0, data.length - 2);
        }
    }

    /**
     * section for conversion utils
     */

    /**
     * converts a byte array to a hex encoded string
     * This method is Null Pointer Exception (NPE) safe
     *
     * @param bytes
     * @return hex encoded string
     */
    public static String bytesToHexNpe(byte[] bytes) {
        if (bytes != null) {
            StringBuffer result = new StringBuffer();
            for (byte b : bytes)
                result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * converts a byte array to a hex encoded string
     * This method is Null Pointer Exception (NPE) safe
     *
     * @param bytes
     * @return hex encoded string with a blank after each value
     */
    public static String bytesToHexBlankNpe(byte[] bytes) {
        if (bytes == null) return "";
        StringBuffer result = new StringBuffer();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1)).append(" ");
        return result.toString();
    }

    /**
     * converts a hex encoded string to a byte array
     *
     * @param str
     * @return
     */
    public static byte[] hexToBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }

    /**
     * converts a byte to int
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        return (int) b & 0xFF;
    }

    public static int intFromByteArray(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }

    /**
     * converts a byte to its hex string representation
     *
     * @param data
     * @return
     */
    public static String byteToHex(byte data) {
        int hex = data & 0xFF;
        return Integer.toHexString(hex);
    }

    /**
     * converts an integer to a byte array
     *
     * @param value
     * @return
     */
    public static byte[] intToByteArray(int value) {
        return new BigInteger(String.valueOf(value)).toByteArray();
    }

    /**
     * splits a byte array in chunks
     *
     * @param source
     * @param chunksize
     * @return a List<byte[]> with sets of chunksize
     */
    private static List<byte[]> divideArray(byte[] source, int chunksize) {
        List<byte[]> result = new ArrayList<byte[]>();
        int start = 0;
        while (start < source.length) {
            int end = Math.min(source.length, start + chunksize);
            result.add(Arrays.copyOfRange(source, start, end));
            start += chunksize;
        }
        return result;
    }

    /**
     * section for NFC
     */

    private void showWirelessSettings() {
        Toast.makeText(this, "You need to enable NFC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled())
                showWirelessSettings();
            Bundle options = new Bundle();
            // Work around for some broken Nfc firmware implementations that poll the card too fast
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250);
            // Enable ReaderMode for all types of card and disable platform sounds
            // the option NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK is NOT set
            // to get the data of the tag afer reading
            mNfcAdapter.enableReaderMode(this,
                    this,
                    NfcAdapter.FLAG_READER_NFC_A |
                            NfcAdapter.FLAG_READER_NFC_B |
                            NfcAdapter.FLAG_READER_NFC_F |
                            NfcAdapter.FLAG_READER_NFC_V |
                            NfcAdapter.FLAG_READER_NFC_BARCODE |
                            NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                    options);
        }
    }

    /**
     * important is the disabling of the ReaderMode when activity is pausing
     */

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableReaderMode(this);
    }

    /**
     * section for UI
     */

    /**
     * shows a progress bar as long as the reading lasts
     *
     * @param isVisible
     */

    private void setLoadingLayoutVisibility(boolean isVisible) {
        runOnUiThread(() -> {
            if (isVisible) {
                loadingLayout.setVisibility(View.VISIBLE);
            } else {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * vibrate
     */
    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, 10));
        } else {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
        }
    }

    /**
     * Sound files downloaded from Material Design Sounds
     * https://m2.material.io/design/sound/sound-resources.html
     */
    private void playSinglePing() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.notification_decorative_02);
        mp.start();
    }

    private void playDoublePing() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.notification_decorative_01);
        mp.start();
    }

    private void startEndSequence(IsoDep nfc) {
        playDoublePing();
        writeToUiFinal(etLog);
        setLoadingLayoutVisibility(false);
        vibrate();
        try {
            nfc.close();
        } catch (IOException e) {
            // throw new RuntimeException(e);
        }
        return;
    }

    /**
     * prints a nice header for each step
     *
     * @param step
     * @param message
     */
    private void printStepHeader(int step, String message) {
        // message should not extend 29 characters, longer messages will get trimmed
        String emptyMessage = "                                 ";
        StringBuilder sb = new StringBuilder();
        sb.append(outputString); // has already a line feed at the end
        sb.append("").append("\n");
        sb.append(stepSeparatorString).append("\n");
        sb.append("************ step ").append(String.format("%02d", step)).append(" ************").append("\n");
        sb.append("* ").append((message + emptyMessage).substring(0, 29)).append(" *").append("\n");
        sb.append(stepSeparatorString).append("\n");
        outputString = sb.toString();
    }

    /**
     * used for printing the card responses in a human readable format to a string
     *
     * @param responseData
     * @return
     */
    private String prettyPrintDataToString(byte[] responseData) {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------").append("\n");
        sb.append(trimLeadingLineFeeds(TlvUtil.prettyPrintAPDUResponse(responseData))).append("\n");
        sb.append("------------------------------------").append("\n");
        return sb.toString();
    }

    /**
     * trim leading line feeds if existing
     *
     * @param input
     * @return
     */
    public static String trimLeadingLineFeeds(String input) {
        String[] output = input.split("^\\n+", 2);
        return output.length > 1 ? output[1] : output[0];
    }

    private void clearData() {
        runOnUiThread(() -> {
            outputString = "";
            exportString = "";
            etData.setText("");
            etLog.setText("");
        });
    }

    private void writeToUiAppend(String message) {
        //System.out.println(message);
        outputString = outputString + message + "\n";
    }

    private void writeToUiAppend(final TextView textView, String message) {
        runOnUiThread(() -> {
            if (TextUtils.isEmpty(textView.getText().toString())) {
                if (textView == (TextView) etLog) {
                } else {
                    textView.setText(message);
                }
            } else {
                String newString = textView.getText().toString() + "\n" + message;
                if (textView == (TextView) etLog) {
                } else {
                    textView.setText(newString);
                }
            }
        });
    }

    private void writeToUiFinal(final TextView textView) {
        if (textView == (TextView) etLog) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(outputString);
                    System.out.println(outputString); // print the data to console
                }
            });
        }
    }

    private void provideTextViewDataForExport(TextView textView) {
        exportString = textView.getText().toString();
    }

    private void writeToUiToast(String message) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(),
                    message,
                    Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * section OptionsMenu export text file methods
     */

    private void exportTextFile() {
        provideTextViewDataForExport(etLog);
        if (exportString.isEmpty()) {
            writeToUiToast("Scan a tag first before writing files :-)");
            return;
        }
        writeStringToExternalSharedStorage();
    }

    private void writeStringToExternalSharedStorage() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        // boolean pickerInitialUri = false;
        // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        // get filename from edittext
        String filename = exportStringFileName;
        // sanity check
        if (filename.equals("")) {
            writeToUiToast("scan a tag before writing the content to a file :-)");
            return;
        }
        intent.putExtra(Intent.EXTRA_TITLE, filename);
        selectTextFileActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> selectTextFileActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent resultData = result.getData();
                        // The result data contains a URI for the document or directory that
                        // the user selected.
                        Uri uri = null;
                        if (resultData != null) {
                            uri = resultData.getData();
                            // Perform operations on the document using its URI.
                            try {
                                // get file content from edittext
                                String fileContent = exportString;
                                System.out.println("## data to write: " + exportString);
                                writeTextToUri(uri, fileContent);
                                writeToUiToast("file written to external shared storage: " + uri.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                                writeToUiToast("ERROR: " + e.toString());
                                return;
                            }
                        }
                    }
                }
            });

    private void writeTextToUri(Uri uri, String data) throws IOException {
        try {
            System.out.println("** data to write: " + data);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().getContentResolver().openOutputStream(uri));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            System.out.println("Exception File write failed: " + e.toString());
        }
    }

    /**
     * options menu show licenses
     */

    // run: displayLicensesAlertDialog();
    // display licenses dialog see: https://bignerdranch.com/blog/open-source-licenses-and-android/
    private void displayLicensesAlertDialog() {
        WebView view = (WebView) LayoutInflater.from(this).inflate(R.layout.dialog_licenses, null);
        view.loadUrl("file:///android_asset/open_source_licenses.html");
        android.app.AlertDialog mAlertDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
        mAlertDialog = new android.app.AlertDialog.Builder(this, R.style.Theme_TalkToYourCreditCard)
                .setTitle(getString(R.string.action_licenses))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    /**
     * section for OptionsMenu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        MenuItem mCopyData = menu.findItem(R.id.action_copy_data);
        mCopyData.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("BasicNfcEmvReader", etLog.getText());
                clipboard.setPrimaryClip(clip);
                // show toast only on Android versions < 13
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                    Toast.makeText(getApplicationContext(), "copied", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        MenuItem mExportTextFile = menu.findItem(R.id.action_export_text_file);
        mExportTextFile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "mExportTextFile");
                exportTextFile();
                return false;
            }
        });

        MenuItem mLicenses = menu.findItem(R.id.action_licenses);
        mLicenses.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "mLicenses");
                displayLicensesAlertDialog();
                return false;
            }
        });

        MenuItem mAbout = menu.findItem(R.id.action_about);
        mAbout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "mLicenses");
                CharSequence description = "This is the basic app for the Talk to your Credit Card application";
                View aboutPage = new AboutPage(context)
                        .isRTL(false)
                        .setDescription(getString(R.string.app_description))
                        //.setCustomFont(String) // or Typeface
                        .setImage(R.drawable.ic_launcher_playstore)
                        .addItem(new Element().setTitle("Version 1.0"))
                        .addGroup("Connect with us")
                        .addEmail("androidcrypto@gmx.de")
                        .addWebsite("https://medium.com/@androidcrypto")
                        .addGitHub("androidcrypto")
                        .addItem(getCopyRightsElement())
                        .create();
                setContentView(aboutPage);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setAutoApplyIconTint(true);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}