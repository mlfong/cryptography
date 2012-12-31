package cryptography.keys;

public class IntKey extends Key
{
    private int value;
    public IntKey(int v)
    {
        this.value = v;
    }
    public int getValue()
    {
        return this.value;
    }
}
