import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class InverseBWT {
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

//    String inverseBWT(String bwt) {
//        StringBuilder result = new StringBuilder();
//
//        // write your code here
//
//        return result.toString();
//    }
    private final static String ALL_CHAR = "$ACGT";
    private final static int LETTER_RANGE = ALL_CHAR.length();

    private int letterToIndex(char letter) {
        switch (letter) {
        case '$':
            return 0;
        case 'A':
            return 1;
        case 'C':
            return 2;
        case 'G':
            return 3;
        case 'T':
            return 4;
        default: // '$'
//            assert (false);
            return 0;
        }
    }

    private int[] lastToFirstFrStr(String s) {
        int[] counts = new int[LETTER_RANGE];

        // Count characters for radix sort
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            counts[letterToIndex(c)]++;
        }

        // update counts is array of starting offsets for each character
        int temp = -1;
        for (int i = 0; i < ALL_CHAR.length(); ++i) {
            temp += counts[i];
            counts[i] = temp;
        }

        // first array will have the indices of the characters
        // in s in sorted order
        int[] first = new int[s.length()];
        for (int i = s.length() - 1; i >= 0; --i) {
            char c = s.charAt(i);
            first[i] = counts[letterToIndex(c)];
            counts[letterToIndex(c)]--;
        }
        return first;
    }

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();
        int[] first = lastToFirstFrStr(bwt);

        result.append('$');
        int pos = 0;
        for (int i = 0; i < bwt.length() - 1; ++i) {
            result.append(bwt.charAt(pos));
            pos = first[pos];
        }
        result.reverse();
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
