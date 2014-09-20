public class QuickSortDijkstra
{
    private static boolean less(Comparable i, Comparable j)
    {
        return i.compareTo(j) < 0;  
    }
    private static void exch(Comparable[] a, int i, int j)
    {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;   
    }

    public static void sort(Comparable[] a)
    {
        //StdRandom.shuffle(a);                          //commented for test purposes
        sort(a, 0, a.length - 1);
    }
    
    private static void sort(Comparable[] a, int lo, int hi) 
    {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt)
        {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        
        for (int p=0; p<a.length; p++)                   //for test purposes
        {
            if  (p == a.length-1) StdOut.print(a[p]+""); 
            else StdOut.print(a[p]+" "); 
        }
        StdOut.println();
        
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
        
        public static void main(String[] args)
    {
      Comparable a[] = new Comparable[] {44, 90, 13, 44, 27, 67, 94, 87, 85, 44 };
      sort(a);
    }
} 