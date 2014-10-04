public class BSTTest
{
    public static void main(String[] args)
    {
        //FIRST EXERCISE
        
    int[] keys = new int[] {45, 39, 97, 36, 41, 52, 88, 75, 91, 54};
    int value = 0;
    
    BST pq = new BST();
    
    for (int key: keys) pq.put(key, value++);
    
    StdOut.println(" "+pq.get(79));

    return;
    }
}