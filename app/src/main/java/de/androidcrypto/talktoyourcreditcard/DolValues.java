package de.androidcrypto.talktoyourcreditcard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DolValues {

    private List<DolTag> dolList = new ArrayList<>();
    // used in PDOL
    //private final DolTag t9f66 = setTag(new byte[]{(byte) 0x9f, (byte) 0x66}, "Terminal Transaction Qualifiers", hexBlankToBytes("A0 00 00 00")); // runs on all my cards but not returns not all afl
    // test for visa comd, seems to be the better option ()
    // online decoder: https://paymentcardtools.com/emv-tag-decoders/ttq
    //private final DolTag t9f66 = setTag(new byte[]{(byte) 0x9f, (byte) 0x66}, "Terminal Transaction Qualifiers", hexBlankToBytes("B7 60 40 00")); // does not run with Lloyds Visa
    private final DolTag t9f66 = setTag(new byte[]{(byte) 0x9f, (byte) 0x66}, "Terminal Transaction Qualifiers", hexBlankToBytes("27 00 00 00"));

/*
https://stackoverflow.com/a/68996365/8166854
Your Terminal Transaction Qualifier byte 1 bit 1 is set to zero, meaning "Offline Data Authentication for Online Authorizations
not supported". Try setting it to 1: B6 60 40 00 --> B7 60 40 00.
I was having the same issue and this was enough to receive an AFL.
 */

    //private final DolTag t9f66 = setTag(new byte[]{(byte) 0x9f, (byte) 0x66}, "Terminal Transaction Qualifiers", hexBlankToBytes("F0 20 40 00")); // this fails on DKB debit card
    private final DolTag t9f02 = setTag(new byte[]{(byte) 0x9f, (byte) 0x02}, "Transaction Amount", hexBlankToBytes("00 00 00 00 10 00")); // 00 00 00 00 10 00
    private final DolTag t9f03 = setTag(new byte[]{(byte) 0x9f, (byte) 0x03}, "Amount, Other (Numeric)", hexBlankToBytes("00 00 00 00 00 00"));
    private final DolTag t9f1a = setTag(new byte[]{(byte) 0x9f, (byte) 0x1a}, "Terminal Country Code", hexBlankToBytes("09 78")); // eur
    private final DolTag t95 = setTag(new byte[]{(byte) 0x95}, "Terminal Verificat.Results", hexBlankToBytes("00 00 00 00 00"));
    private final DolTag t5f2a = setTag(new byte[]{(byte) 0x5f, (byte) 0x2a}, "Transaction Currency Code", hexBlankToBytes("09 78")); // eur
    private final DolTag t9a = setTag(new byte[]{(byte) 0x9a}, "Transaction Date", hexBlankToBytes("23 03 01"));
    private final DolTag t9c = setTag(new byte[]{(byte) 0x9c}, "Transaction Type", hexBlankToBytes("00"));
    private final DolTag t9f37 = setTag(new byte[]{(byte) 0x9f, (byte) 0x37}, "Unpredictable Number", hexBlankToBytes("38 39 30 31"));

    // used in CDOL1
    private final DolTag t9f35 = setTag(new byte[]{(byte) 0x9f, (byte) 0x35}, "Terminal Type", hexBlankToBytes("22"));
    private final DolTag t9f45 = setTag(new byte[]{(byte) 0x9f, (byte) 0x45}, "Data Authentication Code", hexBlankToBytes("00 00"));
    private final DolTag t9f4c = setTag(new byte[]{(byte) 0x9f, (byte) 0x4c}, "ICC Dynamic Number", hexBlankToBytes("00 00 00 00 00 00 00 00"));
    private final DolTag t9f34 = setTag(new byte[]{(byte) 0x9f, (byte) 0x34}, "Terminal Transaction Qualifiers", hexBlankToBytes("00 00 00"));
    private final DolTag t9f21 = setTag(new byte[]{(byte) 0x9f, (byte) 0x21}, "Transaction Time (HHMMSS)", hexBlankToBytes("11 10 09"));
    private final DolTag t9f7c = setTag(new byte[]{(byte) 0x9f, (byte) 0x7c}, "Merchant Custom Data", hexBlankToBytes("00 00 00 00 00 00 00 00 00 00 00 00 00 00"));
    private final DolTag t00 = setTag(new byte[]{(byte) 0x00}, "Tag not found", hexBlankToBytes("00"));

/*
I/System.out:          8C 27 -- Card Risk Management Data Object List 1 (CDOL1)
I/System.out:                9F 02 06 -- Amount, Authorised (Numeric)
I/System.out:                9F 03 06 -- Amount, Other (Numeric)
I/System.out:                9F 1A 02 -- Terminal Country Code
I/System.out:                95 05 -- Terminal Verification Results (TVR)
I/System.out:                5F 2A 02 -- Transaction Currency Code
I/System.out:                9A 03 -- Transaction Date
I/System.out:                9C 01 -- Transaction Type
I/System.out:                9F 37 04 -- Unpredictable Number
I/System.out:                9F 35 01 -- Terminal Type
I/System.out:                9F 45 02 -- Data Authentication Code
I/System.out:                9F 4C 08 -- ICC Dynamic Number
I/System.out:                9F 34 03 -- Cardholder Verification (CVM) Results
I/System.out:                9F 21 03 -- Transaction Time (HHMMSS)
I/System.out:                9F 7C 14 -- Merchant Custom Data
CDOL1 MC
0x00, 0x00, 0x00, 0x00, 0x01, 0x00, // amount ok
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // other amount ok
                0x06, 0x42, // terminal country ok
                0x00, 0x00, 0x00, 0x00, 0x00, // tvr terminal ok
                0x09, 0x46, // currency code ok
                0x20, 0x08, 0x23, // transaction date ok, todo fix date ?
                0x00, // transaction type ok
                0x11, 0x22, 0x33, 0x44, // UN ok
                0x22, // terminal type ok
                0x00, 0x00,// data auth code ok
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // icc dynamic ok
                0x00, 0x00, 0x00, // cvm results ok
                0x11, 0x10, 0x09, // Transaction Time (HHMMSS) added
                //0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  // 8 cut
                //0x54, 0x11, // 2 merchant category cut
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
 */



/*
  9f66 04 Terminal Transaction Qualifiers : B6 60 40 00
            9f02 06 Transaction Amount :              00 00 00 01 00 00
            9f03 06 Amount, Other (Numeric) always:   00 00 00 00 00 00
            9f1a 02 Terminal Country Code :           08 26              UK
  95   05 Terminal Verificat.Results alway: 00 00 00 00 00
            5f2a 02 Transaction Currency Code :       08 26
            9a   03 Transaction Date :                23 03 03
            9c   01 Transaction Type :                00
            9f37 04 Unpredictable Number :            38 39 30 31
*/
    public DolValues() {
        // empty constructor to fill the emvTagList
    }

    public String getDolName(byte[] tagByte) {
        for (int i = 0; i < dolList.size(); i++) {
            DolTag dolTag = dolList.get(i);
            if (Arrays.equals(dolTag.getTag(), tagByte)) {
                return dolTag.getTagName();
            }
        }
        return t00.getTagName(); // default, entry not found
    }

    public byte[] getDolValue(byte[] tagByte) {
        for (int i = 0; i < dolList.size(); i++) {
            DolTag dolTag = dolList.get(i);
            if (Arrays.equals(dolTag.getTag(), tagByte)) {
                return dolTag.getDefaultValue();
            }
        }
        return null; // default, entry not found
    }

    private DolTag setTag(byte[] tagByte, String tagName, byte[] tagValueByte) {
        DolTag dolTag = new DolTag(tagByte, tagName, tagValueByte);
        dolList.add(dolTag);
        return dolTag;
    }

    private static byte[] hexBlankToBytes(String str) {
        str = str.replaceAll(" ", "");
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }
}
