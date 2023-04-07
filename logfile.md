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
************ step 99 ************
* our journey ends              *
*********************************
```