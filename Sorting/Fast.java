import java.util.Arrays;

public class Fast {
   public static void main(String[] args)
   {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point points[] = new Point[N];
        for (int i = 0; i < N; i++) //N
        {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        int collinears = 0; //for debug purposes 
        
        Arrays.sort(points);       
        Point slopeSorted[] = new Point[N];
                
        for (int i = 0; i < N-1; i++) //N-1
        {
            Point p = points[i];   
            for (int j = 0; j < N; j++) slopeSorted[j] = points[j];     //N     
            slopeSorted[i] = points[N-1];
            Arrays.sort(slopeSorted, 0, N-1, p.SLOPE_ORDER);
            double prev = 0;  
            int from = 0;
            int to = 0;
            for (int j = 0; j < N; j++) //N
            {
                Point q = slopeSorted[j];
                double slope = p.slopeTo(q);         
                if (slope == prev)
                {
                    to = j;
                }
                else
                { 
                    if (to - from >= 2 && p.compareTo(slopeSorted[from]) <= 0) //<=0
                    {
                        Point aux[] = new Point[to-from+1];
                        int counter = 0;
                        for (int idx = from; idx <= to; idx++) 
                        {
                            aux[counter] = slopeSorted[idx];
                            counter++;
                        }
                        Arrays.sort(aux);
                        StdOut.printf("%s", p);
                        for (Point n:aux)
                            StdOut.printf(" -> %s", n);
                        StdOut.printf("\n");
                        p.drawTo(aux[--counter]);
                        collinears++;
                        
                    }
                    from = j;
                }
                prev = slope;
            }     
            if (to == N-1 && to-from >= 3 && p.compareTo(slopeSorted[from]) <= 0) 
            {
                Point aux[] = new Point[to-from];
                int counter = 0;
                for (int idx = from; idx < to; ++idx) 
                {
                    aux[counter] = slopeSorted[idx];
                    counter++;
                }
                Arrays.sort(aux);
                StdOut.printf("%s", p);
                for (Point n:aux)
                    StdOut.printf(" -> %s", n);
                StdOut.printf("\n");
                p.drawTo(aux[--counter]);
                collinears++;
            }
        }
        StdOut.println(""+collinears);
        // display to screen all at once
        StdDraw.show(0);
        
        // reset the pen radius
        StdDraw.setPenRadius();
   }
}