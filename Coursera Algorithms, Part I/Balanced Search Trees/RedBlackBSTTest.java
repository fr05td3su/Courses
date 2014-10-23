public class RedBlackBSTTest
{
    public static void main(String[] args)
    {
        //FIRST EXERCISE
        
    int[] keys = new int[] {58, 42 ,83 ,13, 52, 73, 92, 11, 39, 49, 36, 95, 31 };

    RedBlackBST pq = new RedBlackBST();
    
    for (int key: keys) pq.put(key, key);
    pq.traverse();
    StdOut.println("\n "+pq.keys());
    for (int key: keys)
        {
            StdOut.println("Get key " + key);      
            pq.get(key);
            StdOut.println();        
        }
    return;
    }
}