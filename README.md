cryptography
============

Java implementation of miscellaneous crypto algorithms (a winter break 2012 project)

30 Dec 2012 - Redid the format of the ciphers -- basically every encryption and decryption needs a key(s), or else it defeats the purpose of a cipher

31 Dec 2012 - Okay scrapped that whole Key idea, Cipher is now abstract but there's no way to subcall encrypt or decrypt from Cipher now
