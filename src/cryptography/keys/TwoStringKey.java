package cryptography.keys;

public class TwoStringKey extends Key
{
    private String key1;
    private String key2;
    
    public TwoStringKey(String k1, String k2)
    {
        this.key1 = k1;
        this.key2 = k2;
    }
    public String getKey1()
    {
        return this.key1;
    }
    public String getKey2()
    {
        return this.key2;
    }
}
