import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class SuffixArrayMatching {
    class fastscanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        fastscanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextint() throws IOException {
            return Integer.parseInt(next());
        }
    }

//    public int[] computeSuffixArray(String text) {
//        int[] suffixArray;
//
//        // write your code here
//
//        return suffixArray;
//    }
//
//    public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) {
//        List<Integer> result;
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

    private int findMinSuffixPat(String pattern, String text, int[] suffixArray) {
        int pLen = pattern.length();
        int tLen = text.length();
        // suffixes are sorted so we can use binary search
        int min = 0, max = tLen;
        // find the lowest suffixarray index that match pattern
        while (min < max) {
            int mid = (min + max) / 2;
            int i = 0;
            int suffix = suffixArray[mid];

            // compare string and find the 1st prefix of a suffix that match pattern
            while (i < pLen && suffix + i < tLen) {
                char pChar = pattern.charAt(i);
                char tChar = text.charAt(suffix + i);
                if (pChar > tChar) {
                    min = mid + 1;
                    break;
                } else if (pChar < tChar) {
                    max = mid;
                    break;
                }
                i++;
                if (i == pLen)
                    max = mid;
                else if (suffix + i == tLen)
                    min = mid + 1;
            }
        }
        return min;
    }

    private int findMaxSuffixPat(int min, String pattern, String text, int[] suffixArray) {
        int pLen = pattern.length();
        int tLen = text.length();
        // suffixes are sorted so we can use binary search
        int max = tLen;

        // find the highest suffixarray index that match pattern
        while (min < max) {
            int mid = (min + max) / 2;
            int suffix = suffixArray[mid];
            int i = 0;

            while (i < pLen && suffix + i < tLen) {
                char pChar = pattern.charAt(i);
                char tChar = text.charAt(suffix + i);
                if (pChar < tChar) {
                    max = mid;
                    break;
                }
                i++;

                // if pattern match the beginning of a suffix
                // we continue
                if (i == pLen && i <= tLen - suffix) {
                    min = mid + 1;
                }
            }
        }
        // max is just 1 step beyond the last match pattern
        return max - 1;
    }

    public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) {
        List<Integer> result = new ArrayList<>();
        int start = findMinSuffixPat(pattern, text, suffixArray);
        int end = findMaxSuffixPat(start, pattern, text, suffixArray);
        for (int i = start; i <= end; ++i) {
            result.add(suffixArray[i]);
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayMatching().run();
    }

    public void print(boolean[] x) {
        for (int i = 0; i < x.length; ++i) {
            if (x[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public void run() throws IOException {
        fastscanner scanner = new fastscanner();
        String text = scanner.next() + "$";
        int[] suffixArray = computeSuffixArray(text);
        int patternCount = scanner.nextint();
        boolean[] occurs = new boolean[text.length()];
        for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex) {
            String pattern = scanner.next();
            List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
            for (int x : occurrences) {
                occurs[x] = true;
            }
        }
        print(occurs);
    }
}
