package cryptography.ciphers;

/*****
 * @author mlfong
 * 
 * http://en.wikipedia.org/wiki/ADFGVX_cipher
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ADFGVXCipher extends Cipher
{
    private final static String ALPHABET = "abcdefghiklmnopqrstuvwxyz0123456789 ";
    private final static String CIPHER = "adfgvx";
    
    public String encrypt(String message, String transportationKey, int seed)
    {
        List<Character> substitutionKey = new ArrayList<Character>();
        for(Character c : ALPHABET.toCharArray())
            substitutionKey.add(c);
        Collections.shuffle(substitutionKey, new Random(seed));
        
        Map<Character, String> cipherTable = new HashMap<Character, String>();
        int i = 0;
        for(Character c : CIPHER.toCharArray())
            for(Character c2: CIPHER.toCharArray())
                cipherTable.put(substitutionKey.get(i++), ""+c+c2);
        cipherTable.put('J', cipherTable.get('I'));
        
        message = message.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
        StringBuilder sb = new StringBuilder(message.length()*2);
        for(Character c : message.toCharArray())
            sb.append(cipherTable.get(c));
        List<CipherColumn> tKeyTable = new ArrayList<CipherColumn>(transportationKey.length());
        for(Character c : transportationKey.toCharArray())
            tKeyTable.add(new CipherColumn(c));
        int r = transportationKey.length() - (sb.length() % transportationKey.length());
        while(r-- > 0)
            sb.append(substitutionKey.get((int)(Math.random()*substitutionKey.size())));
        i = 0;
        for(Character c : sb.toString().toCharArray())
            tKeyTable.get(i++%tKeyTable.size()).add(c);
        Collections.sort(tKeyTable);
        sb.delete(0, sb.length());
        for(CipherColumn cc : tKeyTable)
            sb.append(cc.column);
        return sb.toString();
    }
    public String decrypt(String encoded, String transportationKey, int seed)
    {
        List<Character> substitutionKey = new ArrayList<Character>();
        for(Character c : ALPHABET.toCharArray())
            substitutionKey.add(c);
        Collections.shuffle(substitutionKey, new Random(seed));
        
        Map<String, Character> reverseCipherTable = new HashMap<String, Character>();
        int j = 0;
        for(Character c : CIPHER.toCharArray())
            for(Character c2: CIPHER.toCharArray())
                reverseCipherTable.put(""+c+c2, substitutionKey.get(j++));
        
        StringBuilder sb = new StringBuilder(encoded.length());
        int col = encoded.length()/transportationKey.length();
        List<CipherColumn> sortedTKeyTable = new ArrayList<CipherColumn>(transportationKey.length());
        List<Character> sortedTKey = new ArrayList<Character>(transportationKey.length());
        for(Character c : transportationKey.toCharArray())
            sortedTKey.add(c);
        Collections.sort(sortedTKey);
        j = 0;
        for(int i = 0 ; i < encoded.length(); i += col)
        {
            sortedTKeyTable.add(new CipherColumn(sortedTKey.get(j)));
            sortedTKeyTable.get(j++).add(encoded.substring(i,i+col));
        }
        List<CipherColumn> origTKeyTable = new ArrayList<CipherColumn>(transportationKey.length());
        for(Character c : transportationKey.toCharArray())
        {
            CipherColumn cc = null;
            for(int i = 0 ; i < sortedTKeyTable.size(); i++)
            {
                if(sortedTKeyTable.get(i).key == c)
                {
                    cc = sortedTKeyTable.get(i);
                    sortedTKeyTable.remove(i);
                    break;
                }
            }
            origTKeyTable.add(cc);
        }
        for(j = 0; j < col ; j++)
            for(int i = 0; i < origTKeyTable.size(); i++)
                sb.append(origTKeyTable.get(i).column.charAt(j));
        StringBuilder decrypt = new StringBuilder(encoded.length()/2);
        for(j = 0; j < sb.length(); j+=2)
            if(reverseCipherTable.containsKey(sb.substring(j,j+2)))
                decrypt.append(reverseCipherTable.get(sb.substring(j, j+2)));
        return decrypt.toString();
    }
    
    class CipherColumn implements Comparable<Object>
    {
        private Character key;
        private StringBuilder column;
        
        public CipherColumn(Character key)
        {
            this.key = key;
            column = new StringBuilder();
        }
        public void add(Character c)
        {
            column.append(c);
        }
        public void add(String s)
        {
            column.append(s);
        }
        public void add(char[] c)
        {
            column.append(c);
        }
        public void add(StringBuilder sb)
        {
            column.append(sb);
        }
        
        @Override
        public int compareTo(Object arg0)
        {
            if(arg0 instanceof CipherColumn)
            {
                CipherColumn other = (CipherColumn)arg0;
                return this.key - other.key;
            }
            return -1;
        }
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(this.key + ": [" );
            sb.append(this.column.toString() + "]");
            return sb.toString();
        }
    }
    
    public static void main(String[] args)
    {
        int seed = 42;
        String key = "programmer";
        ADFGVXCipher cipher = new ADFGVXCipher();
        String message = "Wow what a great example message this is";
        String encrypted = cipher.encrypt(message, key, seed);
        System.out.println(encrypted);
        String decrypted = cipher.decrypt(encrypted,key, seed);
        System.out.println(decrypted);
    }

}
