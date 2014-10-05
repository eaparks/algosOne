/**
 * Created with IntelliJ IDEA.
 * User: edward
 * Date: 9/20/14
 * Time: 8:53 AM
 */
public class Percolation {

    private int TOP_VIRTUAL;
    private int BOTTOM_VIRTUAL;
    private static final boolean BLOCKED = false;
    private boolean[] sites;
    private WeightedQuickUnionUF wqUnionFind;
    private int n;

    public Percolation(final int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than zero.");
        }
        this.n = n;
        TOP_VIRTUAL = n * n;
        BOTTOM_VIRTUAL = n * n + 1;
        sites = new boolean[n * n + 2];         // Adding virtual top and bottom
        for (int i = 0; i < sites.length; i++) {
            sites[i] = BLOCKED;
        }
        sites[TOP_VIRTUAL] = !BLOCKED;
        sites[BOTTOM_VIRTUAL] = !BLOCKED;

        wqUnionFind = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int i, int j) {

        checkRange(i, j, n);
        if (!isOpen(i, j)) {
            sites[indexFromRowCol(i, j)] = !BLOCKED;
            unionWithNeighbors(i, j);
        }
    }

    private int indexFromRowCol(final int i, final int j) {

        return (i - 1) * n + j - 1;
    }

    private void unionWithNeighbors(int i, int j) {

        unionWithVirtualNeighbors(i, j);
        unionWithAbove(i, j);
        unionWithBelow(i, j);
        unionWithLeft(i, j);
        unionWithRight(i, j);
    }

    private void unionWithVirtualNeighbors(int i, int j) {

        if (i == 1) {
            wqUnionFind.union(indexFromRowCol(i, j), TOP_VIRTUAL);
        }

        if (i == n) {
            wqUnionFind.union(indexFromRowCol(i, j), BOTTOM_VIRTUAL);
        }
    }

    private void unionWithRight(int i, int j) {

        if (j < n) {
            if (isOpen(i, j)) {
                wqUnionFind.union(indexFromRowCol(i, j), (i - 1) * n + j);
            }
        }
    }

    private void unionWithLeft(int i, int j) {

        if (j > 1) {
            if (isOpen(i, j - 1)) {
                wqUnionFind.union(indexFromRowCol(i, j), (i - 1) * n + j - 2);
            }
        }
    }

    private void unionWithBelow(int i, int j) {

        if (i < n) {
            if (isOpen(i + 1, j)) {
                wqUnionFind.union(indexFromRowCol(i, j), i * n + j - 1);
            }
        }
    }

    private void unionWithAbove(int i, int j) {

        if (i > 1) {
            if (isOpen(i - 1, j)) {
                wqUnionFind.union(indexFromRowCol(i, j), (i - 2) * n + j - 1);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {

        checkRange(i, j, n);

        return sites[indexFromRowCol(i, j)];
    }

    /**
     * A full site is an open site that can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down) open sites.
     *
     */
    public boolean isFull(int i, int j) {

        return isOpen(i, j) && wqUnionFind.connected(indexFromRowCol(i, j), TOP_VIRTUAL);
    }

    // does the system percolate?
    public boolean percolates() {

        return wqUnionFind.connected(TOP_VIRTUAL, BOTTOM_VIRTUAL);
    }

    private int rand() {

        return 1 + StdRandom.uniform(n);
    }

    private void checkRange(int input1, int input2, int max) {

        if (input1 <= 0 || input1 > max || input2 <= 0 || input2 > max) {
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
