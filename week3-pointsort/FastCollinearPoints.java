import java.util.Arrays;
import java.util.Comparator;

/**
 * Finds all line segments containing 4 points
 * by comparing all point
 */
public class FastCollinearPoints {
    private static final int INITIAL_SEGMENTS = 1;

    private LineSegment[] segments;
    private int nextSegment;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("null input");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("null input");
            }
        }

        segments = new LineSegment[INITIAL_SEGMENTS];
        nextSegment = 0;

        collinearPoints(Arrays.copyOf(points, points.length));
    }

    /**
     * finds all line segments containing 4 points
     */
    private void collinearPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            if (p == null) {
                throw new IllegalArgumentException("null point");
            }

            Comparator<Point> comparator = p.slopeOrder();

            Arrays.sort(points, 0, i, comparator);
            Arrays.sort(points, i + 1, points.length, comparator);

            for (int j = i + 1, length = 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeating points");
                }

                if ((j == (points.length - 1)) || (comparator.compare(points[j], points[j + 1]) != 0)) {
                    if (length >= 3) {
                        int res = Arrays.binarySearch(points, 0, i, points[j], comparator);
                        if (res < 0) {
                            Point[] collinear = new Point[length + 1];
                            collinear[0] = points[i];
                            for (int k = 0; k < length; k++) {
                                collinear[k + 1] = points[j - k];
                            }
                            Arrays.sort(collinear);
                            addSegment(new LineSegment(collinear[0], collinear[collinear.length - 1]));
                        }
                    }
                    length = 1;
                } else {
                    length++;
                }
            }
        }
        adjustSize();
    }

    private void adjustSize() {
        copySegments(nextSegment);
    }

    private void addSegment(LineSegment lineSegment) {
        if (nextSegment == segments.length) {
            copySegments(segments.length * 2);
        }

        segments[nextSegment++] = lineSegment;
    }

    private void copySegments(int newLength) {
        LineSegment[] newSegments = new LineSegment[newLength];

        int copyCount = (segments.length > newLength) ? newLength : segments.length;

        for (int i = 0; i < copyCount; i++) {
            newSegments[i] = segments[i];
        }
        segments = newSegments;
    }

    /**
     * the number of line segments
     */
    public int numberOfSegments()
    {
        return segments.length;
    }

    /**
     * collinear segments
     */
    public LineSegment[] segments() // the line segments
    {
        return Arrays.copyOf(segments, segments.length);
    }
}