public class PointSET {
    
    private SET<Point2D> set;
    public PointSET()                                        // construct an empty set of points 
    {
        set = new SET<Point2D>();   
    }
    public boolean isEmpty()                                 // is the set empty? 
    {
        return set.isEmpty();
    }
    public int size()                                        // number of points in the set 
    {
        return set.size();
    }
    public void insert(Point2D p)                            // add the point to the set (if it is not already in the set)
    {
        if (!set.contains(p))
            set.add(p);
    }
    public boolean contains(Point2D p)                       // does the set contain point p? 
    {
        return set.contains(p);
    }
    public void draw()                                       // draw all points to standard draw 
    {
        if (!isEmpty())
        for (Point2D p: set)
            p.draw();
    }
    public Iterable<Point2D> range(RectHV rect)              // all points that are inside the rectangle 
    {
         SET<Point2D> ins = new SET<Point2D>();  
        for (Point2D p: set)
            if (rect.contains(p)) ins.add(p);
        return ins;
    }
    public Point2D nearest(Point2D p)                        // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (!isEmpty())
        {
            Point2D closest = p;
            if      (p != set.min()) closest = set.min();
            else if (p != set.max()) closest = set.max();
            for (Point2D q: set)
                if (q != p && p.distanceTo(q) < p.distanceTo(closest)) closest = q;
            return closest;
        }
        return null;
    } 
    public static void main(String[] args)                   // unit testing of the methods 
    {
    }
}