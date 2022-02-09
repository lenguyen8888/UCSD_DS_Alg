import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class GSMNetwork {
    private final InputReader reader;
    private final OutputWriter writer;

    public GSMNetwork(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new GSMNetwork(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    static class CombSubSet {

        CombSubSet(int n, int r) {
        }

        // nCr = n!/((n-r)!*r!)
        static int size(int n, int r) {
            long numer = 1, deno = 1;

            // numer == n!/k! == n*(n-1)...*(k+1)
            for (int i = r + 1; i <= n; ++i)
                numer *= i;

            // deno == (n-2)! == 1 * 2 ... * (n-r)
            for (int i = 1; i <= n - r; ++i)
                deno *= i;

            return (int) (numer / deno);
        }

        private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
            if (index == data.length) {
                int[] combination = data.clone();
                combinations.add(combination);
            } else if (start <= end) {
                data[index] = start;
                helper(combinations, data, start + 1, end, index + 1);
                helper(combinations, data, start + 1, end, index);
            }
        }

        static List<int[]> getSubSet(int n, int r) {
            List<int[]> combinations = new ArrayList<>();
            helper(combinations, new int[r], 0, n - 1, 0);
            return combinations;
        }

        static List<int[]> getSubSet(int[] data, int r) {
            List<int[]> combinations = new ArrayList<>();
            List<int[]> retVal = new ArrayList<>();
            int n = data.length;
            if (n >= r) {
                helper(combinations, new int[r], 0, n - 1, 0);
            }
            for (int[] indices : combinations) {
                int[] values = indices.clone();
                for (int i = 0; i < r; ++i)
                    values[i] = data[indices[i]];
                retVal.add(values);
            }
            return retVal;
        }
    }

    class ConvertGSMNetworkProblemToSat {
        int numVertices;
        Edge[] edges;

        List<int[]> clauses;
        private static final int NUM_COLOR = 3;

        ConvertGSMNetworkProblemToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
            init();
        }

        class Node {
            private int nodeId;

            Node(int nodeId) {
                this.nodeId = nodeId;
            }

            // all literals must be > 0 since -0 == 0
            int[] getAllColors() {
                int[] colors = new int[NUM_COLOR];
                for (int i = 0; i < colors.length; ++i) {
                    colors[i] = getColor(i);
                }
                return colors;
            }

            int getColor(int colorId) {
                assert (0 <= colorId && colorId < NUM_COLOR);
                return (nodeId - 1) * NUM_COLOR + colorId + 1;
            }
        }

        /**
         * 
         */
        private void init() {
            clauses = new ArrayList<int[]>();
        }

        private void exactly_one_of(int[] literals) {
            int[] tempArray = literals.clone();

            // Translate from python code
            // Add all literals to be true
            clauses.add(tempArray);

            // Add pairwise negation => we are sure to have only 1 literal == true
            List<int[]> pairs = CombSubSet.getSubSet(literals, 2);
            for (int[] pair : pairs) {
                tempArray = pair.clone();
                tempArray[0] = -pair[0];
                tempArray[1] = -pair[1];
                clauses.add(tempArray);
            }
        }

//        void printEquisatisfiableSatFormula() {
//            // This solution prints a simple satisfiable formula
//            // and passes about half of the tests.
//            // Change this function to solve the problem.
//            writer.printf("3 2\n");
//            writer.printf("1 2 0\n");
//            writer.printf("-1 -2 0\n");
//            writer.printf("1 -2 0\n");
//        }
        void printEquisatisfiableSatFormula() {
            buildSATClauses();

            writer.printf("%d %d\n", clauses.size(), numVertices * NUM_COLOR);
            for (int[] clause : clauses) {
                for (int i : clause)
                    writer.printf("%d ", i);
                writer.printf("0\n");
            }
        }

        /**
         * 
         */
        private void buildSATClauses() {
            for (int i = 1; i <= numVertices; ++i) {
                Node node = new Node(i);
                exactly_one_of(node.getAllColors());
            }
            for (Edge edge : edges) {
                Node u = new Node(edge.from);
                Node v = new Node(edge.to);

                // For each edge we make sure the 2 adjacent nodes
                // are of different color
                for (int i = 0; i < NUM_COLOR; ++i) {
                    int[] literal = new int[2];
                    literal[0] = -u.getColor(i);
                    literal[1] = -v.getColor(i);
                    clauses.add(literal);
                }
            }
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }

        converter.printEquisatisfiableSatFormula();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
