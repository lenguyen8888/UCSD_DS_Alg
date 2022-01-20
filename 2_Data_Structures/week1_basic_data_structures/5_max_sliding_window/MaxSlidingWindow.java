import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;
import java.util.Vector;

public class MaxSlidingWindow {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static final boolean USE_NAIVE = false;

    private void max_sliding_window(Vector<Integer> A, int w) {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < A.size(); ++i) {
            while (!dq.isEmpty() && A.get(i) >= A.get(dq.peekLast()))
                dq.removeLast();
            dq.addLast(i);
            if ((i >= w) && !dq.isEmpty() && dq.peek() == (i - w))
                dq.remove();
            if (i >= w - 1)
                System.out.print(A.get(dq.peek()) + " ");
        }
    }

    private void max_sliding_windows_naive(Vector<Integer> A, int w) {
        for (int i = 0; i < A.size() - w + 1; ++i) {
            int window_max = A.get(i);
            for (int j = i + 1; j < i + w; ++j) {
                window_max = Math.max(window_max, A.get(j));
            }
            System.out.print(window_max + " ");
        }
    }

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        Vector<Integer> A = new Vector<>();
        int n = scanner.nextInt();

        for (int i = 0; i < n; ++i) {
            A.add(scanner.nextInt());
        }
        int w = scanner.nextInt();
        if (USE_NAIVE)
            max_sliding_windows_naive(A, w);
        else
            max_sliding_window(A, w);
    }

    public static void main(String[] args) throws IOException {
        new MaxSlidingWindow().solve();
    }

}
