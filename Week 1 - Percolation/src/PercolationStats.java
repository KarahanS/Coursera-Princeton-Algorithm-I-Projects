import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
	private final double[] results;
	private static final double constant = 1.96;

	// performs independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		results = new double[trials];
		// Trials are going to be implemented here.
		for (int m = 0; m < trials; m++) {

			Percolation perc = new Percolation(n);
			int openSites = 0;
			while (!perc.percolates()) {
				int i = StdRandom.uniform(n);
				int j = StdRandom.uniform(n);
				while (perc.isOpen(i + 1, j + 1)) {
					i = StdRandom.uniform(n);
					j = StdRandom.uniform(n);
				}
				perc.open(i + 1, j + 1);
				openSites++;
			}

			double threshold = (double) openSites / (n * n);
			results[m] = threshold;

		}

	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(results);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if (results.length == 1)
			return Double.NaN;
		return StdStats.stddev(results);
	}

	// low endpoint of %95 confidence interval
	public double confidenceLo() {
		return (this.mean() - (constant * this.stddev() / Math.sqrt(this.results.length)));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return (this.mean() + (constant * this.stddev() / Math.sqrt(this.results.length)));
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		PercolationStats simulation = new PercolationStats(n, t);
		StdOut.println("mean                    = " + simulation.mean());
		StdOut.println("stddev                  = " + simulation.stddev());
		StdOut.println("95% confidence interval = " + "[" + simulation.confidenceLo() + ", " + simulation.confidenceHi()
				+ "]");

	}

}
