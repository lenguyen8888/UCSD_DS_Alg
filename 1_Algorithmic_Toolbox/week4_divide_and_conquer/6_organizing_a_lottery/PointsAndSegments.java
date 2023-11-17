import java.util.Arrays;
import java.util.Scanner;

public class PointsAndSegments {

    // We set an order of START, POSTION, END for sort criteria
    static final int START = 0;
    static final int POSITION = 1;
    static final int END = 2;

    private static class SegStruct implements Comparable<SegStruct> {
        int segPos;
        int segType;
        int segId;

        SegStruct(int pos, int type, int id) {
            this.segPos = pos;
            this.segType = type;
            this.segId = id;
        }

        SegStruct(SegStruct o) {
            this.segPos = o.segPos;
            this.segType = o.segType;
            this.segId = o.segId;
        }

        @Override
        public int compareTo(SegStruct o) {
            int diff = Integer.compare(this.segPos, o.segPos);
            if (diff != 0)
                return diff;
            return Integer.compare(this.segType, o.segType);
        }
    }

    private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        // write your code here
        SegStruct dataA[] = new SegStruct[starts.length + ends.length + points.length];
        int dataIndex = 0;
        for (int i = 0; i < starts.length; i++) {
            dataA[dataIndex++] = new SegStruct(starts[i], START, i);
        }
        for (int i = 0; i < points.length; i++) {
            dataA[dataIndex++] = new SegStruct(points[i], POSITION, i);
        }
        for (int i = 0; i < ends.length; i++) {
            dataA[dataIndex++] = new SegStruct(ends[i], END, i);
        }
        Arrays.sort(dataA);
        int seg = 0;
        for (int i = 0; i < dataA.length; i++) {
            SegStruct item = dataA[i];
            if (item.segType == START) {
                seg++;
            } else if (item.segType == END) {
                seg--;
            } else { // POSITION
                cnt[item.segId] = seg;
            }
        }
        return cnt;
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        // use fastCountSegments
//        int[] cnt = naiveCountSegments(starts, ends, points);
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
    }
}
