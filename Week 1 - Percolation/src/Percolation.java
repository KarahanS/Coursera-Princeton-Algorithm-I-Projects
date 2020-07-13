import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	// blocked site --> false
	// open site --> true
	// full open site --> uf.find(p) == uf.find(virtual top site)
	private boolean[][] grid;
	private final WeightedQuickUnionUF uf;
	private final WeightedQuickUnionUF uf_backwash;
	private int openSites;
	

	// Creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		openSites = 0;
		if (n <= 0)
			throw new IllegalArgumentException();
		this.grid = new boolean[n][n];
		uf = new WeightedQuickUnionUF((grid.length * grid.length) + 2); // +2 - virtual bottom side and virtual top side
		uf_backwash = new WeightedQuickUnionUF((grid.length * grid.length) + 1);  // only virtual top side
	}

	// Opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		int i = row - 1;
		int j = col - 1;

		if (isValid(i, j)) {
			if (this.grid[i][j] == true)
				return;
			this.grid[i][j] = true;
			openSites++;
			if (i == 0) {
				uf_backwash.union(xyTo1D(i, j), (grid.length * grid.length) ); // virtual top side
				uf.union(xyTo1D(i, j), (grid.length * grid.length) + 1); // Binding first row elements to the virtual
																			// top site
			}
			if (i == grid.length - 1) {
				uf.union(xyTo1D(i, j), (grid.length * grid.length)); // Binding last row elements to the virtual bottom
																		// site
			}

			// left
			if (j > 0 && grid[i][j - 1]) {
				uf_backwash.union(xyTo1D(i, j), xyTo1D(i, j - 1));
				uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));

			}
			// right
			if (j < grid.length - 1 && grid[i][j + 1]) {
				uf_backwash.union(xyTo1D(i, j), xyTo1D(i, j + 1));
				uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
			}
			// above
			if (i > 0 && grid[i - 1][j]) {
				uf_backwash.union(xyTo1D(i, j), xyTo1D(i - 1, j));
				uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
			}
			// below
			if (i < grid.length - 1 && grid[i + 1][j]) {
				uf_backwash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
				uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
			}

		} else
			throw new IllegalArgumentException();
	}

	// is the site(row, col) open?
	public boolean isOpen(int row, int col) {
		if (isValid(row - 1, col - 1)) {
			return (grid[row - 1][col - 1]);
		} else
			throw new IllegalArgumentException();
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (isValid(row - 1, col - 1)) {
			return (uf_backwash.find(xyTo1D(row - 1, col - 1)) == uf_backwash.find(grid.length * grid.length));
		} else
			throw new IllegalArgumentException();

	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return (uf.find(grid.length * grid.length) == uf.find(grid.length * grid.length + 1));
	}

	private int xyTo1D(int i, int j) {
		return (grid.length * i) + j;
	}

	private boolean isValid(int i, int j) {
		return !(i < 0 || i > grid.length - 1 || j < 0 || j > grid.length - 1);
	}

}
