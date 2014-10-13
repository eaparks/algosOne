import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by eparks on 10/7/2014.
 */
public class Fast {

    private Point[] inputPoints;
    private Point[] slopeSortedPoints;
    private Line[] lines;
    private int i = 0;

    private Point[] linears;
    private final int LINEARS_INIT_SIZE = 6;
    private int countOfPointsInLine;
    private int lineCount = 0;

    private void init(int pointCount) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(.005);

        inputPoints = new Point[pointCount];
        slopeSortedPoints = new Point[pointCount];
        lines = new Line[LINEARS_INIT_SIZE];
    }


    private void addPoint(int x, int y) {

        Point in = new Point(x, y);
        in.draw();
        inputPoints[i++] = in;
    }

    private void findLines() {

        System.arraycopy(inputPoints, 0, slopeSortedPoints, 0, inputPoints.length);

        int indexAboveFour;

        for (int j = 0; j < inputPoints.length; j++) {

            Arrays.sort(slopeSortedPoints, inputPoints[j].SLOPE_ORDER);

            double slope1;
            double slope2;
            double slope3;

            for (int j1 = 1; j1 < slopeSortedPoints.length - 2; j1++) {
                countOfPointsInLine = 0;
                slope1 = inputPoints[j].slopeTo(slopeSortedPoints[j1]);
                slope2 = inputPoints[j].slopeTo(slopeSortedPoints[j1 + 1]);
                slope3 = inputPoints[j].slopeTo(slopeSortedPoints[j1 + 2]);

                // Create a temp array to store linear points
                linears = new Point[LINEARS_INIT_SIZE];
                if (slope1 == slope2 && slope1 == slope3) {
                    indexAboveFour = j1 + 3;
                    if(lineCount > 0 && isASubsegment(slope1, slopeSortedPoints[j1])) {
                        continue;
                    }
                    linears[countOfPointsInLine++] = inputPoints[j];
                    linears[countOfPointsInLine++] = slopeSortedPoints[j1];
                    linears[countOfPointsInLine++] = slopeSortedPoints[j1 + 1];
                    linears[countOfPointsInLine++] = slopeSortedPoints[j1 + 2];

                    while (indexAboveFour < slopeSortedPoints.length &&
                            inputPoints[j].slopeTo(slopeSortedPoints[indexAboveFour]) == slope1) {
                        if(countOfPointsInLine == linears.length) {
                            resize(2 * linears.length);
                        }
                        linears[countOfPointsInLine++] = slopeSortedPoints[indexAboveFour];
                        indexAboveFour++;
                    }
                    sortPointsAndSaveLine();
                }
            }
        }
    }


    private boolean isASubsegment(double slope, Point p) {

        for (int j = 0; j < lines.length; j++) {
            if(lines[j] != null && slope == lines[j].slope()) {
                if((p.x == lines[j].start.x && p.y == lines[j].start.y) ||
                        (p.x == lines[j].end.x && p.y == lines[j].end.y)) {
                    return true;
                }
                if (p.slopeTo(lines[j].start) == p.slopeTo(lines[j].end)) {
                    return true;
                }
            }

        }
        return false;
    }

    private void sortPointsAndSaveLine() {

        Line line = new Line();

        // Sort linear points in temp array
        Point[] sortableNonSparsePoints = new Point[countOfPointsInLine];
        System.arraycopy(linears, 0, sortableNonSparsePoints, 0, countOfPointsInLine);
        Arrays.sort(sortableNonSparsePoints);


        // Push onto lineEndPoints the most distant point in temp array
        line.start = sortableNonSparsePoints[0];
        line.end = sortableNonSparsePoints[countOfPointsInLine - 1];

//        System.out.println("\tSaving Line with start: " + line.start + ", end: " + line.end);
        if(!lineAlreadyDiscovered(lines, line)) {
            out(sortableNonSparsePoints);
            saveLine(line);
        }
    }

    private void saveLine(Line line) {

        lineCount++;
        if(lineCount == lines.length) {
            resizeLines(2 * lines.length);
        }
        lines[lineCount - 1] = line;
    }

    private boolean lineAlreadyDiscovered(Line[] lines, Line line) {

        for (int j = 0; j < lines.length; j++) {
            if(lines[j] != null && lines[j].equals(line)) {
                return true;
            }
        }
        return false;
    }

    private void out(Point[] linears) {

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < linears.length; j++) {
            sb.append(linears[j]);
            if (j < linears.length - 1) {
                sb.append(" -> ");
            }
        }
        StdOut.println(sb.toString());
    }


    /**
     * Resize the underlying array holding the elements.
     *
     */
    private void resize(int capacity) {

//        System.out.println("Resizing linears array...");
        assert capacity >= countOfPointsInLine;

        Point[] temp = new Point[capacity];
        for(int i = 0; i < countOfPointsInLine; i++) {
            temp[i] = linears[i];
        }
        linears = temp;
    }

    /**
     * Resize the lines array.
     * @param capacity - the new array size
     */
    private void resizeLines(int capacity) {

        assert capacity >= lineCount;

        Line[] temp = new Line[capacity];
        for(int i = 0; i < lineCount; i++) {
            temp[i] = lines[i];
        }
        lines = temp;
    }

    private void draw() {

        int count = 0;
        for (int j = 0; j < lines.length; j++) {
            if(lines[j] == null) {
                continue;
            }
            lines[j].draw();
            count++;
        }
//        System.out.println("Line count is: " + count);
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

        Fast fast = new Fast();
        fast.init(pointCount);

        for (int i = 0; i < pointCount; i++) {
            int x = in.readInt();
            int y = in.readInt();
            fast.addPoint(x, y);
        }
        fast.findLines();
        fast.draw();
    }

    private class Line {

        Point start;
        Point end;

        void draw() {
            start.drawTo(end);
        }

        @Override
        public boolean equals(Object obj) {

            if(this == obj) {
                return true;
            }
            if(obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            Line that = (Line) obj;

            boolean thisStartEqualsThatStart = (this.start.x == that.start.x) && (this.start.y == that.start.y);
            boolean thisEndEqualsThatEnd = (this.end.x == that.end.x) && (this.end.y == that.end.y);

            boolean thisStartEqualsThatEnd = (this.start.x == that.end.x) && (this.start.y == that.end.y);
            boolean thisEndEqualsThatStart = (this.end.x == that.start.x) && (this.end.y == that.start.y);

            if((thisStartEqualsThatStart && thisEndEqualsThatEnd) || (thisStartEqualsThatEnd && thisEndEqualsThatStart)) {
                return true;
            }
            return false;
        }

        public double slope() {

            return start.slopeTo(end);
        }

        public String toString() {
            return "Start: " + start + " End:" + end;
        }
    }


    private class Point implements Comparable<Point> {

        // compare points by slope to this point
        public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();

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
                result = Double.POSITIVE_INFINITY;
            } else {
                result = (double) (that.y - this.y) / (double) (that.x - this.x);
            }

            return result;
        }

        /**
         * The SLOPE_ORDER comparator should compare points by the slopes they
         * make with the invoking point (x0, y0). Formally, the point (x1, y1)
         * is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
         * is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal,
         * vertical, and degenerate line segments as in the slopeTo() method.
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


}
