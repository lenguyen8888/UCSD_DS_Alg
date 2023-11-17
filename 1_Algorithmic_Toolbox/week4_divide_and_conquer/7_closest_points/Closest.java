import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Closest {

//    static class Point implements Comparable<Point> {
//        long x, y;
//
//        public Point(long x, long y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        @Override
//        public int compareTo(Point o) {
//            return o.y == y ? Long.signum(x - o.x) : Long.signum(y - o.y);
//        }
//    }

    // We will switch between x sort order and y sort order => we don't need to
    // implement Comparable
    static class Point {
        long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    static double getDistance(Point p1, Point p2) {
        double d = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
        return d;
    }

//    static double minimalDistance(int[] x, int y[]) {
//        double ans = Double.POSITIVE_INFINITY;
//        // write your code here
//        return ans;
//    }
    private static double minDistNaive(Point[] p) {
        int pLen = p.length;
        assert (pLen >= 2);
        double d = getDistance(p[0], p[1]);
        for (int i = 0; i < pLen; ++i)
            for (int j = i + 1; j < pLen; ++j)
                d = Math.min(d, getDistance(p[i], p[j]));
        return d;
    }

    private static double minDist(Point[] points) {
        int pLen = points.length;
        if (pLen <= 3)
            return minDistNaive(points);
        int mid = pLen / 2;
        Point[] pLeft = new Point[mid];
        Point[] pRight = new Point[pLen - mid];
        System.arraycopy(points, 0, pLeft, 0, pLeft.length);
        System.arraycopy(points, mid, pRight, 0, pRight.length);
        double minLeft = minDist(pLeft);
        double minRight = minDist(pRight);
        double minVal = Math.min(minLeft, minRight);
        double minStripDist = stripMin(points, minVal);
        return Math.min(minVal, minStripDist);
    }

    private static double stripMin(Point[] points, double min_d) {
        int pLen = points.length;
        int mid = pLen / 2;
        long midX = points[mid].x;
        LinkedList<Point> midPoints = new LinkedList<>();
        for (Point pt : points) {
            if (Math.abs(pt.x - midX) < min_d) {
                midPoints.add(pt);
            }
        }
        if (midPoints.size() < 2)
            return min_d;
        Point[] p_y_sort = new Point[midPoints.size()];
        for (int i = 0; i < p_y_sort.length; ++i) {
            Point pt = midPoints.pop();
            p_y_sort[i] = new Point(pt.x, pt.y);
        }
        int p_y_len = p_y_sort.length;

        Arrays.sort(p_y_sort, (Point a, Point b) -> {
            return Long.signum(a.y - b.y);
        });
        double minStripDist = getDistance(p_y_sort[0], p_y_sort[1]);
        for (int i = 0; i < p_y_len - 1; ++i) {
            for (int j = i + 1; j < Math.min(i + 7, p_y_len); ++j) {
                minStripDist = Math.min(minStripDist, getDistance(p_y_sort[i], p_y_sort[j]));
            }
        }
        return Math.min(minStripDist, min_d);
    }

    static double minimalDistance(int[] x, int y[]) {
        Point points[] = new Point[x.length];
        for (int i = 0; i < x.length; ++i) {
            points[i] = new Point(x[i], y[i]);
        }
        Arrays.sort(points, (Point a, Point b) -> {
            return Long.signum(a.x - b.x);
        });
        return minDist(points);
    }

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = nextInt();
            y[i] = nextInt();
        }
        System.out.println(minimalDistance(x, y));
        writer.close();
    }

    static BufferedReader reader;
    static PrintWriter writer;
    static StringTokenizer tok = new StringTokenizer("");

    static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    static int nextInt() {
        return Integer.parseInt(next());
    }
}
