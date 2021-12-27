import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MajorityElement {
    private static int getMajorityElement(int[] a, int left, int right) {
        if (left == right) {
            return -1;
        }
        if (left + 1 == right) {
            return a[left];
        }
        // write your code here
        return -1;
    }

    private static int getMajorityVal(int[] a, int left, int right) {
        int[] tempA = new int[right - left];
        for (int i = 0; i < tempA.length; i++)
            tempA[i] = a[left + i];
        Arrays.sort(tempA);
        // sort array in n*log(n) time
        // Now if the element a distance length/2 away is the same
        // we have at least length/2 + 1 elements with that value
        int halfSize = (tempA.length) / 2;

        for (int i = 0; i < (tempA.length - halfSize); i++) {
            if (tempA[i] == tempA[i + halfSize])
                return tempA[i];
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityVal(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
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
