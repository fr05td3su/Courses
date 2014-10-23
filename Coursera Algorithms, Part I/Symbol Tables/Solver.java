public class Solver {
    private SearchNode current;
   
    private class SearchNode implements Comparable<SearchNode>
    {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int priority;
        
        public SearchNode(Board brd, SearchNode prev)
        {
            board = brd;
            previous = prev;
            if (prev == null) 
                moves = 0;
            else 
                moves = previous.moves + 1;
            priority = board.manhattan()+moves;
        }
        public int compareTo(SearchNode that)
        { 
            return this.priority - that.priority; 
        }
    } 
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {        
        if (initial.isGoal())
            current = new SearchNode(initial, null);
        else
        {
            SearchNode lpn, twinlpn;
            MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
            MinPQ<SearchNode> twinqueue = new MinPQ<SearchNode>();
            queue.insert(new SearchNode(initial, null));
            twinqueue.insert(new SearchNode(initial.twin(), null));
            while (true)
            {
                twinlpn = SolverIteraion(twinqueue);
                lpn = SolverIteraion(queue);
                
                if (twinlpn.board.isGoal())
                {
                    lpn = null;
                    break;
                }
                if (lpn.board.isGoal()) 
                    break;           
            }
            current = lpn;
        }
    }
    private SearchNode SolverIteraion(MinPQ<SearchNode> queue)
    {
        SearchNode lpn = queue.delMin();
        for (Board neighbor: lpn.board.neighbors())
            if (lpn.previous == null || !neighbor.equals(lpn.previous.board))
            queue.insert(new SearchNode(neighbor, lpn)); 
        return lpn;
    }
    public boolean isSolvable()             // is the initial board solvable?
    {
        return current != null;
    }
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if (current != null)
            return current.moves;
        return -1;
    }
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if (current == null)
            return null;
        Stack<Board> stack = new Stack<Board>();
        SearchNode sn = current;
        while (sn != null)
        {
            stack.push(sn.board);
            sn = sn.previous;
        }
        return stack;      
    }
    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}