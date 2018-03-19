import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> lsList = null;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        lsList = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();

                double slope_i_to_j = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length; k++) {
                    if (points[j].compareTo(points[k]) == 0)
                        throw new IllegalArgumentException();

                    double slope_j_to_k = points[j].slopeTo(points[k]);
                    if (slope_i_to_j != slope_j_to_k)
                        continue;

                    for (int l = k + 1; l < points.length; l++) {
                        if (points[l].compareTo(points[k]) == 0)
                            throw new IllegalArgumentException();

                        double slope_k_to_l = points[k].slopeTo(points[l]);
                        if (slope_k_to_l == slope_j_to_k) {
                            LineSegment ls = new LineSegment(points[i], points[l]);
                            lsList.add(ls);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lsList.size();
    }

    public LineSegment[] segments() {
        return (LineSegment[])lsList.toArray();
    }

    static public void main(String[] args) {
        /*
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        */
    }
}