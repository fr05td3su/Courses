public class PercolationStats {
    private double[] fractions;
    private int N;
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
             throw new IllegalArgumentException();
        this.N = N;
        fractions = new double[T];
        for (int i = 0; i < T; i++) {
            double frac = (double) oneTime() / (N * N);
            fractions[i] = frac;
        }
    }
    public double mean() {
        return StdStats.mean(fractions);
    }
    public double stddev() {
        return StdStats.stddev(fractions);
    }
    public double confidenceLo() {           // returns lower bound of the 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(fractions.length);
    }
    public double confidenceHi() {            // returns upper bound of the 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(fractions.length);
    }
    private int oneTime() {
        Percolation perc = new Percolation(N);
        int sitesOpened = 0;
        while (!perc.percolates()) {
            int i = rand();
            int j = rand();
            if (!perc.isOpen(i, j)) {
                perc.open(i, j);
                sitesOpened++;
            }
        }
        return sitesOpened;
    }
    private int rand() {
        return StdRandom.uniform(N) + 1;
    }
    public static void main(String[] args) {
    }
}