package cryptography.ciphers;

import cryptography.exceptions.WrongKeyException;
import cryptography.keys.IntKey;
import cryptography.keys.Key;


public class CaesarCipher implements Cipher
{
    private final int ASCII_MOD = 256;
    
    public CaesarCipher()
    {
    }

    @Override
    public String encrypt(String message, Key key) throws WrongKeyException
    {
        if(!(key instanceof IntKey))
            throw new WrongKeyException();
        StringBuilder sb = new StringBuilder(message.length());
        for(Character c : message.toCharArray())
            sb.append((char)((c+((IntKey)key).getValue())%ASCII_MOD));
        return sb.toString();
    }

    @Override
    public String decrypt(String encoded, Key key) throws WrongKeyException
    {
        if(!(key instanceof IntKey))
            throw new WrongKeyException();
        StringBuilder sb = new StringBuilder(encoded.length());
        for(Character c : encoded.toCharArray())
            sb.append((char)((c+256-((IntKey)key).getValue())%ASCII_MOD));
        return sb.toString();
    }

    public static void main(String[] args) throws WrongKeyException
    {
        Key key = new IntKey(15);
        CaesarCipher cc = new CaesarCipher();
        String message = "Another great message!!101!1!";
        String encrypt = cc.encrypt(message, key);
        System.out.println(encrypt);
        String decrypt = cc.decrypt(encrypt, key);
        System.out.println(decrypt);
    }

}
