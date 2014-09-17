public class FixedCapacityStackOfStrings
{
    private int N;
    private String[] s;
    
    public FixedCapacityStackOfStrings(int capacity)
    {
        s = new String[capacity];
    }
    
    public boolean isEmpty()
    {
        return N == 0;
    }
    
    public void push(String item)
    {
        s[N++] = item;
    }
    
    public String pop()
    {
        String item = s[--N];
        s[N] = null;
        return item;
    }
}