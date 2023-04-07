# Talk to your Credit Card part 0

This app shows how to talk with a Credit Card and get information's like the Credit Card number, 
the card's expiration date and some other data.

A general description can be found in an article series on medium.com:

- general overview about the app
- ask the card which applications are available on the card ("select PPSE")
- analyze the card's response and identify one or more of the application number or application id ("AID")
- select one application on the card to work with ("select AID") [or iterate through the applications  and run the following steps for each application]
- analyze the card's response to find out what data the card needs to proceed (find the "processing options data object list" (PDOL))
- analyze the card's response and get the content of the element "application file locator" (AFL) list
- read all files given in the AFL list and find the file where there are the elements "Application Primary Account Number" and "Application Expiration Date"
- print out the "Application Primary Account Number" ("PAN") = card number and "Application Expiration Date" = expiration date of the card.

The app uses the NFC **ReaderMode** to get access to the card.

There are apps available for each step in my GitHub repository - the naming shows the step that has the code including this step.

This is the app for the general overview, means it is the basic app that has a very limited functionality: it detects the presence of a Credit Card 
near the device's NFC reader: **TalkToYourCreditCardPart0**.

The app is tested on real Android devices running Android versions 5.0.1, 8.0 and 13.

There are 3 dependencies necessary for the app to run:

build.gradle:
```plaintext
    // parsing BER-TLV encoded data, e.g. a credit card
    // source: https://github.com/evsinev/ber-tlv
    implementation 'com.payneteasy:ber-tlv:1.0-11'
    
    // pretty printing of card's responses
    // source: https://github.com/devnied/EMV-NFC-Paycard-Enrollment
    implementation 'com.github.devnied.emvnfccard:library:3.0.1'
    
    // implementing an about page
    implementation 'io.github.medyo:android-about-page:2.0.0'
```

These are the steps to read a payment card, it is a kind of "question & answer" workflow:
- ask the card which applications are available on the card ("select PPSE")
- analyze the card's response and identify one or more of the application number or application id ("AID")
- select one application on the card to work with ("select AID") [or iterate through the applications  and run the following steps for each application]
- analyze the card's response to find out what data the card needs to proceed (find the "processing options data object list" (PDOL))
- analyze the card's response and get the content of the element "application file locator" (AFL) list
- read all files given in the AFL list and find the file where there are the elements "Application Primary Account Number" and "Application Expiration Date"
- print out the "Application Primary Account Number" ("PAN") = card number and "Application Expiration Date" = expiration date of the card.

![step 0: after reading a Credit Card](docs/app00.png?raw=true)

In AndroidManifest.xml grant these permissions:
```plaintext
    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.VIBRATE" />
```

Dependencies in build.gradle (app):
```plaintext
    // parsing BER-TLV encoded data, e.g. a credit card
    // source: https://github.com/evsinev/ber-tlv
    implementation 'com.payneteasy:ber-tlv:1.0-11'

    // pretty printing of card's responses
    // source: https://github.com/devnied/EMV-NFC-Paycard-Enrollment
    implementation 'com.github.devnied.emvnfccard:library:3.0.1'
```
## Library ber-tlv

Source code: https://github.com/evsinev/ber-tlv

License:  Apache-2.0 license

## Library: EMV-NFC-Paycard-Enrollment

Source code: https://github.com/devnied/EMV-NFC-Paycard-Enrollment

License: Apache-2.0 license

## Library: Android About Page

Source code: https://github.com/medyo/android-about-page

License: The MIT License (MIT)

## Sound resources for pings: https://m2.material.io/design/sound/sound-resources.html

Licensed under Attribution 4.0 International (CC BY 4.0): https://creativecommons.org/licenses/by/4.0/legalcode

