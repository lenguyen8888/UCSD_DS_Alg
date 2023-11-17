import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SuffixArrayLong {
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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
//    public int[] computeSuffixArray(String text) {
//        int[] result = new int[text.length()];
//
//        // write your code here
//
//        return result;
//    }

    private static final char[] ALPHABET = "$ACGT".toCharArray();

    private int[] sortCharacters(String s) {
        int sLen = s.length();
        int[] order = new int[sLen];
        Map<Character, Integer> count = new HashMap<>();
        for (char c : ALPHABET) {
            count.put(c, 0);
        }
        for (char c : s.toCharArray()) {
            count.put(c, count.get(c) + 1);
        }
        for (int i = 1; i < ALPHABET.length; ++i) {
            count.put(ALPHABET[i], count.get(ALPHABET[i]) + count.get(ALPHABET[i - 1]));
        }
        for (int j = sLen - 1; j >= 0; --j) {
            char c = s.charAt(j);
            count.put(c, count.get(c) - 1);
            order[count.get(c)] = j;
        }
        return order;
    }

    private int[] computeCharClasses(String s, int[] order) {
        int sLen = s.length();
        int[] char_class = new int[sLen];
        for (int i = 1; i < sLen; ++i) {
            if (s.charAt(order[i]) == s.charAt(order[i - 1])) {
                char_class[order[i]] = char_class[order[i - 1]];
            } else {
                char_class[order[i]] = char_class[order[i - 1]] + 1;
            }
        }
        return char_class;
    }

    private int[] sortDoubled(String s, int L, int[] old_order, int[] old_class) {
        int sLen = s.length();
        int[] count = new int[sLen];
        for (int i = 0; i < count.length; ++i) {
            count[old_class[i]]++;
        }
        for (int i = 1; i < count.length; ++i) {
            count[i] += count[i - 1];
        }

        int[] new_order = new int[sLen];

        // walk backward from string's end
        for (int j = sLen - 1; j >= 0; --j) {
            int start = (old_order[j] - L + sLen) % sLen;
            int classVal = old_class[start];
            count[classVal] -= 1;
            new_order[count[classVal]] = start;
        }
        return new_order;
    }

    private int[] updateClasses(int[] new_order, int[] old_class, int L) {
        int n = old_class.length;
        int[] new_class = new int[n];
        for (int i = 1; i < n; ++i) {
            int cur = new_order[i];
            int mid = (cur + L) % n;
            int prev = new_order[i - 1];
            int mid_prev = (prev + L) % n;
            if (old_class[cur] == old_class[prev] && old_class[mid] == old_class[mid_prev]) {
                new_class[cur] = new_class[prev];
            } else {
                new_class[cur] = new_class[prev] + 1;
            }
        }
        return new_class;
    }

    public int[] computeSuffixArray(String text) {
        int[] order = sortCharacters(text);
        int[] char_class = computeCharClasses(text, order);
        int L = 1;
        int textLen = text.length();
        while (L < textLen) {
            order = sortDoubled(text, L, order, char_class);
            char_class = updateClasses(order, char_class, L);
            L *= 2;
        }
        return order;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}
