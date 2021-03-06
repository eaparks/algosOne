/**
 * Created with IntelliJ IDEA.
 * User: edward
 * Date: 9/21/14
 * Time: 9:35 AM
 */
public class PercolationStats {

    private double[] percThreshold;
    private int experimentsCount;


    /**
     * perform T independent computational experiments on an gridSize-by-gridSize grid
     *
     * @param n - the gridSize
     * @param t - the number of experiments to execute
     */
    public PercolationStats(final int n, final int t) {

        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Experiments count and gridSize must be > 0");
        }
        Percolation perc;
        experimentsCount = t;
        int[] sitesOpened = new int[t];
        percThreshold = new double[t];

        int randomI;
        int randomJ;
        for (int i = 0; i < t; i++) {

            int openCount = 0;
            perc = new Percolation(n);
            while (!perc.percolates()) {
                randomI = StdRandom.uniform(1, n + 1);
                randomJ = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(randomI, randomJ)) {
                    openCount++;
                    perc.open(randomI, randomJ);
                }
            }
            sitesOpened[i] = openCount;
            percThreshold[i] = (double) sitesOpened[i] / ((double) (n * n));
        }
    }

/*  Begin required methods */

    /**
     * @return the mean perc threshold.
     */
    public double mean() {

        return StdStats.mean(percThreshold);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {

        if (experimentsCount == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(percThreshold);
    }

    /**
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {

        return mean() - (1.96 * stddev() / Math.sqrt(experimentsCount));
    }

    /**
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {

        return mean() + (1.96 * stddev() / Math.sqrt(experimentsCount));
    }

    // test client, described below

    /**
     * Test client.
     *
     * @param args - gridSize followed by experiment count
     */
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);         // N-by-N percolation system
        int t = Integer.parseInt(args[1]);         // T experiments
        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval\t= " + stats.confidenceLo() + ", " + stats.confidenceHi());

    }

 /* End of required methods */

}
