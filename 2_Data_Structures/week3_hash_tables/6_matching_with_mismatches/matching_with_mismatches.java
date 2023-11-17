import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class matching_with_mismatches {
//    public List<Integer> solve(int k, String text, String pattern) {
//        ArrayList<Integer> pos = new ArrayList<>();
//        return pos;
//    }

    private static final long PRIME = Math.round(1e9) + 7;
    private static final long X = 263;
    private long[] h1Table, h2Table;

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

    private long[] hashTable(String s, long prime, long x) {
        int sLen = s.length();
        long[] retVal = new long[sLen + 1];
        retVal[0] = 0;
        for (int i = 1; i <= sLen; ++i) {
            long newHash = (x * retVal[i - 1] + s.charAt(i - 1)) % prime;
            if (newHash < 0)
                newHash += prime;
            retVal[i] = newHash;
        }
        return retVal;
    }

    private long hashValue(long[] table, long prime, long x, int start, int length) {
        long y = fastExp(x, length, prime);
        long val = (table[start + length] - y * table[start]) % prime;
        if (val < 0)
            val += prime;
        return val;
    }

    private void preCompute(String text, String pattern) {
        h1Table = hashTable(text, PRIME, X);
        h2Table = hashTable(pattern, PRIME, X);
    }

    class CheckData {
        int a, b, l, n;

        CheckData(int a, int b, int l, int n) {
            this.a = a;
            this.b = b;
            this.l = l;
            this.n = n;
        }
    }

    private boolean checkMatch(int start, int length, int pLen, int k) {
        ArrayDeque<CheckData> stack = new ArrayDeque<>();
        stack.add(new CheckData(start, 0, length, 1));
        stack.add(new CheckData(start + length, length, pLen - length, 1));

        int count = 0;
        int temp = 2;
        int C = 0;
        while (!stack.isEmpty()) {
            CheckData cur = stack.pop();
            long u1 = hashValue(h1Table, PRIME, X, cur.a, cur.l);
            long v1 = hashValue(h2Table, PRIME, X, cur.b, cur.l);
            if (temp != cur.n)
                count = C;
            if (u1 != v1) {
                count++;
                if (cur.l > 1) {
                    stack.add(new CheckData(cur.a, cur.b, cur.l / 2, cur.n + 1));
                    stack.add(new CheckData(cur.a + cur.l / 2, cur.b + cur.l / 2, cur.l - cur.l / 2, cur.n + 1));
                } else {
                    C++;
                }
            }
            if (count > k) {
                return false;
            }
            temp = cur.n;
        }

        if (count > k)
            return false;
        else
            return true;
    }

    public List<Integer> solve(int k, String text, String pattern) {
        ArrayList<Integer> pos = new ArrayList<>();
        preCompute(text, pattern);
        int traceLen = text.length() - pattern.length() + 1;
        for (int i = 0; i < traceLen; ++i) {
            if (checkMatch(i, pattern.length() / 2, pattern.length(), k))
                pos.add(i);
        }
        return pos;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            int k = Integer.valueOf(tok.nextToken());
            String s = tok.nextToken();
            String t = tok.nextToken();
            List<Integer> ans = solve(k, s, t);
            out.format("%d ", ans.size());
            out.println(ans.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(" ")));
        });
        out.close();
    }

    static public void main(String[] args) {
        new matching_with_mismatches().run();
    }
}
