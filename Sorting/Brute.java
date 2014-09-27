import java.util.Arrays;

public class Brute {
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
        for (int i = 0; i < N; i++) 
        {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        // horrible brute-force algo iterates through every 4 points in the array, checking whether they are collinear
        for (int a = 0; a < N; a++)
        {
            Point pta = points[a];
            for (int b = a+1; b < N; b++)
            {
                Point ptb = points[b];
                for (int c = b+1; c < N; c++)
                {
                    Point ptc = points[c];
                    double ab = pta.slopeTo(ptb);
                    double ac = pta.slopeTo(ptc);
                    if (ab == ac) 
                    {                 
                        for (int d = c+1; d < N; d++)
                        {
                            Point ptd = points[d];
                            double ad = pta.slopeTo(ptd);
                            if (ab == ad) 
                            {
                                Point sorted[] = new Point[] {pta, ptb, ptc, ptd};
                                Arrays.sort(sorted);
                                StdOut.printf("%s -> %s -> %s -> %s\n", sorted[0], sorted[1], sorted[2], sorted[3]);
                                sorted[0].drawTo(sorted[3]);
                            }
                        }
                    }
                }
            }
        }       
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
   }
}
