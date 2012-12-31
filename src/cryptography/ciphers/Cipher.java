package cryptography.ciphers;

import cryptography.exceptions.WrongKeyException;
import cryptography.keys.Key;


public interface Cipher
{
    public String encrypt(String message, Key key) throws WrongKeyException;
    public String decrypt(String encoded, Key key) throws WrongKeyException;
}
