/**
 * Created with IntelliJ IDEA.
 * User: edward
 * Date: 9/20/14
 * Time: 8:53 AM
 */
public class Percolation {

    private boolean[] sites;
    private WeightedQuickUnionUF wqUnionFind;
    private int N;
    private static boolean BLOCKED = false;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than zero.");
        }
        this.N = N;
        sites = new boolean[N * N + 2];         // Adding virtual top and bottom
        for (int i = 0; i < sites.length; i++) {
            sites[i] = BLOCKED;
        }
        sites[N * N] = !BLOCKED;    // top virtual
        sites[N * N + 1] = !BLOCKED;    // bottom virtual

        wqUnionFind = new WeightedQuickUnionUF(N * N + 2);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {

        checkRange(i, N);
        checkRange(j, N);
        if (isOpen(i, j)) {
            return;
        }
        sites[(i - 1) * N + j - 1] = !BLOCKED;
//        wqUnionFind.find((i - 1) * N + j - 1);

        // If top row, connect to virtual top
        if (i == 1) {
            wqUnionFind.union((i - 1) * N + j - 1, N * N);
        }

        // If bottom row, connect to virtual bottom
        if (i == N) {
            wqUnionFind.union((i - 1) * N + j - 1, N * N + 1);
        }

        // Look for open neighbors and union with them
        //above
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                wqUnionFind.union((i - 1) * N + j - 1, (i - 2) * N + j - 1);
            }
        }
        //below
        if (i < N) {
            if (isOpen(i, j)) {
                wqUnionFind.union((i - 1) * N + j - 1, i * N + j - 1);
            }
        }
        //left
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                wqUnionFind.union((i - 1) * N + j - 1, (i - 1) * N + j - 2);
            }
        }
        //right
        if (j < N) {
            if (isOpen(i, j)) {
                wqUnionFind.union((i - 1) * N + j - 1, (i - 1) * N + j);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {

        checkRange(i, N);
        checkRange(j, N);

        return sites[(i - 1) * N + j - 1];
    }

    // is site (row i, column j) full?
    // A full site is an open site that can be connected to an open site
    // in the top row via a chain of neighboring (left, right, up, down) open sites.
    public boolean isFull(int i, int j) {

        return isOpen(i, j) && wqUnionFind.connected((i - 1) * N + j -1, N * N);
    }

    // does the system percolate?
    public boolean percolates() {

        return wqUnionFind.connected(N * N, N * N + 1);
    }

    private int rand() {

        return 1 + StdRandom.uniform(N);
    }

    private void checkRange(int input, int max) throws IndexOutOfBoundsException {

        if (input <= 0 || input > max) {
            throw new IndexOutOfBoundsException("out of range");
        }
    }

    public static void main(String[] args) {

        Percolation p = new Percolation(22);
        int counter = 0;
        while (!p.percolates()) {
            int row = p.rand();
            int col = p.rand();
            System.out.println("Opening: [" + row + "," + col + "]");
            p.open(row, col);
            counter++;
        }
        System.out.println("It percolates after " + counter);

    }

}
