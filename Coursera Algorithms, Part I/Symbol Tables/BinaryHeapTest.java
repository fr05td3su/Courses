public class BinaryHeapTest
{
    public static void main(String[] args)
    {
        //FIRST EXERCISE
        
    int[] data = new int[] {94, 72, 80, 43, 32, 51, 55, 23, 27, 28 };
    MaxPQ pq = new MaxPQ(data.length+10);
    for (int item: data) pq.insert(item);
    
    
    int[] insertions = new int[] { 16, 76, 21 };
    for (int item: insertions) pq.insert(item);
    Comparable[] pull = pq.pull();
    for (Comparable item: pull) StdOut.printf(" "+item);
    StdOut.println();
    
        // SECOND EXERCISE
    data = new int[] {98, 93, 85, 66, 72, 21, 29, 37, 40, 46 };
    pq = new MaxPQ(data.length+10);
    for (int item: data) pq.insert(item);
    for (int i=3; i > 0; i--) pq.delMax();
    pull = pq.pull();
    for (Comparable item: pull) StdOut.printf(" "+item);
    StdOut.println();
    
    return;
    }
}