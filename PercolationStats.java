import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {

    private int[] numbers;
    private static double confidentConst = 1.96;
    private int generateRandom(int size) {
        return (int) (Math.ceil(StdRandom.uniform() * size));
    }

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        numbers = new int[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = generateRandom(n);
                int col = generateRandom(n);
                perc.open(row, col);
            }

            int number = perc.numberOfOpenSites();
            numbers[i] = number;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(numbers);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(numbers);
    }

    // low end point of 95% confidence interval
    public double confidenceLo() {
        double meanNum = mean();
        double s = stddev();
        return meanNum - confidentConst * s / Math.sqrt(numbers.length);
    }

    // high end point of 95% confidence interval
    public double confidenceHi() {
        double meanNum = mean();
        double s = stddev();
        return meanNum + confidentConst * s / Math.sqrt(numbers.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        StdOut.println("Please enter the grid length: ");
        int gridLength = StdIn.readInt();
        StdOut.println("Please enter the trial times: ");
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(gridLength, trials);
        StdOut.println("\n");
        StdOut.println("Experiments on " + gridLength + " x " + gridLength + " grid, " + trials + " tials: ");
        StdOut.println("Mean: " + stats.mean());
        StdOut.println("Standard dev: " + stats.stddev());
        StdOut.println("Confidence Low: " + stats.confidenceLo());
        StdOut.println("Confidence Hi: " + stats.confidenceHi());
    }
}
