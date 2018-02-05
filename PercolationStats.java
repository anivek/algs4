import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int mDimention;
    private final int mTrials;
    private double[] mThreshold;
    private double mStddev;
    private double mMean;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        mDimention = n;
        mTrials = trials;
        mThreshold = new double[trials];
    }

    public double mean() {
        for (int times = 0; times < mTrials; times++) {
            Percolation percolation = new Percolation(mDimention);

            while (!percolation.percolates()) {
                int randomSite = StdRandom.uniform(0, mDimention * mDimention);
                if (percolation.isOpen(randomSite / mDimention, randomSite % mDimention))
                    continue;
                percolation.open(randomSite / mDimention, randomSite % mDimention);
            }

            mThreshold[times] = (double) percolation.numberOfOpenSites() /
                                (double) (mDimention * mDimention);
        }

        mMean = StdStats.mean(mThreshold);
        StdOut.println("mean                    = " + mMean);

        return mMean;
    }

    public double stddev() {
        mStddev = StdStats.stddev(mThreshold);
        StdOut.println("stddev                  = " + mStddev);

        return mStddev;
    }

    public double confidenceLo() {
        return mMean - (1.96 * mStddev / Math.sqrt(mTrials));
    }

    public double confidenceHi() {
        return mMean + (1.96 * mStddev / Math.sqrt(mTrials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("incorrect arguments");
            return;
        }

        try {
            int n = Integer.valueOf(args[0]);
            int trials = Integer.valueOf(args[1]);
            PercolationStats percolationStats = new PercolationStats(n, trials);
            percolationStats.mean();
            percolationStats.stddev();

            double confidenceLo = percolationStats.confidenceLo();
            double confidenceHi = percolationStats.confidenceHi();
            StdOut.println("95% confidence interval = [" + confidenceLo + ", " + confidenceHi + "]");
        } catch (NumberFormatException e) {
            StdOut.println("invalid argument format");
            return;
        }
    }
}