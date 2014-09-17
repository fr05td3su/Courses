/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation input.txt
 *  Dependencies: Percolation.java StdDraw.java In.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size N of the percolation system.
 *    - Creates an N-by-N grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ****************************************************************************/
    Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java StdDraw.java In.java


public class Percolation {
    private final int N; // Length of one side of the grid.
    private boolean[] open;
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullness;
    private final int virtualTop;
    private final int virtualBottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) { 
        if (N >= 0xffff || N <= 0)
            throw new IllegalArgumentException("Dimension must be < 2^16");
        this.N = N;
        open = new boolean[N*N];
        // Add two for the virtual top and bottom
        percolation = new WeightedQuickUnionUF(N*N + 2);
        fullness = new WeightedQuickUnionUF(N*N + 2);
        virtualTop = indexOf(N, N) + 1;
        virtualBottom = indexOf(N, N) + 2;
    }
// is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return open[indexOf(i, j)];
    }
// is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return fullness.connected(virtualTop, indexOf(i, j));
    }
// does the system percolate?
    public boolean percolates() {
        return percolation.connected(virtualTop, virtualBottom);
    }
// open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (isOpen(i, j))
            return;
        int index = indexOf(i, j);
        open[index] = true;
        if (i == 1) {
            percolation.union(virtualTop, index);
            fullness.union(virtualTop, index);
        }
        if (i == N)
            percolation.union(virtualBottom, index);
        if (i < N && isOpen(i + 1, j)) {
            percolation.union(indexOf(i + 1, j), index);
            fullness.union(indexOf(i + 1, j), index);
        }
        if (i > 1 && isOpen(i - 1, j)) {
            percolation.union(indexOf(i - 1, j), index);
            fullness.union(indexOf(i - 1, j), index);
        }
        if (j < N && isOpen(i, j + 1)) {
            percolation.union(indexOf(i, j + 1), index);
            fullness.union(indexOf(i, j + 1), index);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            percolation.union(indexOf(i, j - 1), index);
            fullness.union(indexOf(i, j - 1), index);
        }
    }
    /* Convert grid coordinates of the form (x, y) where x,y in {1,...,N}
     to an array index. E.g., indexOf(1,1) == 0; indexOf(N, N) = N^2 - 1.
     Assume the grid is in row-major form.
     */
    private int indexOf(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N)
            throw new IndexOutOfBoundsException(
                                                "(" + row + ", " + col + ") out of bounds "
                                                    + "for " + N + "^2 grid.");
        return (row - 1) * N + (col - 1);
    }
    public static void main(String[] argv) {

    }
}