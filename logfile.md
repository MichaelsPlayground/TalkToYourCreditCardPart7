# Complete log file after reading a Credit Card

## Log file for a MasterCard
```plaintext
NFC tag discovered
TagId: 028eedb17074b0
TechList found with these entries:
android.nfc.tech.IsoDep
android.nfc.tech.NfcA
connection with card success

*********************************
************ step 00 ************
* our journey begins            *
*********************************
increase IsoDep timeout for long lasting reading
timeout old: 2000 ms
timeout new: 10000 ms

*********************************
************ step 01 ************
* select PPSE                   *
*********************************
01 select PPSE command  length 20 data: 00a404000e325041592e5359532e444446303100
01 select PPSE response length 64 data: 6f3c840e325041592e5359532e4444463031a52abf0c2761254f07a000000004101050104465626974204d6173746572436172648701019f0a04000101019000
------------------------------------
6F 3C -- File Control Information (FCI) Template
      84 0E -- Dedicated File (DF) Name
            32 50 41 59 2E 53 59 53 2E 44 44 46 30 31 (BINARY)
      A5 2A -- File Control Information (FCI) Proprietary Template
            BF 0C 27 -- File Control Information (FCI) Issuer Discretionary Data
                     61 25 -- Application Template
                           4F 07 -- Application Identifier (AID) - card
                                 A0 00 00 00 04 10 10 (BINARY)
                           50 10 -- Application Label
                                 44 65 62 69 74 20 4D 61 73 74 65 72 43 61 72 64 (=Debit MasterCard)
                           87 01 -- Application Priority Indicator
                                 01 (BINARY)
                           9F 0A 04 -- [UNKNOWN TAG]
                                    00 01 01 01 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------


*********************************
************ step 02 ************
* search applications on card   *
*********************************
02 analyze select PPSE response and search for tag 0x4F (applications on card)
Found tag 0x4F 1 time:
application Id (AID): a0000000041010


*********************************
************ step 03 ************
* select application by AID     *
*********************************
03 select application by AID a0000000041010 (number 1)

03 select AID command  length 13 data: 00a4040007a000000004101000
03 select AID response length 86 data: 6f528407a0000000041010a54750104465626974204d6173746572436172649f12104465626974204d6173746572436172648701019f1101015f2d046465656ebf0c119f0a04000101019f6e07028000003030009000
------------------------------------
6F 52 -- File Control Information (FCI) Template
      84 07 -- Dedicated File (DF) Name
            A0 00 00 00 04 10 10 (BINARY)
      A5 47 -- File Control Information (FCI) Proprietary Template
            50 10 -- Application Label
                  44 65 62 69 74 20 4D 61 73 74 65 72 43 61 72 64 (=Debit MasterCard)
            9F 12 10 -- Application Preferred Name
                     44 65 62 69 74 20 4D 61 73 74 65 72 43 61 72 64 (=Debit MasterCard)
            87 01 -- Application Priority Indicator
                  01 (BINARY)
            9F 11 01 -- Issuer Code Table Index
                     01 (NUMERIC)
            5F 2D 04 -- Language Preference
                     64 65 65 6E (=deen)
            BF 0C 11 -- File Control Information (FCI) Issuer Discretionary Data
                     9F 0A 04 -- [UNKNOWN TAG]
                              00 01 01 01 (BINARY)
                     9F 6E 07 -- Visa Low-Value Payment (VLP) Issuer Authorisation Code
                              02 80 00 00 30 30 00 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------


*********************************
************ step 04 ************
* search for tag 0x9F38         *
*********************************
04 search for tag 0x9F38 in the selectAid response
Available predefined values for PDOL and CDOL
List of predefined tag and values for PDOL and CDOL
Tag  Name                            Value
-------------------------------------------------
9f66 Terminal Transaction Qualifiers 27000000
9f66 Terminal Transaction Qualifiers 27000000
9f66 Terminal Transaction Qualifiers b7604000
9f66 Terminal Transaction Qualifiers a0000000
9f66 Terminal Transaction Qualifiers f0204000
9f02 Transaction Amount              000000001000
9f03 Amount, Other (Numeric)         000000000000
9f1a Terminal Country Code           0978
95   Terminal Verificat.Results      0000000000
5f2a Transaction Currency Code       0978
9a   Transaction Date                230301
9c   Transaction Type                00
9f37 Unpredictable Number            38393031
9f35 Terminal Type                   22
9f45 Data Authentication Code        0000
9f4c ICC Dynamic Number              0000000000000000
9f34 Terminal Transaction Qualifiers 000000
9f21 Transaction Time (HHMMSS)       111009
9f7c Merchant Custom Data            0000000000000000000000000000
00   Tag not found                   00


### processing the MasterCard path ###

No PDOL found in the selectAid response, generating a 'null' PDOL

The card is requesting 0 tags in the PDOL

Tag  Tag Name                        Length Value
-----------------------------------------------------
     no PDOL provided, returning an empty command
-----------------------------------------------------

*********************************
************ step 05 ************
* get the processing options    *
*********************************
05 get the processing options  command length: 8 data: 80a8000002830000
05 get the processing options response length: 22 data: 771282021980940c0801010010010101200102009000
------------------------------------
77 12 -- Response Message Template Format 2
      82 02 -- Application Interchange Profile
            19 80 (BINARY)
      94 0C -- Application File Locator (AFL)
            08 01 01 00 10 01 01 01 20 01 02 00 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

workflow c)
the response is of type 'Response Message Template Format 2' [tag 0x77]
found tag 0x77 in the gpoResponse
found 'AFL' [tag 0x94] in the response of type 'Response Message Template Format 2' [tag 0x77]
found tag 0x94 in the gpoResponse length: 12 data: 080101001001010120010200


*********************************
************ step 06 ************
* read files & search PAN       *
*********************************
06 read the files from card and search for PAN & Expiration date

The AFL contains 3 entries to read
for SFI 8 we read 1 record
readRecord  command length: 5 data: 00b2010c00
readRecord response length: 121 data: 70759f6c0200019f6206000000000f009f63060000000000fe563442353337353035303030303136303131305e202f5e323430333232313237393433323930303030303030303030303030303030309f6401029f65020f009f660200fe9f6b135375050000160110d24032210000000000000f9f6701029000
------------------------------------
70 75 -- Record Template (EMV Proprietary)
      9F 6C 02 -- Mag Stripe Application Version Number (Card)
               00 01 (BINARY)
      9F 62 06 -- Track 1 bit map for CVC3
               00 00 00 00 0F 00 (BINARY)
      9F 63 06 -- Track 1 bit map for UN and ATC
               00 00 00 00 00 FE (BINARY)
      56 34 -- Track 1 Data
            42 35 33 37 35 30 35 30 30 30 30 31 36 30 31 31
            30 5E 20 2F 5E 32 34 30 33 32 32 31 32 37 39 34
            33 32 39 30 30 30 30 30 30 30 30 30 30 30 30 30
            30 30 30 30 (BINARY)
      9F 64 01 -- Track 1 number of ATC digits
               02 (BINARY)
      9F 65 02 -- Track 2 bit map for CVC3
               0F 00 (BINARY)
      9F 66 02 -- Terminal Transaction Qualifiers
               00 FE (BINARY)
      9F 6B 13 -- Track 2 Data
               53 75 05 00 00 16 01 10 D2 40 32 21 00 00 00 00
               00 00 0F (BINARY)
      9F 67 01 -- Track 2 number of ATC digits
               02 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

for SFI 10 we read 1 record
readRecord  command length: 5 data: 00b2011400
readRecord response length: 171 data: 7081a69f420209785f25032203015f24032403315a0853750500001601105f3401009f0702ffc09f080200028c279f02069f03069f1a0295055f2a029a039c019f37049f35019f45029f4c089f34039f21039f7c148d0c910a8a0295059f37049f4c088e0e000000000000000042031e031f039f0d05b4508400009f0e0500000000009f0f05b4708480005f280202809f4a018257135375050000160110d24032212794329000000f9000
------------------------------------
70 81 A6 -- Record Template (EMV Proprietary)
         9F 42 02 -- Application Currency Code
                  09 78 (NUMERIC)
         5F 25 03 -- Application Effective Date
                  22 03 01 (NUMERIC)
         5F 24 03 -- Application Expiration Date
                  24 03 31 (NUMERIC)
         5A 08 -- Application Primary Account Number (PAN)
               53 75 05 00 00 16 01 10 (NUMERIC)
         5F 34 01 -- Application Primary Account Number (PAN) Sequence Number
                  00 (NUMERIC)
         9F 07 02 -- Application Usage Control
                  FF C0 (BINARY)
         9F 08 02 -- Application Version Number - card
                  00 02 (BINARY)
         8C 27 -- Card Risk Management Data Object List 1 (CDOL1)
               9F 02 06 -- Amount, Authorised (Numeric)
               9F 03 06 -- Amount, Other (Numeric)
               9F 1A 02 -- Terminal Country Code
               95 05 -- Terminal Verification Results (TVR)
               5F 2A 02 -- Transaction Currency Code
               9A 03 -- Transaction Date
               9C 01 -- Transaction Type
               9F 37 04 -- Unpredictable Number
               9F 35 01 -- Terminal Type
               9F 45 02 -- Data Authentication Code
               9F 4C 08 -- ICC Dynamic Number
               9F 34 03 -- Cardholder Verification (CVM) Results
               9F 21 03 -- Transaction Time (HHMMSS)
               9F 7C 14 -- Merchant Custom Data
         8D 0C -- Card Risk Management Data Object List 2 (CDOL2)
               91 0a -- Issuer Authentication Data
               8A 02 -- Authorisation Response Code
               95 05 -- Terminal Verification Results (TVR)
               9F 37 04 -- Unpredictable Number
               9F 4C 08 -- ICC Dynamic Number
         8E 0E -- Cardholder Verification Method (CVM) List
               00 00 00 00 00 00 00 00 42 03 1E 03 1F 03 (BINARY)
         9F 0D 05 -- Issuer Action Code - Default
                  B4 50 84 00 00 (BINARY)
         9F 0E 05 -- Issuer Action Code - Denial
                  00 00 00 00 00 (BINARY)
         9F 0F 05 -- Issuer Action Code - Online
                  B4 70 84 80 00 (BINARY)
         5F 28 02 -- Issuer Country Code
                  02 80 (NUMERIC)
         9F 4A 01 -- Static Data Authentication Tag List
                  82 (BINARY)
         57 13 -- Track 2 Equivalent Data
               53 75 05 00 00 16 01 10 D2 40 32 21 27 94 32 90
               00 00 0F (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

found tag 0x5a in the readRecordResponse length: 8 data: 5375050000160110
found tag 0x5f24 in the readRecordResponse length: 3 data: 240331


*********************************
************ step 07 ************
* print PAN & expire date       *
*********************************
07 get PAN and Expiration date from tags 0x5a and 0x5f24
data for AID a0000000041010
PAN: 5375050000160110
Expiration date (YYMMDD): 240331

for SFI 20 we read 2 records
readRecord  command length: 5 data: 00b2012400
readRecord response length: 189 data: 7081b89f4701039f4681b03cada902afb40289fbdfea01950c498191442c1b48234dcaff66bca63cbf821a3121fa808e4275a4e894b154c1874bddb00f16276e92c73c04468253b373f1e6a9a89e2705b4670682d0adff05617a21d7684031a1cdb438e66cd98d591dc376398c8aab4f137a2226122990d9b2b4c72ded6495d637338fefa893ae7fb4eb845f8ec2e260d2385a780f9fda64b3639a9547adad806f78c9bc9f17f9d4c5b26474b9ba03892a754ffdf24df04c702f869000
------------------------------------
70 81 B8 -- Record Template (EMV Proprietary)
         9F 47 01 -- ICC Public Key Exponent
                  03 (BINARY)
         9F 46 81 B0 -- ICC Public Key Certificate
                     3C AD A9 02 AF B4 02 89 FB DF EA 01 95 0C 49 81
                     91 44 2C 1B 48 23 4D CA FF 66 BC A6 3C BF 82 1A
                     31 21 FA 80 8E 42 75 A4 E8 94 B1 54 C1 87 4B DD
                     B0 0F 16 27 6E 92 C7 3C 04 46 82 53 B3 73 F1 E6
                     A9 A8 9E 27 05 B4 67 06 82 D0 AD FF 05 61 7A 21
                     D7 68 40 31 A1 CD B4 38 E6 6C D9 8D 59 1D C3 76
                     39 8C 8A AB 4F 13 7A 22 26 12 29 90 D9 B2 B4 C7
                     2D ED 64 95 D6 37 33 8F EF A8 93 AE 7F B4 EB 84
                     5F 8E C2 E2 60 D2 38 5A 78 0F 9F DA 64 B3 63 9A
                     95 47 AD AD 80 6F 78 C9 BC 9F 17 F9 D4 C5 B2 64
                     74 B9 BA 03 89 2A 75 4F FD F2 4D F0 4C 70 2F 86 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

readRecord  command length: 5 data: 00b2022400
readRecord response length: 229 data: 7081e08f01059f3201039224abfd2ebc115c3796e382be7e9863b92c266ccabc8bd014923024c80563234e8a11710a019081b004cc60769cabe557a9f2d83c7c73f8b177dbf69288e332f151fba10027301bb9a18203ba421bda9c2cc8186b975885523bf6707f287a5e88f0f6cd79a076319c1404fcdd1f4fa011f7219e1bf74e07b25e781d6af017a9404df9fd805b05b76874663ea88515018b2cb6140dc001a998016d28c4af8e49dfcc7d9cee314e72ae0d993b52cae91a5b5c76b0b33e7ac14a7294b59213ca0c50463cfb8b040bb8ac953631b80fa85a698b00228b5ff442239000
------------------------------------
70 81 E0 -- Record Template (EMV Proprietary)
         8F 01 -- Certification Authority Public Key Index - card
               05 (BINARY)
         9F 32 01 -- Issuer Public Key Exponent
                  03 (BINARY)
         92 24 -- Issuer Public Key Remainder
               AB FD 2E BC 11 5C 37 96 E3 82 BE 7E 98 63 B9 2C
               26 6C CA BC 8B D0 14 92 30 24 C8 05 63 23 4E 8A
               11 71 0A 01 (BINARY)
         90 81 B0 -- Issuer Public Key Certificate
                  04 CC 60 76 9C AB E5 57 A9 F2 D8 3C 7C 73 F8 B1
                  77 DB F6 92 88 E3 32 F1 51 FB A1 00 27 30 1B B9
                  A1 82 03 BA 42 1B DA 9C 2C C8 18 6B 97 58 85 52
                  3B F6 70 7F 28 7A 5E 88 F0 F6 CD 79 A0 76 31 9C
                  14 04 FC DD 1F 4F A0 11 F7 21 9E 1B F7 4E 07 B2
                  5E 78 1D 6A F0 17 A9 40 4D F9 FD 80 5B 05 B7 68
                  74 66 3E A8 85 15 01 8B 2C B6 14 0D C0 01 A9 98
                  01 6D 28 C4 AF 8E 49 DF CC 7D 9C EE 31 4E 72 AE
                  0D 99 3B 52 CA E9 1A 5B 5C 76 B0 B3 3E 7A C1 4A
                  72 94 B5 92 13 CA 0C 50 46 3C FB 8B 04 0B B8 AC
                  95 36 31 B8 0F A8 5A 69 8B 00 22 8B 5F F4 42 23 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

*********************************
************ step 99 ************
* our journey ends              *
*********************************
```

