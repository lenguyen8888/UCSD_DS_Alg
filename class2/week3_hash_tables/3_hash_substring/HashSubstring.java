import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    private static int PRIME = 1000000007;
    private static int MULTIPLIER = 263;

    private static long hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * MULTIPLIER + s.charAt(i)) % PRIME;
        return hash;
    }

    private static long[] precomputeHashes(String text, String pattern) {
        int tLen = text.length();
        int pLen = pattern.length();
        long[] hVal = new long[tLen - pLen + 1];
        long y = 1;
        for (int i = 0; i < pLen; ++i)
            y = (y * MULTIPLIER) % PRIME;
        hVal[hVal.length - 1] = hashFunc(text.substring(tLen - pLen, tLen));
        for (int i = tLen - pLen - 1; i >= 0; --i) {
            hVal[i] = (MULTIPLIER * hVal[i + 1] + text.charAt(i) - y * text.charAt(i + pLen)) % PRIME;

            // Fix negative modulo value
            if (hVal[i] < 0)
                hVal[i] += PRIME;
        }
        return hVal;
    }

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

//    private static List<Integer> getOccurrences(Data input) {
//        String s = input.pattern, t = input.text;
//        int m = s.length(), n = t.length();
//        List<Integer> occurrences = new ArrayList<Integer>();
//        for (int i = 0; i + m <= n; ++i) {
//            boolean equal = true;
//            for (int j = 0; j < m; ++j) {
//                if (s.charAt(j) != t.charAt(i + j)) {
//                    equal = false;
//                    break;
//                }
//            }
//            if (equal)
//                occurrences.add(i);
//        }
//        return occurrences;
//    }

    private static List<Integer> getOccurrences(Data input) {
        String pattern = input.pattern, text = input.text;
        int pLen = pattern.length(), tLen = text.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        long pHash = hashFunc(pattern);
        long hVal[] = precomputeHashes(text, pattern);
        for (int i = 0; i < (tLen - pLen + 1); ++i) {
            if (pHash == hVal[i]) {
                // Check to see if the strings are really equal
                if (pattern.equals(text.substring(i, i + pLen)))
                    occurrences.add(i);
            }
        }
        return occurrences;
    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
