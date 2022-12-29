package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBackWash;
    private int openNum;
    private boolean[][] grid;
    private int N;
    private int[][] directions = {{1,0},{0,1},{0,-1},{-1,0}};

    private int xyTo1D(int row, int col) {
        return row * grid.length + col;
    }


    private boolean validXY(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            return false;
        }
        return true;
    }


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N < 0 ) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        this.openNum = 0;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N*N + 2);
        ufNoBackWash = new WeightedQuickUnionUF(N*N + 2);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            openNum += 1;
            grid[row][col] = true;
            for (int[] dir : directions) {
                int n_row = row + dir[0];
                int n_col = col + dir[1];
                if (n_col < 0 || n_col >= N) {
                    continue;
                }
                if (n_row == -1) {
                    uf.union(N * N, xyTo1D(row, col));
                    ufNoBackWash.union(N * N, xyTo1D(row, col));
                }
                else if (n_row == N) {
                    uf.union(N * N + 1, xyTo1D(row, col));
                }
                if (validXY(n_row, n_col)) {
                    if (isOpen(n_row, n_col) && !ufNoBackWash.connected(xyTo1D(n_row, n_col), xyTo1D(row, col))) {
                        uf.union(xyTo1D(n_row, n_col), xyTo1D(row, col));
                        ufNoBackWash.union(xyTo1D(n_row, n_col), xyTo1D(row, col));
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validXY(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validXY(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return ufNoBackWash.connected(N*N, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(N*N, N*N+1);
    }

}
