public class MergeBU
{  
    private static boolean less(Comparable i, Comparable j)
    {
        return i.compareTo(j) < 0;  
    }
    static int count = 1;
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        StdOut.println(count + "-th call to merge!");
        count++;
        for (int k = lo; k <= hi; k++) aux[k] = a[k];
        
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }    
        if (count==8) for (int p=0; p<a.length; p++) StdOut.println(""+ a[p]);  
        //for (int p=0; p<a.length; p++) StdOut.println(""+ a[p]); 
        //StdOut.println("/n"); 
    }
    
    public static void sort(Comparable[] a)
    {
     int N = a.length;
     Comparable[] aux = new Comparable[N];
     for (int sz = 1; sz < N; sz = sz+sz)
         for (int lo = 0; lo < N-sz; lo += sz+sz)
     {
         merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
     }
    }
    
    public static void main(String[] args)
    {
      Comparable a[] = new Comparable[] {68, 72, 33, 73, 87, 34, 15, 71, 17, 52 };
      sort(a);
    }
}
