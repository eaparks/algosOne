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
     * @param N - the gridSize
     * @param T - the number of experiments to execute
     */
    public PercolationStats(int N, int T) {

        if(N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Experiments count and gridSize must be > 0");
        }
        Percolation perc;
        experimentsCount = T;
        int[] sitesOpened = new int[T];
        percThreshold = new double[T];

        int randomI;
        int randomJ;
        for(int i = 0; i < T; i++) {

            int openCount = 0;
            perc = new Percolation(N);
            while(!perc.percolates()) {
                randomI = StdRandom.uniform(1, N + 1);
                randomJ = StdRandom.uniform(1, N + 1);
                if (!perc.isOpen(randomI, randomJ)) {
                    openCount++;
                    perc.open(randomI, randomJ);
                }
            }
            sitesOpened[i] = openCount;
            percThreshold[i] = (double) sitesOpened[i] / ((double) (N * N));
        }
    }

/*  Begin required methods */

    /**
     *
     * @return the mean perc threshold.
     */
    public double mean() {

        return StdStats.mean(percThreshold);
    }

    /**
     *
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {

        if(experimentsCount == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(percThreshold);
    }

    /**
     *
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {

        return mean() - (1.96 * stddev()/Math.sqrt(experimentsCount));
    }

    /**
     *
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {

        return mean() + (1.96 * stddev()/Math.sqrt(experimentsCount));
    }

    // test client, described below

    /**
     * Test client.
     *
     * @param args - gridSize followed by experiment count
     */
    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);         // N-by-N percolation system
        int T = Integer.parseInt(args[1]);         // T experiments
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval\t= " + stats.confidenceLo() + ", " + stats.confidenceHi());

    }

 /* End of required methods */

}
