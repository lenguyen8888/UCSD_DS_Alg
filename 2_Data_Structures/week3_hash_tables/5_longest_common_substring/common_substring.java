import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class common_substring {
    static final boolean USE_NAIVE = false;

    public class Answer {
        int i, j, len;

        Answer(int i, int j, int len) {
            this.i = i;
            this.j = j;
            this.len = len;
        }

        Answer(Answer o) {
            this.i = o.i;
            this.j = o.j;
            this.len = o.len;
        }
    }

    public Answer naieveSolve(String s, String t) {
        Answer ans = new Answer(0, 0, 0);
        for (int i = 0; i < s.length(); i++)
            for (int j = 0; j < t.length(); j++)
                for (int len = 0; i + len <= s.length() && j + len <= t.length(); len++)
                    if (len > ans.len && s.substring(i, i + len).equals(t.substring(j, j + len)))
                        ans = new Answer(i, j, len);
        return ans;
    }

    private long fastExp(long base, long exp, long modulo) {
        if (exp == 0)
            return 1;

        if (exp == 1)
            return base % modulo;

        long t = fastExp(base, exp / 2, modulo);
        t = (t * t) % modulo;

        // if exponent is even value
        if (exp % 2 == 0)
            return t;

        // if exponent is odd value
        else
            return ((base % modulo) * t) % modulo;
    }

    private long polyHash(String s, long prime, long multiplier) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return hash;
    }

    private long[] hashTable(String s, int pLen, long prime, long multiplier) {
        int sLen = s.length();
        long[] retVal = new long[sLen - pLen + 1];
        String subStr = s.substring(sLen - pLen);
        retVal[sLen - pLen] = polyHash(subStr, prime, multiplier);
        long y = fastExp(multiplier, pLen, prime);
        for (int i = sLen - pLen - 1; i >= 0; --i) {
            long newHash = (multiplier * retVal[i + 1] + s.charAt(i) - y * s.charAt(i + pLen)) % prime;
            if (newHash < 0)
                newHash += prime;
            retVal[i] = newHash;
        }
        return retVal;
    }

    private Map<Long, Integer> hashDict(String s, int pLen, long prime, long multiplier) {
        Map<Long, Integer> retDict = new HashMap<>();
        int sLen = s.length();
        String subStr = s.substring(sLen - pLen);
        long last = polyHash(subStr, prime, multiplier);
        retDict.put(last, sLen - pLen);
        long y = fastExp(multiplier, pLen, prime);
        for (int i = sLen - pLen - 1; i >= 0; --i) {
            long newHash = (multiplier * last + s.charAt(i) - y * s.charAt(i + pLen)) % prime;
            if (newHash < 0)
                newHash += prime;
            retDict.put(newHash, i);
            last = newHash;
        }
        return retDict;
    }

    // Important trick here is to store a map of all indices pairs that the hash
    // matches
    // the 1st attempt to return the 1st match right away failed with this error
    //
    // Failed case #7/12: Wrong answer
    // (Time used: 0.83/5.00, memory used: 328384512/2147483648.)
    private class SearchRes {
        boolean check;
        Map<Integer, Integer> bIndex;

        public SearchRes() {
            this.check = false;
            this.bIndex = new HashMap<>();
        }

    }

    private SearchRes searchSubString(long[] hashTable, Map<Long, Integer> hashDict) {
        SearchRes res = new SearchRes();
        for (int i = 0; i < hashTable.length; ++i) {
            if (hashDict.containsKey(hashTable[i])) {
                res.check = true;
                res.bIndex.put(i, hashDict.get(hashTable[i]));
            }
        }
        return res;
    }

    static private final long PRIME1 = Math.round(1E9) + 7;
    static private final long PRIME2 = Math.round(1E9) + 9;// 4249;
    static private final long X = 263;

    private Answer matchLenStep(String s, String t, int pLen) {
        SearchRes resa, resb;
        long[] hashTable = hashTable(s, pLen, PRIME1, X);
        Map<Long, Integer> hashDict = hashDict(t, pLen, PRIME1, X);
        resa = searchSubString(hashTable, hashDict);
        if (resa.check) {

            // Try a new prime value set
            hashTable = hashTable(s, pLen, PRIME2, X);
            hashDict = hashDict(t, pLen, PRIME2, X);
            resb = searchSubString(hashTable, hashDict);
            for (int i : resa.bIndex.keySet()) {
                // find the 1st indices pair that both prime hash
                // matches
                if (resb.bIndex.containsKey(i)) {
                    int j = resb.bIndex.get(i);
                    return new Answer(i, j, pLen);
                }
            }
        }
        return new Answer(0, 0, 0);
    }

    public Answer solve(String s, String t) {
        Answer ans = new Answer(0, 0, 0);
        int minLen = 0, maxLen = Math.min(s.length(), t.length());
        while (minLen <= maxLen) {
            int pLen = (minLen + maxLen) / 2;
            Answer stepAns = matchLenStep(s, t, pLen);
            // if we find match
            if (stepAns.len > 0) {
                // save answer if we get better length
                if (stepAns.len > ans.len)
                    ans = new Answer(stepAns);
                // search for longer match
                minLen = pLen + 1;
            } else {
                // search for shorter match
                maxLen = pLen - 1;
            }
        }
        return ans;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            String s = tok.nextToken();
            String t = tok.nextToken();
            Answer ans;
            if (USE_NAIVE)
                ans = naieveSolve(s, t);
            else
                ans = solve(s, t);

            out.format("%d %d %d\n", ans.i, ans.j, ans.len);
        });
        out.close();
    }

    static public void main(String[] args) {
        new common_substring().run();
    }
}
