import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int _dimention;
    private int _trials;
    private double[] _threshold;
    private double _stddev;
    private double _mean;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        _dimention = n;
        _trials = trials;
        _threshold = new double[trials];
    }

    public double mean() {
        for (int times = 0; times < _trials; times++) {
            Percolation percolation = new Percolation(_dimention);
            //StdOut.println("times: " + times);
            while (!percolation.percolates()) {
                int randomSite = StdRandom.uniform(0, _dimention * _dimention);
                if (percolation.isOpen(randomSite / _dimention, randomSite % _dimention))
                    continue;
                percolation.open(randomSite / _dimention, randomSite % _dimention);
            }

            _threshold[times] = (double)percolation.numberOfOpenSites() / (double)(_dimention * _dimention);
        }

        _mean = StdStats.mean(_threshold);
        StdOut.println("mean                    = " + _mean);

        return _mean;
    }

    public double stddev() {
        _stddev = StdStats.stddev(_threshold);
        StdOut.println("stddev                  = " + _stddev);

        return _stddev;
    }

    public double confidenceLo() {
        return _mean - (1.96 * _stddev / Math.sqrt(_trials));
    }

    public double confidenceHi() {
        return _mean + (1.96 * _stddev / Math.sqrt(_trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("incorrect arguments");
            System.exit(0);
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
            System.exit(-1);
        }
    }
}