# Complete log file after reading a Credit Card

## Log file for a MasterCard
```plaintext
I/System.out: NFC tag discovered
I/System.out: TagId: 028eedb17074b0
I/System.out: TechList found with these entries:
I/System.out: android.nfc.tech.IsoDep
I/System.out: android.nfc.tech.NfcA
I/System.out: connection with card success
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 00 ************
I/System.out: * our journey begins            *
I/System.out: *********************************
I/System.out: increase IsoDep timeout for long lasting reading
I/System.out: timeout old: 2000 ms
I/System.out: timeout new: 10000 ms
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 01 ************
I/System.out: * select PPSE                   *
I/System.out: *********************************
I/System.out: 01 select PPSE command  length 20 data: 00a404000e325041592e5359532e444446303100
I/System.out: 01 select PPSE response length 64 data: 6f3c840e325041592e5359532e4444463031a52abf0c2761254f07a000000004101050104465626974204d6173746572436172648701019f0a04000101019000
I/System.out: ------------------------------------
I/System.out: 6F 3C -- File Control Information (FCI) Template
I/System.out:       84 0E -- Dedicated File (DF) Name
I/System.out:             32 50 41 59 2E 53 59 53 2E 44 44 46 30 31 (BINARY)
I/System.out:       A5 2A -- File Control Information (FCI) Proprietary Template
I/System.out:             BF 0C 27 -- File Control Information (FCI) Issuer Discretionary Data
I/System.out:                      61 25 -- Application Template
I/System.out:                            4F 07 -- Application Identifier (AID) - card
I/System.out:                                  A0 00 00 00 04 10 10 (BINARY)
I/System.out:                            50 10 -- Application Label
I/System.out:                                  44 65 62 69 74 20 4D 61 73 74 65 72 43 61 72 64 (=Debit MasterCard)
I/System.out:                            87 01 -- Application Priority Indicator
I/System.out:                                  01 (BINARY)
I/System.out:                            9F 0A 04 -- [UNKNOWN TAG]
I/System.out:                                     00 01 01 01 (BINARY)
I/System.out: 90 00 -- Command successfully executed (OK)
I/System.out: ------------------------------------
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 02 ************
I/System.out: * search applications on card   *
I/System.out: *********************************
I/System.out: 02 analyze select PPSE response and search for tag 0x4F (applications on card)
I/System.out: Found tag 0x4F 1 time:
I/System.out: application Id (AID): a0000000041010
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 03 ************
I/System.out: * select application by AID     *
I/System.out: *********************************
I/System.out: 03 select application by AID a0000000041010 (number 1)
I/System.out: 
I/System.out: 03 select AID command  length 13 data: 00a4040007a000000004101000
I/System.out: 03 select AID response length 86 data: 6f528407a0000000041010a54750104465626974204d6173746572436172649f12104465626974204d6173746572436172648701019f1101015f2d046465656ebf0c119f0a04000101019f6e07028000003030009000
I/System.out: ------------------------------------
I/System.out: 6F 52 -- File Control Information (FCI) Template
I/System.out:       84 07 -- Dedicated File (DF) Name
I/System.out:             A0 00 00 00 04 10 10 (BINARY)
I/System.out:       A5 47 -- File Control Information (FCI) Proprietary Template
I/System.out:             50 10 -- Application Label
I/System.out:                   44 65 62 69 74 20 4D 61 73 74 65 72 43 61 72 64 (=Debit MasterCard)
I/System.out:             9F 12 10 -- Application Preferred Name
I/System.out:                      44 65 62 69 74 20 4D 61 73 74 65 72 43 61 72 64 (=Debit MasterCard)
I/System.out:             87 01 -- Application Priority Indicator
I/System.out:                   01 (BINARY)
I/System.out:             9F 11 01 -- Issuer Code Table Index
I/System.out:                      01 (NUMERIC)
I/System.out:             5F 2D 04 -- Language Preference
I/System.out:                      64 65 65 6E (=deen)
I/System.out:             BF 0C 11 -- File Control Information (FCI) Issuer Discretionary Data
I/System.out:                      9F 0A 04 -- [UNKNOWN TAG]
I/System.out:                               00 01 01 01 (BINARY)
I/System.out:                      9F 6E 07 -- Visa Low-Value Payment (VLP) Issuer Authorisation Code
I/System.out:                               02 80 00 00 30 30 00 (BINARY)
I/System.out: 90 00 -- Command successfully executed (OK)
I/System.out: ------------------------------------
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 04 ************
I/System.out: * search for tag 0x9F38         *
I/System.out: *********************************
I/System.out: 04 search for tag 0x9F38 in the selectAid response
I/System.out: Available predefined values for PDOL and CDOL
I/System.out: List of predefined tag and values for PDOL and CDOL
I/System.out: Tag  Name                            Value
I/System.out: -------------------------------------------------
I/System.out: 9f66 Terminal Transaction Qualifiers 27000000
I/System.out: 9f66 Terminal Transaction Qualifiers 27000000
I/System.out: 9f66 Terminal Transaction Qualifiers b7604000
I/System.out: 9f66 Terminal Transaction Qualifiers a0000000
I/System.out: 9f66 Terminal Transaction Qualifiers f0204000
I/System.out: 9f02 Transaction Amount              000000001000
I/System.out: 9f03 Amount, Other (Numeric)         000000000000
I/System.out: 9f1a Terminal Country Code           0978
I/System.out: 95   Terminal Verificat.Results      0000000000
I/System.out: 5f2a Transaction Currency Code       0978
I/System.out: 9a   Transaction Date                230301
I/System.out: 9c   Transaction Type                00
I/System.out: 9f37 Unpredictable Number            38393031
I/System.out: 9f35 Terminal Type                   22
I/System.out: 9f45 Data Authentication Code        0000
I/System.out: 9f4c ICC Dynamic Number              0000000000000000
I/System.out: 9f34 Terminal Transaction Qualifiers 000000
I/System.out: 9f21 Transaction Time (HHMMSS)       111009
I/System.out: 9f7c Merchant Custom Data            0000000000000000000000000000
I/System.out: 00   Tag not found                   00
I/System.out: 
I/System.out: 
I/System.out: ### processing the MasterCard path ###
I/System.out: 
I/System.out: No PDOL found in the selectAid response, generating a 'null' PDOL
I/System.out: 
I/System.out: The card is requesting 0 tags in the PDOL
I/System.out: 
I/System.out: Tag  Tag Name                        Length Value
I/System.out: -----------------------------------------------------
I/System.out:      no PDOL provided, returning an empty command
I/System.out: -----------------------------------------------------
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 05 ************
I/System.out: * get the processing options    *
I/System.out: *********************************
I/System.out: 05 get the processing options  command length: 8 data: 80a8000002830000
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 99 ************
I/System.out: * our journey ends              *
I/System.out: *********************************
```

