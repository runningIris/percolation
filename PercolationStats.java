import edu.princeton.cs.algs4.*;

public class PercolationStats {
	
	private int[] numbers;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		
		numbers = new int[trials];		
		for(int i = 0; i < trials; i++) 
		{
			Percolation perc = new Percolation(n);

			while (!perc.percolates()) {
				int row = perc.generateRandom();
				int col = perc.generateRandom();
				perc.open(row, col);
			}
			
			int number = perc.numberOfOpenSites();
			numbers[i] = number;
		}
		
	}
	// sample mean of percolation threshold
	public double mean() {
		int sum = 0;
		for (int i = 0; i < numbers.length; i++) {
			sum += numbers[i];
		}
		return sum / numbers.length;
		
	}
	// sample standard deviation of percolation threshold
	public double stddev() {
		int sum = 0;
		double meanNum = mean();
		
		for (int i = 0; i < numbers.length; i++) {
			sum += Math.pow((numbers[i] - meanNum), 2);
		}
		
		return Math.pow(sum / (numbers.length - 1), 1/2);
	}
	// low  end point of 95% confidence interval
	public double confidenceLo() {
		double meanNum = mean();
		double s = stddev();
		return meanNum - 1.96 * s / Math.pow(numbers.length, 1/2);
	}
	// high end point of 95% confidence interval
	public double confidenceHi() {
		double meanNum = mean();
		double s = stddev();
		return meanNum + 1.96 * s / Math.pow(numbers.length, 1/2);
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
