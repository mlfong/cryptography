package cryptography.ciphers;

public class CaesarCipher extends Cipher
{
    private final int ASCII_MOD = 256;
    
    public CaesarCipher()
    {
    }

    public String encrypt(String message, int rotation)
    {
        StringBuilder sb = new StringBuilder(message.length());
        for(Character c : message.toCharArray())
            sb.append((char)((c+(rotation)%ASCII_MOD)));
        return sb.toString();
    }

    public String decrypt(String encoded, int rotation)
    {
        StringBuilder sb = new StringBuilder(encoded.length());
        for(Character c : encoded.toCharArray())
            sb.append((char)((c-(rotation)%ASCII_MOD)));
        return sb.toString();
    }

    public static void main(String[] args)
    {
        int rotation = 3;
        CaesarCipher cc = new CaesarCipher();
        String message = "Another great message!!101!1!";
        String encrypt = cc.encrypt(message, rotation);
        System.out.println(encrypt);
        String decrypt = cc.decrypt(encrypt, rotation);
        System.out.println(decrypt);
    }

}
