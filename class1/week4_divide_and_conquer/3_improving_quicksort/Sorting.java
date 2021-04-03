import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class Sorting {

    private final static boolean FULL_TEST = false;
    private static Random random = new Random();

    private static int[] partition3(int[] a, int l, int r) {
        // write your code here
        int m1 = l;
        int m2 = r;
        int x = a[l];
        int i = l;
        while (i <= m2) {
            if (a[i] < x) {
                int t = a[m1];
                a[m1] = a[i];
                a[i] = t;
                m1++;
                i++;
            } else if (a[i] == x) {
                i++;
            } else if (a[i] > x) {
                int t = a[m2];
                a[m2] = a[i];
                a[i] = t;
                m2--;
            }
        }

        int[] m = { m1, m2 };
        return m;
    }

    private static int partition2(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] <= x) {
                j++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[l];
        a[l] = a[j];
        a[j] = t;
        return j;
    }

    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
        // use partition3
        int m = partition2(a, l, r);
        randomizedQuickSort(a, l, m - 1);
        randomizedQuickSort(a, m + 1, r);
    }

    private static void randomizedQS3(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
        // use partition3
        int[] mL = partition3(a, l, r);
        randomizedQS3(a, l, mL[0] - 1);
        randomizedQS3(a, mL[1] + 1, r);
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        if (FULL_TEST) {
            int[] a1, a2;
            final int DUP_VAL = 5;
            final int RANGE = 10;
            final int ITERATION = 10;
            a1 = new int[DUP_VAL * RANGE];
            a2 = new int[a1.length];
            boolean testPassed = true;
            for (int iter = 0; iter < ITERATION; iter++) {
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = random.nextInt();
                }
                System.arraycopy(a1, 0, a2, 0, a1.length);
                randomizedQS3(a1, 0, a1.length - 1);
                randomizedQuickSort(a2, 0, a2.length - 1);
                boolean arrayEq = true;
                for (int k = 0; k < a1.length; k++)
                    arrayEq &= a1[k] == a2[k];
                if (!arrayEq) {
                    testPassed = false;
                    System.out.println(String.format("iter == %d", iter));
                    for (int i = 0; i < a1.length; i++) {
                        System.out.print(a1[i] + " ");
                    }
                    System.out.println();
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
            randomizedQS3(a, 0, n - 1);
            for (int i = 0; i < n; i++) {
                System.out.print(a[i] + " ");
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
