import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {
    private static final double PERCOLATION_CONFIDENCE_VALUE = 1.96;
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

        for (int times = 0; times < mTrials; times++) {
            Percolation percolation = new Percolation(mDimention);

            while (!percolation.percolates()) {
                int randomSite = StdRandom.uniform(0, mDimention * mDimention);
                if (percolation.isOpen(randomSite / mDimention + 1, randomSite % mDimention + 1))
                    continue;
                percolation.open(randomSite / mDimention + 1, randomSite % mDimention + 1);
            }

            mThreshold[times] = (double) percolation.numberOfOpenSites() /
                                (double) (mDimention * mDimention);
        }

        mMean = StdStats.mean(mThreshold);
        mStddev = StdStats.stddev(mThreshold);
    }

    public double mean() {
        return mMean;
    }

    public double stddev() {
        return mStddev;
    }

    public double confidenceLo() {
        return mMean - (PERCOLATION_CONFIDENCE_VALUE * mStddev / Math.sqrt(mTrials));
    }

    public double confidenceHi() {
        return mMean + (PERCOLATION_CONFIDENCE_VALUE * mStddev / Math.sqrt(mTrials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("incorrect arguments");
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);

            PercolationStats percolationStats = new PercolationStats(n, trials);
            StdOut.println("mean                    = " + percolationStats.mean());
            StdOut.println("stddev                  = " + percolationStats.stddev());

            double confidenceLo = percolationStats.confidenceLo();
            double confidenceHi = percolationStats.confidenceHi();
            StdOut.println("95% confidence interval = [" + confidenceLo + ", " + confidenceHi + "]");
        } catch (NumberFormatException e) {
            StdOut.println("invalid argument format");
            return;
        }
    }
}