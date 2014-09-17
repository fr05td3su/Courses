public class PercolationStats {
    private double[] fractions;
    private int N;
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
             throw new IllegalArgumentException("Nonnegative arguments");
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
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stat = new PercolationStats(N, T);
        double mean = stat.mean();
        double std = stat.stddev();
        double left = mean - 1.96 * std / Math.sqrt(T);
        double right = mean + 1.96 * std / Math.sqrt(T);
        int leftN = (int) (N * N * left);
        int rightN = (int) (N * N * right);
        StdOut.printf("%-23s = %f\n", "mean", mean);
        StdOut.printf("%-23s = %f\n", "stddev", std);
        StdOut.printf("%-23s = %f, %f\n", "95% confidence interval",
                      left, right);
        StdOut.printf("\nN: %d, T: %d\n", N, T);
        StdOut.printf("Total: %d, open sites [%d, %d]\n",
                      N*N, leftN, rightN);
    }
}