## Log file for a VisaCard
```plaintext
I/System.out: NFC tag discovered
I/System.out: TagId: 0585921afb9100
I/System.out: TechList found with these entries:
I/System.out: android.nfc.tech.IsoDep
I/System.out: android.nfc.tech.NfcA
I/System.out: connection with card success
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 00 ************
I/System.out: * our journey begins            *
I/System.out: *********************************
I/System.out: increase IsoDep timeout for long lasting reading
I/System.out: timeout old: 2000 ms
I/System.out: timeout new: 10000 ms
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 01 ************
I/System.out: * select PPSE                   *
I/System.out: *********************************
I/System.out: 01 select PPSE command  length 20 data: 00a404000e325041592e5359532e444446303100
I/System.out: 01 select PPSE response length 62 data: 6f3a840e325041592e5359532e4444463031a528bf0c2561234f07a0000000031010500a566973612044656269748701019f0a0800010501000000009000
I/System.out: ------------------------------------
I/System.out: 6F 3A -- File Control Information (FCI) Template
I/System.out:       84 0E -- Dedicated File (DF) Name
I/System.out:             32 50 41 59 2E 53 59 53 2E 44 44 46 30 31 (BINARY)
I/System.out:       A5 28 -- File Control Information (FCI) Proprietary Template
I/System.out:             BF 0C 25 -- File Control Information (FCI) Issuer Discretionary Data
I/System.out:                      61 23 -- Application Template
I/System.out:                            4F 07 -- Application Identifier (AID) - card
I/System.out:                                  A0 00 00 00 03 10 10 (BINARY)
I/System.out:                            50 0A -- Application Label
I/System.out:                                  56 69 73 61 20 44 65 62 69 74 (=Visa Debit)
I/System.out:                            87 01 -- Application Priority Indicator
I/System.out:                                  01 (BINARY)
I/System.out:                            9F 0A 08 -- [UNKNOWN TAG]
I/System.out:                                     00 01 05 01 00 00 00 00 (BINARY)
I/System.out: 90 00 -- Command successfully executed (OK)
I/System.out: ------------------------------------
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 02 ************
I/System.out: * search applications on card   *
I/System.out: *********************************
I/System.out: 02 analyze select PPSE response and search for tag 0x4F (applications on card)
I/System.out: Found tag 0x4F 1 time:
I/System.out: application Id (AID): a0000000031010
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 03 ************
I/System.out: * select application by AID     *
I/System.out: *********************************
I/System.out: 03 select application by AID a0000000031010 (number 1)
I/System.out: 
I/System.out: 03 select AID command  length 13 data: 00a4040007a000000003101000
I/System.out: 03 select AID response length 115 data: 6f6f8407a0000000031010a564500a566973612044656269749f120f636f6d6469726563742044656269749f1101018701015f2d046465656e9f38189f66049f02069f03069f1a0295055f2a029a039c019f3704bf0c1a9f0a0800010501000000009f5a053109780276bf6304df2001809000
I/System.out: ------------------------------------
I/System.out: 6F 6F -- File Control Information (FCI) Template
I/System.out:       84 07 -- Dedicated File (DF) Name
I/System.out:             A0 00 00 00 03 10 10 (BINARY)
I/System.out:       A5 64 -- File Control Information (FCI) Proprietary Template
I/System.out:             50 0A -- Application Label
I/System.out:                   56 69 73 61 20 44 65 62 69 74 (=Visa Debit)
I/System.out:             9F 12 0F -- Application Preferred Name
I/System.out:                      63 6F 6D 64 69 72 65 63 74 20 44 65 62 69 74 (=comdirect Debit)
I/System.out:             9F 11 01 -- Issuer Code Table Index
I/System.out:                      01 (NUMERIC)
I/System.out:             87 01 -- Application Priority Indicator
I/System.out:                   01 (BINARY)
I/System.out:             5F 2D 04 -- Language Preference
I/System.out:                      64 65 65 6E (=deen)
I/System.out:             9F 38 18 -- Processing Options Data Object List (PDOL)
I/System.out:                      9F 66 04 -- Terminal Transaction Qualifiers
I/System.out:                      9F 02 06 -- Amount, Authorised (Numeric)
I/System.out:                      9F 03 06 -- Amount, Other (Numeric)
I/System.out:                      9F 1A 02 -- Terminal Country Code
I/System.out:                      95 05 -- Terminal Verification Results (TVR)
I/System.out:                      5F 2A 02 -- Transaction Currency Code
I/System.out:                      9A 03 -- Transaction Date
I/System.out:                      9C 01 -- Transaction Type
I/System.out:                      9F 37 04 -- Unpredictable Number
I/System.out:             BF 0C 1A -- File Control Information (FCI) Issuer Discretionary Data
I/System.out:                      9F 0A 08 -- [UNKNOWN TAG]
I/System.out:                               00 01 05 01 00 00 00 00 (BINARY)
I/System.out:                      9F 5A 05 -- Terminal transaction Type (Interac)
I/System.out:                               31 09 78 02 76 (BINARY)
I/System.out:                      BF 63 04 -- [UNKNOWN TAG]
I/System.out:                               DF 20 01 -- [UNKNOWN TAG]
I/System.out:                                        80 (BINARY)
I/System.out: 90 00 -- Command successfully executed (OK)
I/System.out: ------------------------------------
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 04 ************
I/System.out: * search for tag 0x9F38         *
I/System.out: *********************************
I/System.out: 04 search for tag 0x9F38 in the selectAid response
I/System.out: Available predefined values for PDOL and CDOL
I/System.out: List of predefined tag and values for PDOL and CDOL
I/System.out: Tag  Name                            Value
I/System.out: -------------------------------------------------
I/System.out: 9f66 Terminal Transaction Qualifiers 27000000
I/System.out: 9f66 Terminal Transaction Qualifiers 27000000
I/System.out: 9f66 Terminal Transaction Qualifiers b7604000
I/System.out: 9f66 Terminal Transaction Qualifiers a0000000
I/System.out: 9f66 Terminal Transaction Qualifiers f0204000
I/System.out: 9f02 Transaction Amount              000000001000
I/System.out: 9f03 Amount, Other (Numeric)         000000000000
I/System.out: 9f1a Terminal Country Code           0978
I/System.out: 95   Terminal Verificat.Results      0000000000
I/System.out: 5f2a Transaction Currency Code       0978
I/System.out: 9a   Transaction Date                230301
I/System.out: 9c   Transaction Type                00
I/System.out: 9f37 Unpredictable Number            38393031
I/System.out: 9f35 Terminal Type                   22
I/System.out: 9f45 Data Authentication Code        0000
I/System.out: 9f4c ICC Dynamic Number              0000000000000000
I/System.out: 9f34 Terminal Transaction Qualifiers 000000
I/System.out: 9f21 Transaction Time (HHMMSS)       111009
I/System.out: 9f7c Merchant Custom Data            0000000000000000000000000000
I/System.out: 00   Tag not found                   00
I/System.out: 
I/System.out: 
I/System.out: ### processing the American Express, VisaCard and GiroCard path ###
I/System.out: 
I/System.out: found tag 0x9F38 (PDOL) in the selectAid with this length: 24 data: 9f66049f02069f03069f1a0295055f2a029a039c019f3704
I/System.out: 
I/System.out: The card is requesting 9 tags in the PDOL
I/System.out: 
I/System.out: Tag  Tag Name                        Length Value
I/System.out: -----------------------------------------------------
I/System.out: 9f66 Terminal Transaction Qualifiers     4  27 00 00 00 
I/System.out: 9f02 Amount, Authorised (Numeric)        6  00 00 00 00 10 00 
I/System.out: 9f03 Amount, Other (Numeric)             6  00 00 00 00 00 00 
I/System.out: 9f1a Terminal Country Code               2  09 78 
I/System.out: 95   Terminal Verification Results (TVR) 5  00 00 00 00 00 
I/System.out: 5f2a Transaction Currency Code           2  09 78 
I/System.out: 9a   Transaction Date                    3  23 03 01 
I/System.out: 9c   Transaction Type                    1  00 
I/System.out: 9f37 Unpredictable Number                4  38 39 30 31 
I/System.out: -----------------------------------------------------
I/System.out: 
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 05 ************
I/System.out: * get the processing options    *
I/System.out: *********************************
I/System.out: 05 get the processing options  command length: 41 data: 80a8000023832127000000000000001000000000000000097800000000000978230301003839303100
I/System.out: 
I/System.out: *********************************
I/System.out: ************ step 99 ************
I/System.out: * our journey ends              *
I/System.out: *********************************

```