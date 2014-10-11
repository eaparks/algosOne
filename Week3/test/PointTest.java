import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PointTest {

    private Point pointA =          new Point(3, 4);

    private Point pointToRight =    new Point(5, 4);
    private Point pointToLeft =     new Point(1, 4);

    private Point pointAbove =      new Point(3, 6);
    private Point pointAboveRight = new Point(5, 6);
    private Point pointAboveLeft =  new Point(1, 6);

    private Point pointBelow =      new Point(3, 3);
    private Point pointBelowRight = new Point(6, 3);
    private Point pointBelowLeft =  new Point(1, 2);

    private Point pointB =          new Point(3, 4);

    @Test
    public void testCompareTo() throws Exception {

        assertTrue(pointA.compareTo(pointToRight) < 0);
        assertTrue(pointA.compareTo(pointToLeft) > 0);

        assertTrue(pointA.compareTo(pointAboveRight) < 0);
        assertTrue(pointA.compareTo(pointBelowRight) > 0);

        assertTrue(pointA.compareTo(pointBelow) > 0);
        assertTrue(pointA.compareTo(pointAbove) < 0);
        assertTrue(pointA.compareTo(pointA) == 0);
        assertTrue(pointA.compareTo(pointB) == 0);
    }

    @Test
    public void testCompareToWithNullInput() throws Exception {

        try {
            pointA.compareTo(null);
            fail("Expected an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    @Test
    public void testSlopeTo() throws Exception {

        try {
            pointA.slopeTo(null);
            fail("Expected an IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
            // expected
        }
        assertTrue(pointA.slopeTo(pointToLeft) == 0.0);
        assertTrue(pointA.slopeTo(pointToRight) == 0.0);

        assertTrue(pointA.slopeTo(pointAbove) == Double.POSITIVE_INFINITY);
        assertTrue(pointA.slopeTo(pointBelow) == Double.POSITIVE_INFINITY);

        assertTrue(pointA.slopeTo(pointA) == Double.NEGATIVE_INFINITY);
        assertTrue(pointA.slopeTo(pointB) == Double.NEGATIVE_INFINITY);

        assertTrue(pointA.slopeTo(pointAboveRight) > 0.0);
        assertTrue(pointA.slopeTo(pointBelowRight) < 0.0);
    }

    @Test
    public void testComparator() throws Exception {

        Point[] points = {pointAbove, pointAboveLeft, pointAboveRight,
                          pointBelow, pointBelowLeft, pointBelowRight,
                          pointToLeft, pointToRight};

        Arrays.sort(points, pointA.SLOPE_ORDER);

        Point[] pointsInExpectedOrder = {pointAboveLeft, pointBelowRight,
                                         pointToLeft, pointToRight,
                                         pointBelowLeft, pointAboveRight,
                                         pointBelow, pointAbove};

        int i = 0;
        for (Point point : points) {
            System.out.println("Point: " + point.toString());
            System.out.println("Expected Point: " + pointsInExpectedOrder[i]);
            assertTrue(point.getX() == pointsInExpectedOrder[i].getX());
            assertTrue(point.getY() == pointsInExpectedOrder[i].getY());
            i++;
        }
    }
}