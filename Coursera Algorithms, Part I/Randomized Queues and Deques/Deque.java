import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {      
    private Node sentinel;
    private int size = 0; 
    private class Node
    {
        Node next, prev;
        Item item;
        public Node(Node next, Node prev, Item item)
        {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }
    }  
    public Deque()                           // construct an empty deque
    {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return sentinel.next == sentinel;
    }
    public int size()                        // return the number of items on the deque
    {
        return size;
    }
    public void addFirst(Item item)          // insert the item at the front
    {
        if (item == null) throw new NullPointerException();
        Node first = new Node(sentinel.next, sentinel, item);
        sentinel.next.prev = first;
        sentinel.next = first;        
        size++;
    }
    public void addLast(Item item)           // insert the item at the end
    {
        if (item == null) throw new NullPointerException();
        Node last = new Node(sentinel, sentinel.prev, item);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size++;
    }
    public Item removeFirst()                // delete and return the item at the front
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return item;
    }
    public Item removeLast()                 // delete and return the item at the end
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return item;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = sentinel;
        public boolean hasNext()
        {
            return current.next != sentinel;
        }
        public Item next()
        {
            if (!hasNext()) throw new java.util.NoSuchElementException();    
            current = current.next;
            Item item = current.item;
            return item;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }    
    public static void main(String[] args)    // unit testing
    {  
    }
}