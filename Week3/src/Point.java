import java.util.Comparator;

/**
 * Created by eparks on 10/7/2014.
 */
public class Point implements Comparable<Point> {

    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();  // YOUR DEFINITION HERE

    private final int x;
    private final int y;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void draw() {

        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {

        if (that == null) {
            throw new IllegalArgumentException("Point parameter must not be null.");
        }
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {

        return "(" + x + ", " + y + ")";
    }

    /**
     * Compare points by their y-coordinates, breaking ties by their x-coordinates.
     * Formally, the invoking point (x0, y0) is less than the argument
     * point (x1, y1) if and only if either y0 < y1 or if (y0 = y1 and x0 < x1)
     *
     * @param that
     * @return
     */
    public int compareTo(Point that) {

        if (that == null) {
            throw new IllegalArgumentException("Point parameter must not be null.");
        }
        if (y < that.y) return -1;
        if (y > that.y) return 1;
        if (x < that.x) return -1;
        if (x > that.x) return 1;
        return 0;
    }

    /**
     * Return the slope between the invoking point (x0, y0) and the
     * argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).
     * Treat the slope of a horizontal line segment as positive zero;
     * treat the slope of a vertical line segment as positive infinity;
     * treat the slope of a degenerate line segment (between a
     * point and itself) as negative infinity.
     *
     */
    public double slopeTo(Point that) {

        if (that == null) {
            throw new IllegalArgumentException("Point parameter must not be null.");
        }
        double result = 0.0;
        if (this.x == that.x && this.y == that.y) {
            result = Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {
            result = 0.0;
        } else if (this.x == that.x) {
            result =  Double.POSITIVE_INFINITY;
        } else {
            result = (double) (that.y - this.y) / (double) (that.x - this.x);
        }

        return result;
    }

//    int getX() {
//        return x;
//    }
//
//
//    int getY() {
//
//        return y;
//    }

    /**
     * The SLOPE_ORDER comparator should compare points by the slopes they
     * make with the invoking point (x0, y0). Formally, the point (x1, y1)
     * is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
     * is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal,
     * vertical, and degenerate line segments as in the slopeTo() method.
     *
     */
    private class SlopeComparator implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {

            if (slopeTo(p1) == Double.POSITIVE_INFINITY && slopeTo(p2) == Double.POSITIVE_INFINITY) {
                return 0;
            }
            if (slopeTo(p1) == 0 && slopeTo(p2) == 0) {
                return 0;
            }
            if (slopeTo(p1) < slopeTo(p2)) return -1;
            if (slopeTo(p1) > slopeTo(p2)) return 1;
            return p1.compareTo(p2);
        }
    }
}
