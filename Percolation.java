import edu.princeton.cs.algs4.*;

public class Percolation {
	private String [][] grid;
	private int len;
	private WeightedQuickUnionUF uf;
	
	public Percolation(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Illegal Argument: n in Percolation method");
		}
		
		len = n;
		grid = new String[n][n];
		uf = new WeightedQuickUnionUF(n * n);
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = "block";
			}
		}
	}
	
	private void examinateRowAndCol(int row, int col) {
		if (row < 0 || row > len - 1 || col < 0 || col > len - 1) {
			throw new IllegalArgumentException("Illegal Argument: row or col.");
		}
	}
	
	private void connect(int prow, int pcol, int qrow, int qcol) {
		try {
			if (isOpen(prow, pcol) && isOpen(qrow, qcol)) {
				uf.union(root(prow, pcol), root(qrow, qcol));
			}
		} catch (IllegalArgumentException e) {}
	}
	
	private int root (int row, int col) {
		return row * len + col;
	}
	
	public void open(int row, int col) {
		examinateRowAndCol(row, col);

		// if it is already open, doesn't bother to do it again.
		if (isOpen(row, col)) {
			return;
		}

		grid[row][col] = "open";

		// left
		connect(row, col, row, col - 1);

		// right
		connect(row, col, row, col + 1);

		// top
		connect(row, col, row - 1, col);

		// bottom
		connect(row, col, row + 1, col);
	}

	public boolean isOpen(int row, int col) {
		examinateRowAndCol(row, col);
		return grid[row][col] == "open";
	}

	public boolean isFull(int row, int col) {
		examinateRowAndCol(row, col);

		if (!isOpen(row, col)) {
			return false;
		}
		
		if (row == 0) {
			return true;
		}
		
		for (int i = 0; i < len; i++) {

			if (uf.connected(root(row, col), root(0, i))) {
				return true;
			}
		}
		return false;
	}
	
	public int numberOfOpenSites() {
		int num = 0;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (isOpen(i, j)) {
					num++;
				}
			}
		}
		return num;
	}
	public boolean percolates() {
		for(int i = 0; i < len; i++) {

			if (isFull(len - 1, i)) {
				return true;
			}
		}
		return false;
	}
	public int generateRandom() {
		return (int)(Math.floor(StdRandom.uniform() * len));
	}
	public static void main(String[] args) {
		StdOut.println("Please enter the size of grid: ");
		int size = StdIn.readInt();
		
		Percolation perc = new Percolation(size);
		
		while(!perc.percolates()) {
			int row = perc.generateRandom();
			int col = perc.generateRandom();
			perc.open(row, col);
		}
		
		int num = perc.numberOfOpenSites();
		StdOut.println(num + "/" +  (size * size) + " sites are opened when the grid percolates. " );
	}
}
