import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

class Percolation {
    private WeightedQuickUnionUF uf;
    private int size;
    private String[] sites;
    private int len;
    private int openNum;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("IlleagalArgument: n should be larger than 0.");
        }

        openNum = 0;
        size = n;
        len = n * n;

        uf = new WeightedQuickUnionUF(len + 2);
        sites = new String[len + 2];

        for (int i = 0; i < len; i++) {
            sites[i] = "blocked";
        }

        sites[len] = "open";
    }

    private int sizeIndex(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    private void examinateArgument(int row, int col) {
        if (row > size || col > size || row < 1 || col < 1) {
            throw new IllegalArgumentException("IlleagalArgument: row or col.");
        }
    }

    public boolean isFull(int row, int col) {
        examinateArgument(row, col);
        int index = sizeIndex(row, col);
        return uf.connected(index, len);
    }

    public boolean isOpen(int row, int col) {
        examinateArgument(row, col);
        int index = sizeIndex(row, col);
        return sites[index].equals("open");
    }

    public void connect(int p, int q) {
        uf.union(p, q);
    }

    public void open(int row, int col) {
        examinateArgument(row, col);

        int index = sizeIndex(row, col);

        if (!isOpen(row, col)) {

            openNum++;

            sites[index] = "open";

            // connect to the virtual top
            if (row == 1) {
                connect(index, len);
            }

            // connect to the virtual bottom
            if (row == size) {
                connect(index, len + 1);
            }

            // left
            if (row > 1 && isOpen(row - 1, col)) {
                int leftIndex = sizeIndex(row - 1, col);
                connect(index, leftIndex);
            }

            // right
            if (row < size && isOpen(row + 1, col)) {
                int rightIndex = sizeIndex(row + 1, col);
                connect(index, rightIndex);
            }

            // top
            if (col > 1 && isOpen(row, col - 1)) {
                int topIndex = sizeIndex(row, col - 1);
                connect(index, topIndex);
            }

            // bottom
            if (col < size && isOpen(row, col + 1)) {
                int bottomIndex = sizeIndex(row, col + 1);
                connect(index, bottomIndex);
            }
        }
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        // return whether virtual top and virtual bottom is connected
        return uf.connected(len, len + 1);
    }

    public static void main(String[] args) {

        Percolation perc = new Percolation(10);
        StdOut.println(perc.percolates());
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);
        perc.open(4, 1);
        StdOut.println(perc.percolates());
        StdOut.println(perc.isFull(4, 1));
        perc.open(5, 1);
        perc.open(6, 1);
        perc.open(7, 1);
        perc.open(8, 1);
        perc.open(9, 1);
        perc.open(10, 1);
        StdOut.println(perc.numberOfOpenSites());
        StdOut.println(perc.percolates());
    }
}
