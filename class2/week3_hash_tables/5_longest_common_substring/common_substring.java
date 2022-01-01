import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class common_substring {
    private class StrHash {
        private String text;
        private int subLen;
        private long prime, x;
        private ArrayList<Long> table;
        private HashMap<Integer, Long> hashIndex;

        StrHash(String text, int subLen, long prime, long x) {
            this.text = text;
            this.subLen = subLen;
            this.prime = prime;
            this.x = x;
            buildTable();
        }

        long fastExp(long base, long exp, long modulo) {
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

        private long hashFunc(String s) {
            long hash = 0;
            for (int i = s.length() - 1; i >= 0; --i)
                hash = (hash * x + s.charAt(i)) % prime;
            return hash;
        }

        private void buildTable() {
            int tLen = text.length();
            table = new ArrayList<>(tLen + subLen + 1);
            String subStr = text.substring(tLen - subLen);
            long y = fastExp(x, subLen, prime);
            table.set(tLen + subLen, hashFunc(subStr));
            for (int i = tLen + subLen - 1; i >= 0; --i) {
                long tableVal = (x * table.get(i + 1) + text.charAt(i) - y * text.charAt(i)) % prime;
                if (tableVal < 0)
                    tableVal += prime;
                table.set(i, tableVal);
            }

            hashIndex = new HashMap<>();
            for (int i = 0; i < table.size(); ++i)
                hashIndex.put(i, table.get(i));
        }

        public ArrayList<Long> getHashTable() {
            return (new ArrayList<>(this.table));
        }

        public HashMap<Integer, Long> getHashDict() {
            return new HashMap<>(this.hashIndex);
        }
    }

    public class Answer {
        int i, j, len;

        Answer(int i, int j, int len) {
            this.i = i;
            this.j = j;
            this.len = len;
        }
    }

    public Answer solve(String s, String t) {
        Answer ans = new Answer(0, 0, 0);
        for (int i = 0; i < s.length(); i++)
            for (int j = 0; j < t.length(); j++)
                for (int len = 0; i + len <= s.length() && j + len <= t.length(); len++)
                    if (len > ans.len && s.substring(i, i + len).equals(t.substring(j, j + len)))
                        ans = new Answer(i, j, len);
        return ans;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            String s = tok.nextToken();
            String t = tok.nextToken();
            Answer ans = solve(s, t);
            out.format("%d %d %d\n", ans.i, ans.j, ans.len);
        });
        out.close();
    }

    static public void main(String[] args) {
        new common_substring().run();
    }
}
