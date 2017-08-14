import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * PercolationStats accepts a number of trials
 * and a grid size and determines what proportion of cells must be
 * open in order for the system to percolate.
 */
public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double hi;
    private final double lo;

    /**
     * Constructor, accepts the size of the grid and number of
     * trials, then performs them.
     * <p>
     *
     * @param gridSize       <= 0
     * @param numberOfTrials <= 0
     */
    public PercolationStats(int gridSize, int numberOfTrials) {
        if (gridSize <= 0 || numberOfTrials <= 0) {
            throw new IllegalArgumentException("Input must be a positive integer greater than 0");
        }

        double[] results = new double[numberOfTrials];

        for (int i = 0; i < numberOfTrials; i++) {
            results[i] = performTrial(gridSize);
        }

        mean = StdStats.mean(results);
        stddev = (numberOfTrials == 1) ? Double.NaN : StdStats.stddev(results);
        lo = mean - (1.96 * stddev / Math.sqrt(numberOfTrials));
        hi = mean + (1.96 * stddev / Math.sqrt(numberOfTrials));
    }

    /**
     * Returns the mean proportion of cells opened to percolate the system.
     */
    public double mean() {
        return mean;
    }

    /**
     * Returns the standard deviation of proportion of
     * cells opened to percolate the system.
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Returns the lower, 95% confidence interval of open
     * cell proportion, assumes trial number is over 30.
     */
    public double confidenceLo() {
        return lo;
    }

    /**
     * Returns the upper, 95% confidence interval of open
     * cell proportion, assumes trial number is over 30.
     */
    public double confidenceHi() {
        return hi;
    }

    /**
     * Performs the trial by opening cells until the
     * system percolates, adding the proportion to the results.
     */
    private double performTrial(int gridLength) {
        Percolation perc = new Percolation(gridLength);

        while (!perc.percolates()) {
            int i = StdRandom.uniform(1, gridLength + 1);
            int j = StdRandom.uniform(1, gridLength + 1);
            perc.open(i, j);
        }

        return (double) perc.numberOfOpenSites() / (gridLength * gridLength);
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trialCount = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(gridSize, trialCount);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}

