package cryptography.ciphers;

import java.util.HashMap;
import java.util.Map;

public class AlBhedCipher extends Cipher
{
    private static String AL_BHED_ALPHABET = "YPLTAVKREZGMSHUBXNCDIJFQOW";
    private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Map<Character, Character> eng2AB;
    private Map<Character, Character> ab2ENG;
    
    public AlBhedCipher()
    {
        eng2AB = new HashMap<Character, Character>();
        ab2ENG = new HashMap<Character, Character>();
        char[] albhed = AL_BHED_ALPHABET.toCharArray();
        char[] english = ALPHABET.toCharArray();
        assert(albhed.length == english.length);
        for(int i = 0; i < albhed.length; i++)
        {
            eng2AB.put(english[i], albhed[i]);
            eng2AB.put(((char)(english[i]+32)), ((char)(albhed[i]+32)));
            ab2ENG.put(albhed[i], english[i]);
            ab2ENG.put(((char)(albhed[i]+32)), ((char)(english[i]+32)));
        }
    }
    
    public String encrypt(String message)
    {
        StringBuilder sb = new StringBuilder(message.length());
        for(Character c : message.toCharArray())
            sb.append( eng2AB.containsKey(c) ? eng2AB.get(c) : c );
        return sb.toString();
    }

    public String decrypt(String encoded)
    {
        StringBuilder sb = new StringBuilder(encoded.length());
        for(Character c : encoded.toCharArray())
            sb.append( ab2ENG.containsKey(c) ? ab2ENG.get(c) : c );
        return sb.toString();
    }

    public static void main(String[] args)
    {
        String message = "My name is Rikku and I'm not from around here!!";
        AlBhedCipher cipher = new AlBhedCipher();
        String encoded = cipher.encrypt(message);
        System.out.println(encoded);
        String decoded = cipher.decrypt(encoded);
        System.out.println(decoded);
    }
}
