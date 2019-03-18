import edu.princeton.cs.algs4.*;

public class Percolation {
	private String [][] grid;
	private int len;
	
	public Percolation(int n) {
		if (n < 1) 
		{
			throw new IllegalArgumentException("Illegal Argument: n in Percolation method");
		}
		
		len = n;
		grid = new String[n][n];
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
	
	public void open(int row, int col) {
		examinateRowAndCol(row, col);
		if (!isOpen(row, col)) {
			grid[row][col] = "open";
		}
	}
	
	public boolean isOpen(int row, int col) {
		examinateRowAndCol(row, col);
		if (grid[row][col] == "open") {
			return true;
		} else {
			return false;
		}
	}
	// this method needs to be optimized
	// Also, it is not working very well, 
	// because it doesn't concern that the site could be open from the left
	public boolean isFull(int row, int col) {
		examinateRowAndCol(row, col);
		// now it is not optimized at all;
		int currentRow = row;
		int currentCol = col;
		
		if (!isOpen(currentRow, currentCol)) {
			return false;
		}
		if (row == 1) {
			return true;
		}
		
		// from Top
		if (isFull(currentRow - 1, currentCol)) {
			return true;
		}
		
		// from Right
		if (currentCol < (len - 1) && isFull(currentRow, currentCol + 1)) {
			return true;
		}
		
		return false;
	}
	
	public int numberOfOpenSites() {
		int num = 0;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (grid[i][j] == "open") {
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
		double val = Math.floor(StdRandom.uniform() * len);
		return (int)(val);
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
