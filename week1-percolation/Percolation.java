import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class represents a 2D grid of cells that
 * can be in an open or closed state. Its purpose is
 * to be able to tell if a certain system percolates,
 * i.e. water poured in the top can reach the bottom.
 */
public class Percolation {

    private final int gridLength;
    private final boolean[] openCells;
    private final WeightedQuickUnionUF topToBottom;
    private final WeightedQuickUnionUF topToPoint;

    private final int virtualTopCellIndex;
    private final int virtualBottomCellIndex;

    private int n;

    /**
     * Constructor, accepts in length of grid.
     * <p>
     *
     * @param gridLength <= 0
     */
    public Percolation(int gridLength) {
        if (gridLength <= 0) {
            throw new IllegalArgumentException("Attempting to make array with length " + gridLength);
        }
        this.gridLength = gridLength;
        this.virtualTopCellIndex = 0;
        this.virtualBottomCellIndex = gridLength * gridLength + 2;

        this.openCells = new boolean[virtualBottomCellIndex + 1];
        this.topToBottom = new WeightedQuickUnionUF(virtualBottomCellIndex + 1);
        this.topToPoint = new WeightedQuickUnionUF(virtualBottomCellIndex + 1);

        /* Open the virtual top and bottom cells */
        openCells[virtualTopCellIndex] = true;
        openCells[virtualBottomCellIndex] = true;
    }

    /**
     * Open method will open a specific cell and,
     * if appropriate unions it to open cardinal neighbours.
     * <p>
     *
     * @param i coordinate [1, gridlenth]
     * @param j coordinate [1, gridlenth]
     */
    public void open(int i, int j) {
        if (isOpen(i, j)) {
            return;
        }

        n++;
        int cellIndex = coordsToIndex(i, j);
        openCells[cellIndex] = true;
        unionTopmostBottommost(i, cellIndex);
        unionTopBottomLeftRight(i, j, cellIndex);
    }

    private void unionTopmostBottommost(int i, int cell) {
        if (i == 1) {
            topToBottom.union(cell, virtualTopCellIndex);
            topToPoint.union(cell, virtualTopCellIndex);
        }
        if (i == gridLength) {
            topToBottom.union(cell, virtualBottomCellIndex);
        }
    }

    private void unionTopBottomLeftRight(int i, int j, int cellIndex) {
        /* to make union with another cell
        there must be a place in the grid */
        if (i > 1 && openCells[cellIndex - gridLength]) {
            topToBottom.union(cellIndex, cellIndex - gridLength);
            topToPoint.union(cellIndex, cellIndex - gridLength);
        }
        if (i < gridLength && openCells[cellIndex + gridLength]) {
            topToBottom.union(cellIndex, cellIndex + gridLength);
            topToPoint.union(cellIndex, cellIndex + gridLength);
        }
        if (j > 1 && openCells[cellIndex - 1]) {
            topToBottom.union(cellIndex, cellIndex - 1);
            topToPoint.union(cellIndex, cellIndex - 1);
        }
        if (j < gridLength && openCells[cellIndex + 1]) {
            topToBottom.union(cellIndex, cellIndex + 1);
            topToPoint.union(cellIndex, cellIndex + 1);
        }
    }

    /**
     * Returns true if the cell at coordinates (i,j) is open,
     * false if it is closed.
     * <p>
     *
     * @param i coordinate [1, gridlenth]
     * @param j coordinate [1, gridlenth]
     */
    public boolean isOpen(int i, int j) {
        int cellIndex = coordsToIndex(i, j);
        return openCells[cellIndex];
    }

    /**
     * Returns true if the cell at (i,j) is connected to
     * the virtual top cell, false if it is not connected.
     * <p>
     *
     * @param i coordinate [1, gridlenth]
     * @param j coordinate [1, gridlenth]
     */
    public boolean isFull(int i, int j) {
        if (!isOpen(i, j)) {
            return false;
        }
        int cellIndex = coordsToIndex(i, j);
        return topToPoint.connected(cellIndex, virtualTopCellIndex);
    }

    public int numberOfOpenSites() {
        return n;
    }

    /**
     * Returns true if the grid can be percolated,
     * i.e. there is a connection between the virtual top and bottom cell.
     */
    public boolean percolates() {
        return topToBottom.connected(virtualTopCellIndex, virtualBottomCellIndex);
    }

    /**
     * Converts the 2D coordinate (i,j) into the equivalent 1D index.
     * <p>
     *
     * @param i coordinate [1, gridlenth]
     * @param j coordinate [1, gridlenth]
     */
    private int coordsToIndex(int i, int j) {
        validCoords(i, j);
        return (i - 1) * gridLength + j;
    }

    /**
     * @param i coordinate [1, gridlenth]
     * @param j coordinate [1, gridlenth]
     */
    private void validCoords(int i, int j) {
        if (!(beetweenInclusive(1, i, gridLength) && beetweenInclusive(1, j, gridLength))) {
            throw new IllegalArgumentException("Attempting to access " +
                    "a cell (" + i + ", " + j + ") outside of the " + gridLength + " grid");
        }
    }

    private static boolean beetweenInclusive(int low, int number, int high) {
        return low <= number && number <= high;
    }

}
