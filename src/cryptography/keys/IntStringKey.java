package cryptography.keys;

public class IntStringKey extends Key
{
    private int intVal;
    private String strVal;
    
    public IntStringKey(int iv, String sv)
    {
        this.intVal = iv;
        this.strVal = sv;
    }
    public int getIntVal()
    {
        return this.intVal;
    }
    public String getStrVal()
    {
        return this.strVal;
    }
}
