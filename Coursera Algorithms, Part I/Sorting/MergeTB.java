public class MergeTB
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
            else if (less(aux[j],aux[i]))  a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }   
        if (count==8) for (int p=0; p<a.length; p++) StdOut.println(""+ a[p]);  
    }
    
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }
    
    public static void sort(Comparable[] a)
    {
        Comparable aux[] = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }
    public static void main(String[] args)
    {
      Comparable a[] = new Comparable[] {60, 94, 82, 14, 73, 43, 97, 69, 41, 51, 84, 23 };
      sort(a);       
    }
}
    