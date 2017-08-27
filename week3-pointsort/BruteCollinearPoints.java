import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Finds all line segments containing 4 points or more
 * by sorting them in order.
 */
public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments = new ArrayList<LineSegment>();
    private final Point[] points;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        this.points = new Point[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
        sortPoints();
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        find4PointCollinearSegment();
    }

    private Point[] sortPoints() {
        Arrays.sort(points);
        return points;
    }

    /**
     * Finds start and end points of line.
     */
    private List<LineSegment> find4PointCollinearSegment() {
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (areCollinearPoints(p, q, r, s)) {
                            lineSegments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
        return lineSegments;
    }

    /**
     * Checks if points collinear.
     */
    private boolean areCollinearPoints(int... indexes) {
        boolean areCollinearPoints = false;
        if (points.length <= 2) {
            return true;
        }
        double slopeToCompare = points[indexes[0]].slopeTo(points[indexes[1]]);
        for (int i = 2; i < indexes.length; i++) {
            if (slopeToCompare != points[indexes[0]].slopeTo(points[indexes[i]])) {
                return false;
            }
        }
        return true;
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    /**
     * Collinear segments.
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

}