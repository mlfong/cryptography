package cryptography.ciphers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cryptography.exceptions.WrongKeyException;
import cryptography.keys.Key;
import cryptography.keys.TwoStringKey;

public class FourSquareCipher implements Cipher
{
    private static final String ALPHABET = "abcdefghiklmnopqrstuvwxyz0123456789 ";

    public FourSquareCipher()
    {
    }

    @SuppressWarnings("unchecked")
    private static List<List<Character>> makeCipher(String key1, String key2)
    {
        key1 = key1.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "")
                .replaceAll("j", "i");
        key2 = key2.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "")
                .replaceAll("j", "i");
        List<List<Character>> cipher = new ArrayList<List<Character>>()
        {
            private static final long serialVersionUID = -6996104572850838562L;
            {
                add(new ArrayList<Character>());
                add(new ArrayList<Character>());
                add(new ArrayList<Character>());
                add(new ArrayList<Character>());
            }
        };
        for (Character c : ALPHABET.toCharArray())
            for (int i = 0; i < 4; i += 2)
                cipher.get(i).add(c);
        Object[] one = noDuplicates(key1);
        Object[] two = noDuplicates(key2);
        StringBuilder uniqueKey1 = (StringBuilder) one[0];
        StringBuilder uniqueKey2 = (StringBuilder) two[0];
        HashSet<Character> setKey1 = (HashSet<Character>) one[1];
        HashSet<Character> setKey2 = (HashSet<Character>) two[1];
        for (int i = 0; i < uniqueKey1.length(); i++)
            cipher.get(1).add(uniqueKey1.charAt(i));
        for (int i = 0; i < uniqueKey2.length(); i++)
            cipher.get(3).add(uniqueKey2.charAt(i));
        for (Character c : ALPHABET.toCharArray())
        {
            if (!setKey1.contains(c))
                cipher.get(1).add(c);
            if (!setKey2.contains(c))
                cipher.get(3).add(c);
        }
        return cipher;
    }

    private static Object[] noDuplicates(String original)
    {
        Set<Character> s = new HashSet<Character>();
        StringBuilder unique = new StringBuilder(s.size());
        for (Character c : original.toCharArray())
        {
            if (!s.contains(c))
            {
                s.add(c);
                unique.append(c);
            }
        }
        Object[] o =
        { unique, s };
        return o;
    }

    private static int row(int i)
    {
        return i / 6;
    }

    private static int col(int i)
    {
        return i % 6;
    }

    private static int ori(int r, int c)
    {
        return r * 6 + c;
    }

    @Override
    public String encrypt(String message, Key key) throws WrongKeyException
    {
        if (!(key instanceof TwoStringKey))
            throw new WrongKeyException();
        List<List<Character>> cipher = makeCipher(
                ((TwoStringKey) key).getKey1(), ((TwoStringKey) key).getKey2());
        message = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "")
                .replaceAll("j", "i");
        if ((message.length() & 1) == 1)
            message += ALPHABET
                    .charAt((int) (Math.random() * ALPHABET.length()));
        StringBuilder sb = new StringBuilder(message.length());
        // removed next line -- reimplement if we want to be a tad bit more
        // efficient
        // Character extra = (message.length()&1)==1 ?
        // ALPHABET.charAt((int)(Math.random()*ALPHABET.length())) : null;
        // linear search in case we want to swap out alphabet ie caesar shift
        // the alphabet etc
        for (int i = 0; i < message.length(); i += 2)
        {
            int orig1 = cipher.get(0).indexOf(message.charAt(i));
            int orig2 = cipher.get(3).indexOf(message.charAt(i + 1));
            sb.append(cipher.get(1).get(ori(row(orig1), col(orig2))));
            sb.append(cipher.get(2).get(ori(row(orig2), col(orig1))));
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String encoded, Key key) throws WrongKeyException
    {
        if (!(key instanceof TwoStringKey))
            throw new WrongKeyException();
        List<List<Character>> cipher = makeCipher(
                ((TwoStringKey) key).getKey1(), ((TwoStringKey) key).getKey2());
        StringBuilder sb = new StringBuilder(encoded.length());
        for (int i = 0; i < encoded.length(); i += 2)
        {
            int enc1 = cipher.get(1).indexOf(encoded.charAt(i));
            int enc2 = cipher.get(2).indexOf(encoded.charAt(i + 1));
            sb.append(cipher.get(0).get(ori(row(enc1), col(enc2))));
            sb.append(cipher.get(3).get(ori(row(enc2), col(enc1))));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws WrongKeyException
    {
        Key key = new TwoStringKey("battle", "shipps");
        Cipher cc = new FourSquareCipher();
        String message = "my favourite thing is 123";
        String encoded = cc.encrypt(message, key);
        System.out.println(encoded);
        String decoded = cc.decrypt(encoded, key);
        System.out.println(decoded);
    }
}
