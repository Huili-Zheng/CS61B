package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;
import java.util.List;

public class PercolationStats {
    private int sizes;
    private int times;
    private double[] pList;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.sizes = N;
        this.times = T;
        this.pList = new double[T];
        for (int i = 0; i < times; i++) {
            Percolation percolation = pf.make(sizes);
            while (!percolation.percolates() && percolation.numberOfOpenSites() <= sizes*sizes) {
                int rand = StdRandom.uniform(0, sizes * sizes);
                percolation.open(rand/sizes, rand%sizes);
            }
            pList[i] = percolation.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(pList);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(pList);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }


}
