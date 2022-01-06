import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class CoveringSegments {

    private static int[] optimalPoints(Segment[] segments) {
        // write your code here

        // Greedy algorithm is to wait for the segment.end to visit
        // from text book
        Arrays.sort(segments, (Segment a, Segment b) -> Integer.signum(a.end - b.end));
        LinkedList<Integer> pointL = new LinkedList<Integer>();

        int prevEnd = Integer.MIN_VALUE;
        for (int i = 0; i < segments.length; i++) {
            if (segments[i].start > prevEnd) {
                prevEnd = segments[i].end;
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

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
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
