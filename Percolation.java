import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final boolean SITE_BLOCKED = false;
    private static final boolean SITE_OPENED = true;

    private final WeightedQuickUnionUF union;
    private boolean[] grid;
    private boolean[] fullArray;
    private final int dimention;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();

        dimention = n;
        grid = new boolean[n * n];
        fullArray = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            grid[i] = SITE_BLOCKED;
            fullArray[i] = false;
        }

        union = new WeightedQuickUnionUF(n * n);
    }

    private boolean isOutOfBoundary(int row, int col) {
        if (row < 0 || row >= dimention || col < 0 || col >= dimention)
            return true;
        return false;
    }

    private void updateUnion(int pRow, int pCol, int qRow, int qCol) {
        if (isOpen(qRow + 1, qCol + 1)) {
            if (!union.connected(pRow * dimention + pCol, qRow * dimention + qCol))
                union.union(pRow * dimention + pCol, qRow * dimention + qCol);
        }
    }

    public boolean isOpen(int row, int col) {
        row -= 1;
        col -= 1;

        if (isOutOfBoundary(row, col))
            throw new java.lang.IllegalArgumentException();

        return grid[row * dimention + col] == SITE_OPENED;
    }

    public boolean isFull(int row, int col) {
        row -= 1;
        col -= 1;

        if (isOutOfBoundary(row, col))
            throw new java.lang.IllegalArgumentException();

        int site = row * dimention + col;

        if (fullArray[site])
            return true;

        // iterate sites in top row
        for (int i = 0; i < dimention; i++) {
            if (grid[i] == SITE_OPENED && union.connected(site, i)) {
                // cache isFull result
                fullArray[site] = true;
                return true;
            }
        }

        return false;
    }

    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        row -= 1;
        col -= 1;

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
            if (grid[row * dimention + col] == SITE_OPENED && isFull(row + 1, col + 1))
                return true;

        return false;
    }
}