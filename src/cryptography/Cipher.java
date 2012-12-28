package cryptography;

public interface Cipher
{
    public String encrypt(String message);
    public String decrypt(String encoded);
}
