import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class CoveringSegments {

    private static int[] optimalPoints(Segment[] segments) {
        // write your code here

        Segment[] tempS = new Segment[segments.length];
        for (int i = 0; i < segments.length; i++)
            tempS[i] = new Segment(segments[i]);

        // Greedy algorithm is to wait for the segment.end to visit
        // from text book
        Arrays.sort(tempS);
        LinkedList<Integer> pointL = new LinkedList<Integer>();

        int prevEnd = Integer.MIN_VALUE;
        for (int i = 0; i < tempS.length; i++) {
            if (tempS[i].start > prevEnd) {
                prevEnd = tempS[i].end;
                pointL.add(prevEnd);
            }
        }

        int[] points = new int[pointL.size()];
        int i = 0;
        for (Integer p : pointL) {
            points[i++] = p;
        }
        return points;
    }

    private static class Segment implements Comparable<Segment> {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        Segment(Segment o) {
            this.start = o.start;
            this.end = o.end;
        }

        @Override
        public int compareTo(CoveringSegments.Segment o) {
            // TODO Auto-generated method stub
            return Integer.compare(this.end, o.end);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
