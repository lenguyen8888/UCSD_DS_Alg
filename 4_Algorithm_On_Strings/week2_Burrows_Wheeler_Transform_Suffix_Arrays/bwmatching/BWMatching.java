import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BWMatching {
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

    private final static String ALL_CHAR = "$ACGT";
    private final static int LETTER_RANGE = ALL_CHAR.length();

    private int letterToIndex(char letter) {
        int retVal = ALL_CHAR.indexOf(letter);
        assert (retVal >= 0);
        return retVal;
    }

    // Preprocess the Burrows-Wheeler Transform bwt of some text
    // and compute as a result:
    // * starts - for each character C in bwt, starts[C] is the first position
    // of this character in the sorted array of
    // all characters of the text.
    // * occ_count_before - for each character C in bwt and each position P in bwt,
    // occ_count_before[C][P] is the number of occurrences of character C in bwt
    // from position 0 to position P inclusive.
//    private void PreprocessBWT(String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
//        // Implement this function yourself
//    }
    private void PreprocessBWT(String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
        calcStarts(bwt, starts);
        calcOccCount(bwt, occ_counts_before);
    }

    /**
     * @param bwt
     * @param occ_counts_before
     */
    private void calcOccCount(String bwt, Map<Character, int[]> occ_counts_before) {
        occ_counts_before.clear();
        for (char c : ALL_CHAR.toCharArray()) {
            occ_counts_before.put(c, new int[bwt.length() + 1]);
        }

        for (int i = 0; i < bwt.length(); ++i) {
            char c = bwt.charAt(i);
            for (int j = 0; j < ALL_CHAR.length(); ++j) {
                char occCh = ALL_CHAR.charAt(j);
                int[] countA = occ_counts_before.get(occCh);
                if (occCh == c)
                    countA[i + 1] = countA[i] + 1;
                else
                    countA[i + 1] = countA[i];
            }
        }
    }

    /**
     * @param bwt
     * @param starts
     */
    private void calcStarts(String bwt, Map<Character, Integer> starts) {
        // Initialize starts count, occ_counts_before
        Map<Character, Integer> count = new HashMap<>();
        for (char c : ALL_CHAR.toCharArray()) {
            count.put(c, 0);
        }
        // count characters
        for (int i = 0; i < bwt.length(); ++i) {
            char c = bwt.charAt(i);
            count.put(c, count.get(c) + 1);
        }
        // accumulate to the correct start index
        starts.clear();
        starts.put(ALL_CHAR.charAt(0), 0);
        for (int i = 1; i < ALL_CHAR.length(); ++i) {
            char c = ALL_CHAR.charAt(i);
            char prevCh = ALL_CHAR.charAt(i - 1);
            starts.put(c, starts.get(prevCh) + count.get(prevCh));
        }
    }

    // Compute the number of occurrences of string pattern in the text
    // given only Burrows-Wheeler Transform bwt of the text and additional
    // information we get from the preprocessing stage - starts and
    // occ_counts_before.
//    int CountOccurrences(String pattern, String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
//        // Implement this function yourself
//    }
//    int CountOccurrences(String pattern, String bwt, Map<Character, Integer> starts,
//            Map<Character, int[]> occ_counts_before) {
//        return 0;
//    }

    int CountOccurrences(String pattern, String bwt, Map<Character, Integer> starts,
            Map<Character, int[]> occ_counts_before) {
        int top = 0, bottom = bwt.length() - 1;
        int i = pattern.length() - 1;
        while (top <= bottom) {
            if (i >= 0) {
                char symbol = pattern.charAt(i--);
                top = starts.get(symbol) + occ_counts_before.get(symbol)[top];
                bottom = starts.get(symbol) + occ_counts_before.get(symbol)[bottom + 1] - 1;
            } else {
                return bottom - top + 1;
            }
        }
        return 0;
    }

    static public void main(String[] args) throws IOException {
        new BWMatching().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        // Start of each character in the sorted list of characters of bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, Integer> starts = new HashMap<Character, Integer>();
        // Occurrence counts for each character and each position in bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, int[]> occ_counts_before = new HashMap<Character, int[]>();
        // Preprocess the BWT once to get starts and occ_count_before.
        // For each pattern, we will then use these precomputed values and
        // spend only O(|pattern|) to find all occurrences of the pattern
        // in the text instead of O(|pattern| + |text|).
        PreprocessBWT(bwt, starts, occ_counts_before);
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        int[] result = new int[patternCount];
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
            result[i] = CountOccurrences(patterns[i], bwt, starts, occ_counts_before);
        }
        print(result);
    }
}
