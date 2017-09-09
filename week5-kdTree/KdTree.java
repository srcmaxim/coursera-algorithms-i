import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;

/**
 * PointSET represents a set of 2d points in a unit square, using a 2d-tree.
 */
public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private LinkedList<Point2D> contained;
    private Point2D champion;
    private Point2D comparison;
    private int size;

    /**
     * Node class to build the tree
     */
    private class Node {
        Point2D value;
        Node left;
        Node right;
        boolean division;

        Node(Point2D p) {
            value = p;
            left = null;
            right = null;
        }
    }

    /**
     * Constructor, creates a SET of points
     */
    public KdTree() {
        root = null;
    }

    /**
     * Returns true when there are no points in the set
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of points in the set
     */
    public int size() {
        if (root == null) {
            return 0;
        } else {
            return size;
        }
    }

    /**
     * Used to insert a new point into the set
     */
    public void insert(Point2D p) {
        Node newPoint = new Node(p);
        Node currPos = root;

        if (root == null) {
            newPoint.division = VERTICAL;
            root = newPoint;
            size++;
            return;
        }

        while (true) {
            if (p.equals(currPos.value)) {
                return;
            }

            /* Use first bit to determine if level is even or odd */
            if (currPos.division == VERTICAL) {
                /* Slice vertically */
                if (p.x() < currPos.value.x()) {
                    if (currPos.left == null) {
                        newPoint.division = HORIZONTAL;
                        currPos.left = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.left;
                    }
                } else {
                    if (currPos.right == null) {
                        newPoint.division = HORIZONTAL;
                        currPos.right = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.right;
                    }
                }

            } else {
                /* Slice horizontally */
                if (p.y() < currPos.value.y()) {
                    if (currPos.left == null) {
                        newPoint.division = VERTICAL;
                        currPos.left = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.left;
                    }
                } else {
                    if (currPos.right == null) {
                        newPoint.division = VERTICAL;
                        currPos.right = newPoint;
                        size++;
                        return;
                    } else {
                        currPos = currPos.right;
                    }
                }
            }
        }
    }

    /**
     * Returns true if the point is contained within the set
     */
    public boolean contains(Point2D p) {
        Node currPos = root;

        while (currPos != null) {
            if (p.equals(currPos.value)) {
                return true;
            }

            /* Use first bit to determine if level is even or odd */
            if (currPos.division == VERTICAL) {
                /* Vertical slice */
                if (p.x() < currPos.value.x()) {
                    currPos = currPos.left;
                } else {
                    currPos = currPos.right;
                }

            } else {
                /* Horizontal slice */
                if (p.y() < currPos.value.y()) {
                    currPos = currPos.left;
                } else {
                    currPos = currPos.right;
                }
            }
        }

        return false;
    }

    /**
     * Uses StdDraw to draw the points in the set on screen
     */
    public void draw() {
        if (root != null) {
            drawNodes(root);
        }
    }

    private void drawNodes(Node node) {
        node.value.draw();

        if (node.right != null) {
            drawNodes(node.right);
        }

        if (node.left != null) {
            drawNodes(node.left);
        }
    }

    /**
     * Returns a set of points held within a rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        contained = new LinkedList<Point2D>();

        checkPointsInRange(root, rect);

        return contained;
    }

    private void checkPointsInRange(Node node, RectHV rect) {
        if (node == null) return;

        if (node.division == VERTICAL) {
            if (node.value.x() > rect.xmax()) {
                /* go to the left */
                checkPointsInRange(node.left, rect);

            } else if (node.value.x() < rect.xmin()) {
                /* go to the right */
                checkPointsInRange(node.right, rect);

            } else {
                /* go both ways and check self */
                checkPointsInRange(node.left, rect);
                checkPointsInRange(node.right, rect);

                if (rect.contains(node.value)) {
                    contained.add(node.value);
                }
            }

        } else {
            if (node.value.y() > rect.ymax()) {
                /* go to the left */
                checkPointsInRange(node.left, rect);

            } else if (node.value.y() < rect.ymin()) {
                /* go to the right */
                checkPointsInRange(node.right, rect);

            } else {
                /* go both ways and check self */
                checkPointsInRange(node.left, rect);
                checkPointsInRange(node.right, rect);

                if (rect.contains(node.value)) {
                    contained.add(node.value);
                }
            }
        }
    }

    /**
     * Returns the nearest point to the provided Point2D
     */
    public Point2D nearest(Point2D p) {
        champion = null;
        comparison = p;

        checkNearest(root);

        return champion;
    }

    private void checkNearest(Node node) {
        if (node == null) return;

        if (champion == null) {
            champion = node.value;
        } else if (comparison.distanceSquaredTo(champion) > comparison.distanceSquaredTo(node.value)) {
            champion = node.value;
        }

        if (node.division == VERTICAL) {
            /* If this point is closer than champion, explore both subtrees */
            if (comparison.distanceSquaredTo(champion) > comparison.distanceSquaredTo(node.value)) {
                if (node.value.x() >= comparison.x()) {
                    checkNearest(node.left);
                    checkNearest(node.right);

                } else {
                    checkNearest(node.right);
                    checkNearest(node.left);
                }

                /* Otherwise explore the subtree closer to the comparison point */
            } else {
                if (node.value.x() > comparison.x()) {
                    checkNearest(node.left);

                } else if (node.value.x() < comparison.x()) {
                    checkNearest(node.right);

                } else {
                    checkNearest(node.left);
                    checkNearest(node.right);
                }
            }

        } else {
            /* If this point is closer than champion, explore both subtrees */
            if (comparison.distanceSquaredTo(champion) > comparison.distanceSquaredTo(node.value)) {
                if (node.value.y() >= comparison.y()) {
                    checkNearest(node.left);
                    checkNearest(node.right);

                } else {
                    checkNearest(node.right);
                    checkNearest(node.left);
                }

                /* Otherwise explore the subtree closer to the comparison point */
            } else {
                if (node.value.y() > comparison.y()) {
                    checkNearest(node.left);

                } else if (node.value.y() < comparison.y()) {
                    checkNearest(node.right);

                } else {
                    checkNearest(node.left);
                    checkNearest(node.right);
                }
            }
        }
    }

}