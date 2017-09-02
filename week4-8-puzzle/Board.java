import java.util.LinkedList;
import java.util.List;

/**
 * Class board represents the state of a 2d NxN puzzle board.
 */
public class Board {
    private final int[][] blocks;
    private int moves;
    private int hamming;
    private int manhattan;

    /**
     * Requires the starting blocks in a 2d integer array.
     * If no move count is supplied, it is assumed to be a new board, so moves = 0.
     */
    public Board(int[][] blocks) {
        this(blocks, 0);
    }

    private Board(int[][] blocks, int moves) {
        this.moves = moves;
        this.blocks = deepClone(blocks);
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                if (blocks[row][column] != 0) {
                    int compareValue = blocks[row][column] - 1;
                    int expectedRow = compareValue / blocks.length;
                    int expectedColumn = compareValue - expectedRow * blocks.length;
                /* hamming */
                    if (row != expectedRow || column != expectedColumn) {
                        hamming++;
                    }
                /* manhattan */
                    manhattan += Math.abs(expectedRow - row) + Math.abs(expectedColumn - column);
                }
            }
        }
    }


    /**
     * Returns the dimension (N) of the NxN puzzle board.
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * Returns the Hamming priority function of the board.
     * Defined as number of moves, plus 1 for each block out of position.
     */
    public int hamming() {
        return hamming;
    }

    /**
     * Returns the Manhattan priority function of the board.
     * Defined as number of moves plus row + column offset of each square.
     */
    public int manhattan() {
        return manhattan;
    }

    /**
     * Returns true when board is equal to the goal board.
     */
    public boolean isGoal() {
        return hamming == 0;
    }

    /**
     * A board obtained by exchanging 2 blocks in the same row.
     */
    public Board twin() {
        int[][] copy = deepClone(blocks);
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length - 1; j++) {
                // finds not empty adjacent blocks
                if (copy[i][j] > 0 && copy[i][j + 1] > 0) {
                    swap(copy, i, j, i, j + 1);
                    return new Board(copy, moves);
                }
            }
        }
        throw new IllegalArgumentException("Could not make a twin!");
    }

    /**
     * Returns true if this board is equal to Object that.
     */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (this == y) {
            return true;
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (int row = 0; row < this.dimension(); row++) {
            for (int col = 0; col < this.dimension(); col++) {
                if (this.blocks[row][col] != that.blocks[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * All Boards that can legally be made by a single move.
     */
    public Iterable<Board> neighbors() {
        int spaceRowPos = 0;
        int spaceColPos = 0;

        /* Find the empty square */
        findSpace:
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                if (blocks[row][column] == 0) {
                    spaceRowPos = row;
                    spaceColPos = column;
                    break findSpace;
                }
            }
        }

        List<Board> neighbors = new LinkedList<Board>();

        /* Down */
        if (spaceRowPos < dimension() - 1) {
            int[][] downBlocks = deepClone(blocks);
            swap(downBlocks, spaceRowPos, spaceColPos, spaceRowPos + 1, spaceColPos);
            neighbors.add(new Board(downBlocks, moves + 1));
        }

        /* Up */
        if (spaceRowPos > 0) {
            int[][] upBlocks = deepClone(blocks);
            swap(upBlocks, spaceRowPos, spaceColPos, spaceRowPos - 1, spaceColPos);
            neighbors.add(new Board(upBlocks, moves + 1));
        }

    	/* Left */
        if (spaceColPos > 0) {
            int[][] leftBlocks = deepClone(blocks);
            swap(leftBlocks, spaceRowPos, spaceColPos, spaceRowPos, spaceColPos - 1);
            neighbors.add(new Board(leftBlocks, moves + 1));
        }

    	/* Right */
        if (spaceColPos < dimension() - 1) {
            int[][] rightBlocks = deepClone(blocks);
            swap(rightBlocks, spaceRowPos, spaceColPos, spaceRowPos, spaceColPos + 1);
            neighbors.add(new Board(rightBlocks, moves + 1));
        }

        return neighbors;
    }

    /**
     * Clones all values to another array.
     */
    private int[][] deepClone(int[][] matrix) {
        int[][] matrixClone = matrix.clone();
        for (int i = 0; i < matrixClone.length; i++) {
            matrixClone[i] = matrixClone[i].clone();
        }
        return matrixClone;
    }

    /**
     * Swaps space and block in array.
     */
    private void swap(int[][] downBlocks, int spaceRowPos, int spaceColPos, int rowPos, int colPos) {
        int temp = downBlocks[spaceRowPos][spaceColPos];
        downBlocks[spaceRowPos][spaceColPos] = downBlocks[rowPos][colPos];
        downBlocks[rowPos][colPos] = temp;
    }

    /**
     * A string representation of the board.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(dimension() + " \n ");

        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                sb.append(blocks[row][column]);
                sb.append(" ");
            }

            sb.append("\n ");
        }

        return sb.toString();
    }

}
