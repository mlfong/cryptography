package cryptography.keys;

public class OneStringKey extends Key
{
    private String value;
    public OneStringKey(String v)
    {
        this.value = v;
    }
    public String getValue()
    {
        return this.value;
    }
}
