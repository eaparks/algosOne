import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;

public class PercolationTest {

    @Test
    public void testIsFull() throws Exception {

        int counter = 0;
        In in = new In(new File("/Users/edward/IdeaProjects/AlgosPtOne_take2/data/input6.txt"));
        int n = in.readInt();
        System.out.println("Grid size: " + n);
        Percolation p = new Percolation(n);
        assertFalse(p.isFull(1, 1));
        assertFalse(p.isOpen(1, 1));

        while (!p.percolates() && in.hasNextLine()) {
            String line = in.readLine();
            if (line != null && line.length() > 0) {

                int row = Integer.parseInt(line.substring(1, 2));
                int col = Integer.parseInt(line.substring(3, 4));
                System.out.println("Opening: [" + row + "," + col + "]");
                p.open(row, col);
                counter++;
            }
        }
        System.out.println("It percolates after " + counter);
    }
}