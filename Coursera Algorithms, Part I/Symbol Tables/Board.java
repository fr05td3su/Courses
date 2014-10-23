import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private int N;
    private int memo = -1;                 //to keep track of previous steps;
    
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {                                      // (where blocks[i][j] = block in row i, column j)
        N = blocks.length;
        tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N;j++)
            tiles[i][j] = blocks[i][j];
    }
    public int dimension()                 // board dimension N
    { 
        return N;
    }
    public int hamming()                   // number of blocks out of place
    {
        int counter = 0;
        int check = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            if (tiles[i][j] != check++) counter++;
        return --counter;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        if (memo >= 0)
            return memo;
        int check;
        int counter = 0;
        int i = -1;
        int j = -1;
        for (i = 0; i < N; i++)
            for (j = 0; j < N; j++)
            if (i != N-1 || j != N-1 || tiles[i][j] != 0)
        {
            check = tiles[i][j];
            if (check != 0)                // zero is just a blank space
            {
                int ri = (check - 1) / N;
                int rj = (check - 1) % N;
                counter += Math.abs(i - ri) + Math.abs(j - rj);
            }
        }
        memo = counter;
        return counter;     
    }
    public boolean isGoal()                // is this board the goal board?
    {
        if (tiles[N-1][N-1] != 0) 
            return false;
        int check = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            if (tiles[i][j] != check++ && (i != N-1 || j != N-1))
                return false;
        return true;
    }
   public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
       // copy the entire array
       int[][] twin = new int[N][N];      
       for (int i = 0; i < N; i++)
           for (int j = 0; j < N; j++) 
           twin[i][j] = tiles[i][j];
       
       //swap first two non-zero entries in the same row
       find: 
           for (int i = 0; i < N; i++)
           for (int j = 0; j < N-1; j++)    
           if (twin[i][j] != 0 && twin[i][j+1] != 0)
       {
           int swap = twin[i][j];
           twin[i][j] = twin[i][j+1];
           twin[i][j+1] = swap;
           break find;
       }
       return new Board(twin);    
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < N; i++)
            if (!Arrays.equals(this.tiles[i], that.tiles[i])) return false;
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> queue = new Queue<Board>();
        int i = -1;
        int j = -1;
        find:
            for (i = 0; i < N; i++)
            for (j = 0; j < N; j++)
            if (tiles[i][j] == 0) 
            break find;
        if (i < N-1)    queue.enqueue(swap(tiles, i, j, i + 1, j));
        if (i > 0)      queue.enqueue(swap(tiles, i, j, i - 1, j));
        if (j < N-1)    queue.enqueue(swap(tiles, i, j, i, j + 1));
        if (j > 0)      queue.enqueue(swap(tiles, i, j, i, j - 1));
        return queue;  
        }
    
    private Board swap(int[][] tiles, int rf, int cf, int rs, int cs)
    {
       int[][] twin = new int[N][N];      
       for (int i = 0; i < N; i++)
           for (int j = 0; j < N; j++) 
           twin[i][j] = tiles[i][j];
       int swap = twin[rf][cf];
       twin[rf][cf] = twin[rs][cs];
       twin[rs][cs] = swap;       
       return new Board(twin);
    }

    public String toString()               // string representation of the board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}