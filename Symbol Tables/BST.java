public class BST<Key extends Comparable<Key>, Value>
{
    private Node root;
    private class Node
    {
        private Key key;
        private Value val;
        private int count;
        private Node left, right;
        public Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
        }
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public void put(Key key, Value val)
    {
        root = put(key, val, root);
        
    }
    private Node put(Key key, Value val, Node x)
    {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(key, val, x.left);
        else if (cmp > 0) x.right = put(key, val, x.right);
        else x.val = val; 
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }
    public Value get(Key key)
    {
        Node x = root;

        while (x != null)
        {
            StdOut.printf(x.key + " "); //for trace purposes
            int cmp = key.compareTo(x.key);
            if      (cmp < 0)  x = x.left;
            else if (cmp > 0)  x = x.right;
            else return x.val;
        }
        return null;
    }
    public int size()
    {
        return size(root);   
    }
    private int size(Node x)
    {
        if (x == null) return 0;
        return x.count;
    }
    public void deleteMin()
    { 
        root = deleteMin(root); 
    }
    private Node deleteMin(Node x)
    {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    public void delete(Key key)
    { 
        root = delete(root, key); 
    }
    private Node delete(Node x, Key key) 
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else 
        {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    } 
    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    } 
    
    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 
    
    public Iterable<Key> keys()
    {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }
    private void inorder(Node x, Queue<Key> q)
    {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    } 
}