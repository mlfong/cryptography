package cryptography;

public class CaesarCipher implements Cipher
{
    private int rotation ;
    private final int ASCII_MOD = 256;
    
    public CaesarCipher(int r)
    {
        this.rotation = r;
    }

    @Override
    public String encrypt(String message)
    {
        StringBuilder sb = new StringBuilder(message.length());
        for(Character c : message.toCharArray())
            sb.append((char)((c+rotation)%ASCII_MOD));
        return sb.toString();
    }

    @Override
    public String decrypt(String encoded)
    {
        StringBuilder sb = new StringBuilder(encoded.length());
        for(Character c : encoded.toCharArray())
            sb.append((char)((c+256-rotation)%ASCII_MOD));
        return sb.toString();
    }

    public static void main(String[] args)
    {
        int rotation = 15;
        CaesarCipher cc = new CaesarCipher(rotation);
        String message = "Another great message!!101!1!";
        String encrypt = cc.encrypt(message);
        System.out.println(encrypt);
        String decrypt = cc.decrypt(encrypt);
        System.out.println(decrypt);
    }

}
