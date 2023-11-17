import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BurrowsWheelerTransform {
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

//    String BWT(String text) {
//        StringBuilder result = new StringBuilder();
//
//        // write your code here
//
//        return result.toString();
//    }
    String rotateLeft(String s) {
        return s.charAt(s.length() - 1) + s.substring(0, s.length() - 1);
    }

    String BWT(String text) {
        StringBuilder result = new StringBuilder();
        String[] matrix = new String[text.length()];
        matrix[0] = text;
        for (int i = 1; i < text.length(); ++i) {
            matrix[i] = rotateLeft(matrix[i - 1]);
        }
        Arrays.sort(matrix);
        int lastIndex = text.length() - 1;
        for (int i = 0; i < matrix.length; ++i) {
            result.append(matrix[i].charAt(lastIndex));
        }
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}
