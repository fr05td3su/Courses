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
            throw new IllegalArgumentException();
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
    private int indexOf(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N) throw new IndexOutOfBoundsException();
        return (row - 1) * N + (col - 1);
    }
    public static void main(String[] argv) {

    }
}