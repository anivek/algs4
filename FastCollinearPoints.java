

public class FastCollinearPoints {
    private Point[] aux = null;
    private ArrayList<LineSegment> lsList = null;

    public FastCollinearPoints(Point[] points) {
        aux = new Point[points.length];

        lsList = new ArrayList<LineSegment>();
        sort(points, aux, 0, points.length - 1);
    }

    public int numberOfSegments() {
        return lsList.size();
    }

    public LineSegment[] segments() {
        return (LineSegment[])lsList.toArray();
    }

    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0)
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    private static void sort(Point[] a, Point[] aux, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;

        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }
}