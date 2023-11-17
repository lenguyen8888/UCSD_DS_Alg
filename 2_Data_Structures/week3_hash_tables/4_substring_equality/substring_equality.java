import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class substring_equality {

    static long fastExp(long base, long exp, long modulo) {
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

    private class SubStrHash {
        private String text;
        private long prime, x;
        private long[] table;

        public SubStrHash(String text, long prime, long x) {
            this.text = text;
            this.prime = prime;
            this.x = x;
            buildHashTable();
        }

        private void buildHashTable() {
            table = new long[text.length() + 1];
            table[0] = 0;
            for (int i = 1; i < text.length() + 1; ++i) {
                table[i] = (table[i - 1] * x + text.charAt(i - 1) + prime) % prime;
            }
        }

        public long getHash(int start, int length) {
            assert (start >= 0 && start < text.length());
            assert ((start + length) < text.length());
            assert (length > 0);
            long y = fastExp(x, length, prime);
            long hashVal = (table[start + length] - y * table[start]) % prime;
            if (hashVal < 0)
                hashVal += prime;
            return hashVal;
        }

    }

    public class Solver {
        private String s;

        public Solver(String s) {
            this.s = s;
        }

        public boolean ask(int a, int b, int l) {
            return s.substring(a, a + l).equals(s.substring(b, b + l));
        }

    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String s = in.next();
        int q = in.nextInt();
//        Solver solver = new Solver(s);
        final long PRIME1 = Math.round(1E9) + 7;
        final long PRIME2 = Math.round(1E9) + 9;
        final long X = 263;
        SubStrHash hash1 = new SubStrHash(s, PRIME1, X);
        SubStrHash hash2 = new SubStrHash(s, PRIME2, X);
        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int l = in.nextInt();
            Boolean hash1Match = hash1.getHash(a, l) == hash1.getHash(b, l);
            Boolean hash2Match = hash2.getHash(a, l) == hash2.getHash(b, l);
            if (hash1Match && hash2Match)
                out.println("Yes");
            else
                out.println("No");
//            out.println(solver.ask(a, b, l) ? "Yes" : "No");
        }
        out.close();
    }

    static public void main(String[] args) throws IOException {
        new substring_equality().run();
    }

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
}
