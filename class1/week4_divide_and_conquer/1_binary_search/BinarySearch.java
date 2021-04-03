import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BinarySearch {
    private static final boolean FULL_TEST = false;

    static int binarySearch(int[] a, int x) {
        int left = 0, right = a.length - 1;
        // write your code here
        while (left <= right) {
            int mid = (right + left) / 2;
            int val = a[mid];
            if (val < x) {
                left = mid + 1;
            } else if (val == x) {
                return mid;
            } else {// if(val < x )
                right = mid - 1;
            }
        }
        return -1;
    }

    static int linearSearch(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x)
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        if (FULL_TEST) {
            final int NUM_TEST = 100;
            boolean testPassed = true;
            int[] a = new int[NUM_TEST];
            for (int i = 0; i < NUM_TEST; i++)
                a[i] = i;
            for (int i = 0; i < 2 * NUM_TEST; i++) {
                boolean matched = binarySearch(a, i) == linearSearch(a, i);
                if (!matched) {
                    testPassed = false;
                    System.out.println(String.format("mismatch i == %d, bin == %d, lin == %d", i, binarySearch(a, i),
                            linearSearch(a, i)));
                }
            }
            if (testPassed)
                System.out.println("test passed");
        } else {
            FastScanner scanner = new FastScanner(System.in);
            int n = scanner.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = scanner.nextInt();
            }
            int m = scanner.nextInt();
            int[] b = new int[m];
            for (int i = 0; i < m; i++) {
                b[i] = scanner.nextInt();
            }
            for (int i = 0; i < m; i++) {
                // replace with the call to binarySearch when implemented
                System.out.print(binarySearch(a, b[i]) + " ");
            }
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
