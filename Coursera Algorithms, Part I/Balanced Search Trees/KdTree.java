public class KdTree {
    private Node root;
    private int size;
    private static class Node
    {     
        private Point2D p;
        private RectHV rect;
        private Node lb;                                    // left/bottom subtree
        private Node rt;                                    // right/top subtree
        public Node(Point2D p, RectHV rect)
        {
            this.p = p;
            this.rect = rect;
        }
    }
    public KdTree()                                         // construct an empty set of ps 
    {
        root = null;
    }
    public boolean isEmpty()                                // is the set empty? 
    {
        return root == null;
    }
    public int size()                                       // number of ps in the set 
    {
        return size;
    }
    public void insert(Point2D p) 
    {
        root = insert(root, p, true, 0, 0, 1, 1);
    }
    private Node insert(Node x, Point2D p, boolean orientation, double xmin, double ymin, double xmax, double ymax) 
    {
        if (x == null) 
        {
            this.size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (x.p.equals(p)) return x;
        double cmp;
        if (orientation) 
        {
            cmp = p.x() - x.p.x();
            if (cmp < 0) x.lb = insert(x.lb, p, !orientation, x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            else         x.rt = insert(x.rt, p, !orientation, x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
        } 
        else 
        {
            cmp = p.y() - x.p.y();
            if (cmp < 0) x.lb = insert(x.lb, p, !orientation, x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            else         x.rt = insert(x.rt, p, !orientation, x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
        }
        return x;
    }
    public boolean contains(Point2D p)                      // does the set contain p p? 
    {
        return contains(root, p, true);
    }
    private boolean contains(Node x, Point2D p, boolean orientation) 
    {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        double cmp;
        cmp = orientation ? p.x() - x.p.x() : p.y() - x.p.y();
        if (cmp < 0) return contains(x.lb, p, !orientation);
        else         return contains(x.rt, p, !orientation);
    }
    public void draw()                                      // draw all ps to standard draw 
    {
        draw(root, false);
    }
    private void draw(Node x, boolean vertical)             //DOES NOT WORK CORRECT NOW
    {
        if (x != null)
        {
            if (vertical) 
            {
                StdDraw.setPenColor(StdDraw.BOOK_RED); 
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            }
            else 
            {
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }
            //x.rect.draw();
            StdDraw.setPenColor(StdDraw.BLACK);
            x.p.draw();
            if (x.lb != null) draw(x.lb, !vertical);
            if (x.rt != null) draw(x.rt, !vertical);
        }   
    }
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }
    private void range(Node x, RectHV rect, Queue<Point2D> q) 
    {
        if (x == null) return;
        if (!x.rect.intersects(rect)) return;
        if (rect.contains(x.p)) q.enqueue(x.p);
        range(x.lb, rect, q);
        range(x.rt, rect, q);
    }
    public Point2D nearest(Point2D p) 
    {
        return nearest(root, p, Double.POSITIVE_INFINITY);
    }
    private Point2D nearest(Node x, Point2D p, double distance) 
    {
        if (x == null) return null;
        if (x.rect.distanceTo(p) >= distance) return null;
        Point2D nearestPoint = null;
        double nearestDistance = distance;
        double d;
        d = p.distanceTo(x.p);
        if (d < nearestDistance) 
        {
            nearestPoint = x.p;
            nearestDistance = d;
        }
        Node first = x.lb;
        Node second = x.rt;
        if (first != null && second != null) 
        {
            if (first.rect.distanceTo(p) > second.rect.distanceTo(p)) 
            {
                first = x.rt;
                second = x.lb;
            }
        }
        Point2D firstNearestPoint = nearest(first, p, nearestDistance);
        if (firstNearestPoint != null) 
        {
            d = p.distanceTo(firstNearestPoint);
            if (d < nearestDistance) 
            {
                nearestPoint = firstNearestPoint;
                nearestDistance = d;
            }
        }
        Point2D secondNearestPoint = nearest(second, p, nearestDistance);
        if (secondNearestPoint != null) 
        {
            d = p.distanceTo(secondNearestPoint);
            if (d < nearestDistance) 
            {
                nearestPoint = secondNearestPoint;
                nearestDistance = d;
            }
        }
        return nearestPoint;
    }
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        StdDraw.show(0);
        RectHV rect = new RectHV(0, 0, 1, 1);
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                StdDraw.clear();
                kdtree.draw();
                //rect.draw();

            }
            StdDraw.show(50);
        }
    }
}