import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

/**
 * Find a solution to the initial board (using the A* algorithm).
 */
public class Solver {

    private Node lastNode = null;

    /**
     * If no initial board is supplied, throws IllegalArgumentException.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board is null");
        }
        MinPQ<Node> Q = new MinPQ<Node>();
        MinPQ<Node> SQ = new MinPQ<Node>();
        Q.insert(new Node(initial, null));
        SQ.insert(new Node(initial.twin(), null));

        boolean sSolvable = false;
        boolean solvable = false;
        while (!solvable && !sSolvable) { //!solvable &&
            lastNode = expand(Q);
            solvable = (lastNode != null);
            sSolvable = (expand(SQ) != null);
        }
    }

    /**
     * Gets all possible solutions from current move.
     */
    private Node expand(MinPQ<Node> queue) {
        if (queue.isEmpty()) return null;
        Node current = queue.delMin();
        if (current.board.isGoal()) return current;

        for (Board b : current.board.neighbors()) {
            if (current.previous == null || !b.equals(current.previous.board)) {
                queue.insert(new Node(b, current));
            }
        }
        return null;
    }

    /**
     * Is the initial board solvable?
     */
    public boolean isSolvable() {
        return lastNode != null;
    }

    /**
     * Min number of moves to solve initial board; -1 if no solution.
     */
    public int moves() {
        if (lastNode != null) return lastNode.numMoves;
        else return -1;
    }

    /**
     * Sequence of boards in a shortest solution; null if no solution.
     */
    public Iterable<Board> solution() {
        if (lastNode != null) {
            Stack<Board> res = new Stack<Board>();
            for (Node tail = lastNode; tail != null; tail = tail.previous) {
                res.push(tail.board);
            }
            return res;
        } else return null;
    }


    /**
     * Represents one move in game.
     */
    private static class Node implements Comparable<Node> {
        private final Board board;
        private final int numMoves;
        private final Node previous;

        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            if (previous == null) this.numMoves = 0;
            else this.numMoves = previous.numMoves + 1;
        }

        @Override
        public int compareTo(Node node) {
            return (this.board.manhattan() - node.board.manhattan()) + (this.numMoves - node.numMoves);
        }

    }

}























