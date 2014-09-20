public class QuickSort
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
    static int count = 1;
    private static int partition(Comparable[] a, int lo, int hi)
    {
        int i = lo, j = hi+1;
        while (true)
        {
            while (less(a[++i], a[lo]))
                if (i == hi) break;
            while (less(a[lo], a[--j]))
                if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        for (int p=0; p<a.length; p++) 
        {
            if  (p == a.length-1) StdOut.print(a[p]+""); 
            else StdOut.print(a[p]+" "); 
        }
        StdOut.println();
        return j;
    } 
    public static void sort(Comparable[] a)
    {
        //StdRandom.shuffle(a);                // commented for test purposes
        sort(a, 0, a.length - 1);
    }
    private static void sort(Comparable[] a, int lo, int hi) 
    {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    
    /*public static Comparable   select(Comparable [] a, int k)
    { 
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length-1;
        while(hi>lo)
        {
            int j=partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }*/
    
        public static void main(String[] args)
    {
      Comparable a[] = new Comparable[] { 53, 23, 71, 37, 91, 93, 11, 95, 51, 30, 14, 36 };
      sort(a);
    }
} 