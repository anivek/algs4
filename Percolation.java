import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int SITE_BLOCKED = 0;
    private static final int SITE_OPENED = 1;

    private WeightedQuickUnionUF union;
    private int[] grid;
    private int dimention = 0;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();

        dimention = n;
        grid = new int[n * n];
        for (int i = 0; i < n * n; i++)
            grid[i] = SITE_BLOCKED;

        union = new WeightedQuickUnionUF(n * n);
    }

    private boolean isOutOfBoundary(int row, int col) {
        if (row < 0 || row >= dimention || col < 0 || col >= dimention)
            return true;
        return false;
    }

    private void updateUnion(int pRow, int pCol, int qRow, int qCol) {
        if (isOpen(qRow, qCol))
            union.union(pRow * dimention + pCol, qRow * dimention + qCol);
    }

    public boolean isOpen(int row, int col) {
        if (isOutOfBoundary(row, col))
            throw new java.lang.IllegalArgumentException();

        return grid[row * dimention + col] == SITE_OPENED;
    }

    public boolean isFull(int row, int col) {
        if (isOutOfBoundary(row, col))
            throw new java.lang.IllegalArgumentException();

        int site = row * dimention + col;

        // iterate sites in top row
        for (int i = 0; i < dimention; i++)
            if (grid[i] == SITE_OPENED && union.connected(site, i))
                return true;

        return false;
    }

    public void open(int row, int col) {
        if (isOutOfBoundary(row, col))
            throw new java.lang.IllegalArgumentException();

        grid[row * dimention + col] = SITE_OPENED;

        if (row >= 1) // check up neighbor
            updateUnion(row, col, row - 1, col);
        if (row <= dimention - 2) // check down neighbor
            updateUnion(row, col, row + 1, col);
        if (col >= 1) // check left neighbor
            updateUnion(row, col, row, col - 1);
        if (col <= dimention - 2) // check right neighbor
            updateUnion(row, col, row, col + 1);

        numberOfOpenSites++;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // iterate sites in bottom row
        for (int row = dimention - 1, col = 0; col < dimention; col++)
            if (grid[row * dimention + col] == SITE_OPENED && isFull(row, col))
                return true;

        return false;
    }
}