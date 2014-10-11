import java.util.Arrays;

/**
 * Created by eparks on 10/7/2014.
 *
 * Write a program Brute.java that examines 4 points at a time and checks
 * whether they all lie on the same line segment, printing out any such line
 * segments to standard output and drawing them using standard drawing.
 * To check whether the 4 points p, q, r, and s are collinear, check whether
 * the slopes between p and q, between p and r, and between p and s are all equal.
 *
 * The order of growth of the running time of your program should be N4 in the
 * worst case and it should use space proportional to N.
 */
public class Brute {

    private Point[] pointsToDraw;
    private Stack<Point> lineStartPoints;
    private Stack<Point> lineEndPoints;
    private int i = 0;

    private void init(int pointCount) {

        pointsToDraw = new Point[pointCount];
        lineStartPoints = new Stack<Point>();
        lineEndPoints = new Stack<Point>();
    }

    private void addPoint(int x, int y) {

        pointsToDraw[i++] = new Point(x, y);
    }

    private void findFourCollinear() {

        Point pointOne;
        Point pointTwo;
        Point pointThree;
        Point pointFour;

        Arrays.sort(pointsToDraw);
        for (int j = 0; j < pointsToDraw.length - 3; j++) {
            pointOne = pointsToDraw[j];
            for (int k = j + 1; k < pointsToDraw.length - 2; k++) {
                pointTwo = pointsToDraw[k];
                for (int l = k + 1; l < pointsToDraw.length - 1; l++) {
                    pointThree = pointsToDraw[l];
                    for (int m = l + 1; m < pointsToDraw.length; m++) {
                        pointFour = pointsToDraw[m];
                        findCollinearPoints(pointOne, pointTwo, pointThree, pointFour);
                    }
                }
            }
        }
    }

    // Examine 4 points at a time and check whether they all lie on the same
    // line segment, printing out any such line segments to standard output
    // and drawing them using standard drawing.
    // To check whether the 4 points p, q, r, and s are collinear, check whether the slopes between p and q,
    // between p and r, and between p and s are all equal.

    private void findCollinearPoints(Point a, Point b, Point c, Point d) {

        double aToB = a.slopeTo(b);
        double aToD = a.slopeTo(d);
        double bToC = b.slopeTo(c);

        if (aToB == bToC && aToB == aToD) {
            out(a, b, c, d);
            lineStartPoints.push(a);
            lineEndPoints.push(d);
        }
    }

    private void out(Point a, Point b, Point c, Point d) {

        StringBuilder sb = new StringBuilder(a.toString());
        sb.append(" -> ");
        sb.append(b.toString());
        sb.append(" -> ");
        sb.append(c.toString());
        sb.append(" -> ");
        sb.append(d.toString());

        StdOut.println(sb.toString());
    }



    // call draw() once for each point in the input file
    // drawTo() once for each line segment discovered.
    private void draw() {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(.005);

        for (int i = 0; i < pointsToDraw.length; i++) {
            pointsToDraw[i].draw();
        }

//        System.out.println("Line count is: " + lineEndPoints.size());

        while (!lineStartPoints.isEmpty()) {
            lineStartPoints.pop().drawTo(lineEndPoints.pop());
        }
    }

    /**
     * Take the name of an input file as a command-line argument,
     * read the input file (in the format specified below), print\
     * to standard output the line segments discovered (in the format
     * specified below), and draw to standard draw the line segments discovered
     *
     * @param args
     */
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        int pointCount = in.readInt();

//        System.out.println("Point count is: " + pointCount);

        Stopwatch stopwatch = new Stopwatch();
        Brute brute = new Brute();
        brute.init(pointCount);

        for (int i = 0; i < pointCount; i++) {
            int x = in.readInt();
            int y = in.readInt();
            brute.addPoint(x, y);
        }
        brute.findFourCollinear();
        brute.draw();
        System.out.println(stopwatch.elapsedTime());

    }
}
