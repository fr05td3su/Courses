import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] queue;
   private int N;
   
   private void resize(int capacity)
   {
       Item[]resized = (Item[]) new Object[capacity]; 
       for (int i = 0; i < N; i++) resized[i] = queue[i];  
       queue = resized;
   }
   private void exch(Item[] array, int i, int j) //swap items i and j in an array
   {
       Item temp = array[i];
       array[i] = array[j];
       array[j] = temp;
   }   
   public RandomizedQueue()                 // construct an empty randomized queue
   {   
       queue = (Item[]) new Object[1];
       N = 0;                               //last-item pointer
   }
   public boolean isEmpty()                 // is the queue empty?
   {
      return N == 0;
   }
   public int size()                        // return the number of items on the queue
   {
       return N;
   }
   public void enqueue(Item item)           // add the item
   {
       if (item == null) throw new NullPointerException();
       if (N == queue.length) resize(N << 1 ); 
       queue[N++] = item;
   }
   public Item dequeue()                    // delete and return a random item
   {
       if (N == 0) throw new java.util.NoSuchElementException();;
       exch(queue, StdRandom.uniform(N), --N); 
       Item item = queue[N];
       queue[N] = null;            
       if (queue.length > 0 && N < queue.length / 4) resize(queue.length >> 1); //bitshift for division by 2
       return item;    
   }
   public Item sample()                     // return (but do not delete) a random item
   {
       if (N == 0) throw new NoSuchElementException();
       return queue[StdRandom.uniform(N)];
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedQueueIterator();
   }
   private class RandomizedQueueIterator implements Iterator<Item>
   {
       private int[] deck;         //create additional array with random indices
       private int bound = N;
       public RandomizedQueueIterator()
       {
           deck = new int[bound];  
           for (int i = 0; i < bound; i++) deck[i] = i; 
           StdRandom.shuffle(deck);
       }
       public boolean hasNext()
       {
           return bound > 0;
       }
       public Item next()
       {
           if (!hasNext()) throw new NoSuchElementException();
           return queue[deck[--bound]];
       }
       public void remove()
       {
           throw new UnsupportedOperationException();
       }      
   }      
   public static void main(String[] args)   // unit testing
   {
   }
}