## Log file for a VisaCard
```plaintext
NFC tag discovered
TagId: 0585921afb9100
TechList found with these entries:
android.nfc.tech.IsoDep
android.nfc.tech.NfcA
connection with card success

*********************************
************ step 00 ************
* our journey begins            *
*********************************
increase IsoDep timeout for long lasting reading
timeout old: 2000 ms
timeout new: 10000 ms

*********************************
************ step 01 ************
* select PPSE                   *
*********************************
01 select PPSE command  length 20 data: 00a404000e325041592e5359532e444446303100
01 select PPSE response length 62 data: 6f3a840e325041592e5359532e4444463031a528bf0c2561234f07a0000000031010500a566973612044656269748701019f0a0800010501000000009000
------------------------------------
6F 3A -- File Control Information (FCI) Template
      84 0E -- Dedicated File (DF) Name
            32 50 41 59 2E 53 59 53 2E 44 44 46 30 31 (BINARY)
      A5 28 -- File Control Information (FCI) Proprietary Template
            BF 0C 25 -- File Control Information (FCI) Issuer Discretionary Data
                     61 23 -- Application Template
                           4F 07 -- Application Identifier (AID) - card
                                 A0 00 00 00 03 10 10 (BINARY)
                           50 0A -- Application Label
                                 56 69 73 61 20 44 65 62 69 74 (=Visa Debit)
                           87 01 -- Application Priority Indicator
                                 01 (BINARY)
                           9F 0A 08 -- [UNKNOWN TAG]
                                    00 01 05 01 00 00 00 00 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------


*********************************
************ step 02 ************
* search applications on card   *
*********************************
02 analyze select PPSE response and search for tag 0x4F (applications on card)
Found tag 0x4F 1 time:
application Id (AID): a0000000031010


*********************************
************ step 03 ************
* select application by AID     *
*********************************
03 select application by AID a0000000031010 (number 1)

03 select AID command  length 13 data: 00a4040007a000000003101000
03 select AID response length 115 data: 6f6f8407a0000000031010a564500a566973612044656269749f120f636f6d6469726563742044656269749f1101018701015f2d046465656e9f38189f66049f02069f03069f1a0295055f2a029a039c019f3704bf0c1a9f0a0800010501000000009f5a053109780276bf6304df2001809000
------------------------------------
6F 6F -- File Control Information (FCI) Template
      84 07 -- Dedicated File (DF) Name
            A0 00 00 00 03 10 10 (BINARY)
      A5 64 -- File Control Information (FCI) Proprietary Template
            50 0A -- Application Label
                  56 69 73 61 20 44 65 62 69 74 (=Visa Debit)
            9F 12 0F -- Application Preferred Name
                     63 6F 6D 64 69 72 65 63 74 20 44 65 62 69 74 (=comdirect Debit)
            9F 11 01 -- Issuer Code Table Index
                     01 (NUMERIC)
            87 01 -- Application Priority Indicator
                  01 (BINARY)
            5F 2D 04 -- Language Preference
                     64 65 65 6E (=deen)
            9F 38 18 -- Processing Options Data Object List (PDOL)
                     9F 66 04 -- Terminal Transaction Qualifiers
                     9F 02 06 -- Amount, Authorised (Numeric)
                     9F 03 06 -- Amount, Other (Numeric)
                     9F 1A 02 -- Terminal Country Code
                     95 05 -- Terminal Verification Results (TVR)
                     5F 2A 02 -- Transaction Currency Code
                     9A 03 -- Transaction Date
                     9C 01 -- Transaction Type
                     9F 37 04 -- Unpredictable Number
            BF 0C 1A -- File Control Information (FCI) Issuer Discretionary Data
                     9F 0A 08 -- [UNKNOWN TAG]
                              00 01 05 01 00 00 00 00 (BINARY)
                     9F 5A 05 -- Terminal transaction Type (Interac)
                              31 09 78 02 76 (BINARY)
                     BF 63 04 -- [UNKNOWN TAG]
                              DF 20 01 -- [UNKNOWN TAG]
                                       80 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------


*********************************
************ step 04 ************
* search for tag 0x9F38         *
*********************************
04 search for tag 0x9F38 in the selectAid response
Available predefined values for PDOL and CDOL
List of predefined tag and values for PDOL and CDOL
Tag  Name                            Value
-------------------------------------------------
9f66 Terminal Transaction Qualifiers 27000000
9f66 Terminal Transaction Qualifiers 27000000
9f66 Terminal Transaction Qualifiers b7604000
9f66 Terminal Transaction Qualifiers a0000000
9f66 Terminal Transaction Qualifiers f0204000
9f02 Transaction Amount              000000001000
9f03 Amount, Other (Numeric)         000000000000
9f1a Terminal Country Code           0978
95   Terminal Verificat.Results      0000000000
5f2a Transaction Currency Code       0978
9a   Transaction Date                230301
9c   Transaction Type                00
9f37 Unpredictable Number            38393031
9f35 Terminal Type                   22
9f45 Data Authentication Code        0000
9f4c ICC Dynamic Number              0000000000000000
9f34 Terminal Transaction Qualifiers 000000
9f21 Transaction Time (HHMMSS)       111009
9f7c Merchant Custom Data            0000000000000000000000000000
00   Tag not found                   00


### processing the American Express, VisaCard and GiroCard path ###

found tag 0x9F38 (PDOL) in the selectAid with this length: 24 data: 9f66049f02069f03069f1a0295055f2a029a039c019f3704

The card is requesting 9 tags in the PDOL

Tag  Tag Name                        Length Value
-----------------------------------------------------
9f66 Terminal Transaction Qualifiers     4  27 00 00 00 
9f02 Amount, Authorised (Numeric)        6  00 00 00 00 10 00 
9f03 Amount, Other (Numeric)             6  00 00 00 00 00 00 
9f1a Terminal Country Code               2  09 78 
95   Terminal Verification Results (TVR) 5  00 00 00 00 00 
5f2a Transaction Currency Code           2  09 78 
9a   Transaction Date                    3  23 03 01 
9c   Transaction Type                    1  00 
9f37 Unpredictable Number                4  38 39 30 31 
-----------------------------------------------------


*********************************
************ step 05 ************
* get the processing options    *
*********************************
05 get the processing options  command length: 41 data: 80a8000023832127000000000000001000000000000000097800000000000978230301003839303100
05 get the processing options response length: 203 data: 7781c68202202094041001030057134871780082770574d25072211328662101000f9f100706011203a000009f260812542e554303b0479f2701809f360200ec9f6c0204009f4b8180144bc4846abb62be64f36f8860b819eb13a65f289189d2975866a09ad0a7b338de8bf79d7651dc71c0fb1dd8becc99ae78b28ed6a3156644c2e1f67c9e466321ee3c9811061f89bc78f8c6ab86b10132cdfebaf575408997b956fd0c4d430264e4b281d805296b08b3ddde5c354dc22647ead5e6f071b8d6c5179c5db022044c9000
------------------------------------
77 81 C6 -- Response Message Template Format 2
         82 02 -- Application Interchange Profile
               20 20 (BINARY)
         94 04 -- Application File Locator (AFL)
               10 01 03 00 (BINARY)
         57 13 -- Track 2 Equivalent Data
               48 71 78 00 82 77 05 74 D2 50 72 21 13 28 66 21
               01 00 0F (BINARY)
         9F 10 07 -- Issuer Application Data
                  06 01 12 03 A0 00 00 (BINARY)
         9F 26 08 -- Application Cryptogram
                  12 54 2E 55 43 03 B0 47 (BINARY)
         9F 27 01 -- Cryptogram Information Data
                  80 (BINARY)
         9F 36 02 -- Application Transaction Counter (ATC)
                  00 EC (BINARY)
         9F 6C 02 -- Mag Stripe Application Version Number (Card)
                  04 00 (BINARY)
         9F 4B 81 80 -- Signed Dynamic Application Data
                     14 4B C4 84 6A BB 62 BE 64 F3 6F 88 60 B8 19 EB
                     13 A6 5F 28 91 89 D2 97 58 66 A0 9A D0 A7 B3 38
                     DE 8B F7 9D 76 51 DC 71 C0 FB 1D D8 BE CC 99 AE
                     78 B2 8E D6 A3 15 66 44 C2 E1 F6 7C 9E 46 63 21
                     EE 3C 98 11 06 1F 89 BC 78 F8 C6 AB 86 B1 01 32
                     CD FE BA F5 75 40 89 97 B9 56 FD 0C 4D 43 02 64
                     E4 B2 81 D8 05 29 6B 08 B3 DD DE 5C 35 4D C2 26
                     47 EA D5 E6 F0 71 B8 D6 C5 17 9C 5D B0 22 04 4C (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

workflow a)


*********************************
************ step 06 ************
* read files & search PAN       *
*********************************
06 read the files from card skipped
the response contains a Track 2 Equivalent Data tag [tag 0x57]
the response contains a Track 2 Equivalent Data tag [tag 0x57]
found tag 0x57 in the gpoResponse length: 19 data: 4871780082770574d25072211328662101000f
found a PAN 4871780082770574 with Expiration date: 2507


*********************************
************ step 07 ************
* print PAN & expire date       *
*********************************
07 get PAN and Expiration date from tag 0x57 (Track 2 Equivalent Data)
data for AID a0000000031010
PAN: 4871780082770574
Expiration date (YYMM): 2507

workflow c)
the response is of type 'Response Message Template Format 2' [tag 0x77]
found tag 0x77 in the gpoResponse
found 'AFL' [tag 0x94] in the response of type 'Response Message Template Format 2' [tag 0x77]
found tag 0x94 in the gpoResponse length: 4 data: 10010300


*********************************
************ step 06 ************
* read files & search PAN       *
*********************************
06 read the files from card and search for PAN & Expiration date

The AFL contains 1 entry to read
for SFI 10 we read 3 records
readRecord  command length: 5 data: 00b2011400
readRecord response length: 256 data: 7081fb9081f85ab54faf4ad810b3cca4ed42c38e1e768fca3187ed1be4196c6779c4633cbe88751889c12b05e10ee87cb198518793ff61e87534f66850e96239b76648429eced4cc207608d0d2a932dd9e8c4bb0d139c4eca59e1ef5f4708f72d80dc5b66c45f4566c91b55384dfdeabb55faa622c6764cc9fb4c4900b6ab2cec5abad9057e2cf63a881bb4ec2a5d96634d7c11366eb908a168d33aa3c544822fc83e74c104b9275b2ef1cf41375b404a260bbf8fb3d4452af3d0630bb1ec2a01676ba588ae7820727622a6d9df5c93a3ce807d54b79ae007c3d401f8787dc3e235e8b9ae6b1b9279328cb1ca94105434010f15eb07f487f4d5c94f4a5a79000
------------------------------------
70 81 FB -- Record Template (EMV Proprietary)
         90 81 F8 -- Issuer Public Key Certificate
                  5A B5 4F AF 4A D8 10 B3 CC A4 ED 42 C3 8E 1E 76
                  8F CA 31 87 ED 1B E4 19 6C 67 79 C4 63 3C BE 88
                  75 18 89 C1 2B 05 E1 0E E8 7C B1 98 51 87 93 FF
                  61 E8 75 34 F6 68 50 E9 62 39 B7 66 48 42 9E CE
                  D4 CC 20 76 08 D0 D2 A9 32 DD 9E 8C 4B B0 D1 39
                  C4 EC A5 9E 1E F5 F4 70 8F 72 D8 0D C5 B6 6C 45
                  F4 56 6C 91 B5 53 84 DF DE AB B5 5F AA 62 2C 67
                  64 CC 9F B4 C4 90 0B 6A B2 CE C5 AB AD 90 57 E2
                  CF 63 A8 81 BB 4E C2 A5 D9 66 34 D7 C1 13 66 EB
                  90 8A 16 8D 33 AA 3C 54 48 22 FC 83 E7 4C 10 4B
                  92 75 B2 EF 1C F4 13 75 B4 04 A2 60 BB F8 FB 3D
                  44 52 AF 3D 06 30 BB 1E C2 A0 16 76 BA 58 8A E7
                  82 07 27 62 2A 6D 9D F5 C9 3A 3C E8 07 D5 4B 79
                  AE 00 7C 3D 40 1F 87 87 DC 3E 23 5E 8B 9A E6 B1
                  B9 27 93 28 CB 1C A9 41 05 43 40 10 F1 5E B0 7F
                  48 7F 4D 5C 94 F4 A5 A7 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

readRecord  command length: 5 data: 00b2021400
readRecord response length: 11 data: 70078f01099f3201039000
------------------------------------
70 07 -- Record Template (EMV Proprietary)
      8F 01 -- Certification Authority Public Key Index - card
            09 (BINARY)
      9F 32 01 -- Issuer Public Key Exponent
               03 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

readRecord  command length: 5 data: 00b2031400
readRecord response length: 240 data: 7081eb9f4681b02c4b62dfaede136b9bafeddebaaf41e5f4fdc9920b077817de896e6503c69c8f80ece2559cdf721ce1b7b2bc159fe77ec8d6eb45296876fbf4a6bd4bb4a11511ebd80fdf1c7bb8e1f4a2cdb7c4db0cc6f9fda7f6696c30d3846e1b98f4c849b7385f349d280fd92d75774dcbed96a5328f657f7eceb4bfa3ec3f9f39a64414bdbf0f03b15c49cbf0475bfa6a5f2513689c195faea031ae2391998be2028aa1671b380eb19a69a6c454bd2a30d11bc63c9f4701035a0848717800827705745f24032507315f3401015f280202769f070200809f4a01829f6e04207000009f6907015fc31d9704009000
------------------------------------
70 81 EB -- Record Template (EMV Proprietary)
         9F 46 81 B0 -- ICC Public Key Certificate
                     2C 4B 62 DF AE DE 13 6B 9B AF ED DE BA AF 41 E5
                     F4 FD C9 92 0B 07 78 17 DE 89 6E 65 03 C6 9C 8F
                     80 EC E2 55 9C DF 72 1C E1 B7 B2 BC 15 9F E7 7E
                     C8 D6 EB 45 29 68 76 FB F4 A6 BD 4B B4 A1 15 11
                     EB D8 0F DF 1C 7B B8 E1 F4 A2 CD B7 C4 DB 0C C6
                     F9 FD A7 F6 69 6C 30 D3 84 6E 1B 98 F4 C8 49 B7
                     38 5F 34 9D 28 0F D9 2D 75 77 4D CB ED 96 A5 32
                     8F 65 7F 7E CE B4 BF A3 EC 3F 9F 39 A6 44 14 BD
                     BF 0F 03 B1 5C 49 CB F0 47 5B FA 6A 5F 25 13 68
                     9C 19 5F AE A0 31 AE 23 91 99 8B E2 02 8A A1 67
                     1B 38 0E B1 9A 69 A6 C4 54 BD 2A 30 D1 1B C6 3C (BINARY)
         9F 47 01 -- ICC Public Key Exponent
                  03 (BINARY)
         5A 08 -- Application Primary Account Number (PAN)
               48 71 78 00 82 77 05 74 (NUMERIC)
         5F 24 03 -- Application Expiration Date
                  25 07 31 (NUMERIC)
         5F 34 01 -- Application Primary Account Number (PAN) Sequence Number
                  01 (NUMERIC)
         5F 28 02 -- Issuer Country Code
                  02 76 (NUMERIC)
         9F 07 02 -- Application Usage Control
                  00 80 (BINARY)
         9F 4A 01 -- Static Data Authentication Tag List
                  82 (BINARY)
         9F 6E 04 -- Visa Low-Value Payment (VLP) Issuer Authorisation Code
                  20 70 00 00 (BINARY)
         9F 69 07 -- UDOL
                  01 5F C3 1D 97 04 00 (BINARY)
90 00 -- Command successfully executed (OK)
------------------------------------

found tag 0x5a in the readRecordResponse length: 8 data: 4871780082770574
found tag 0x5f24 in the readRecordResponse length: 3 data: 250731


*********************************
************ step 07 ************
* print PAN & expire date       *
*********************************
07 get PAN and Expiration date from tags 0x5a and 0x5f24
data for AID a0000000031010
PAN: 4871780082770574
Expiration date (YYMMDD): 250731

*********************************
************ step 99 ************
* our journey ends              *
*********************************
```