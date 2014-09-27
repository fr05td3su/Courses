/*************************************************************************
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private class SlopeOrder implements Comparator<Point>
    {
        public int compare(Point fpt, Point spt)
        {
            //Formally, the point (x1, y1) is less than the point (x2, y2) if and only if 
            //the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0). 
            double diff = slopeTo(fpt) - slopeTo(spt);
            if (diff < 0.0) return -1;
            if (diff > 0.0) return 1;
            return 0;
                
        }
    }
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    
    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if (that.y == this.y) return Double.NEGATIVE_INFINITY; // degenerate line segment's slope is -inf
            return Double.POSITIVE_INFINITY;                       // vertical line segment's slope is +inf
        }
        if (that.y == this.y) return 0.0;                          // horizontal line segment's slope is positive zero 
        return ((double) that.y - this.y)/(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y == that.y) return this.x - that.x;
        return this.y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}