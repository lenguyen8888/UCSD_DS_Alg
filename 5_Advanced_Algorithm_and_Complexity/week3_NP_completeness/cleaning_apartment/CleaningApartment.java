import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class CleaningApartment {
    private final InputReader reader;
    private final OutputWriter writer;

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

    public CleaningApartment(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CleaningApartment(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;

        Edge() {
            from = 0;
            to = 0;
        }

        Edge(int u, int v) {
            from = u;
            to = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Edge edge = (Edge) o;

            if (from != edge.from) {
                return false;
            }
            if (to != edge.to) {
                return false;
            }

            return true;
        }

        private final int LARGE_PRIME = 1009;

        @Override
        public int hashCode() {
            return LARGE_PRIME * from + to;
        }
    }

    class ConvertHampathToSat {
        static private final boolean USE_OBJ_HASH = true;

        int numVertices;
        Edge[] edges;

        private List<int[]> clauses;

        private HashSet<Integer> edgeSet;
        private HashSet<Edge> edgeObjSet;

        ConvertHampathToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
            edgeSet = new HashSet<>();
            edgeObjSet = new HashSet<>();
            clauses = new ArrayList<>();
        }

        int varMap(int node, int pos) {
            return (node - 1) * numVertices + (pos - 1) + 1;
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
            writer.printf("%d %d\n", clauses.size(), numVertices * numVertices);
            for (int[] clause : clauses) {
                for (int i : clause)
                    writer.printf("%d ", i);
                writer.printf("0\n");
            }
        }

        int edgeMap(int u, int v) {
            return u * numVertices + v;
        }

        /**
         * 
         */
        private void buildSATClauses() {

            // Build numVertices paths with exactly 1 node at each position
            for (int node = 1; node <= numVertices; ++node) {
                int[] allPos = new int[numVertices];
                for (int pos = 1; pos <= numVertices; ++pos) {
                    allPos[pos - 1] = varMap(node, pos);
                }
                exactly_one_of(allPos);
            }

            // The reverse match of each position to a node
            for (int pos = 1; pos <= numVertices; ++pos) {
                int[] allNodes = new int[numVertices];
                for (int node = 1; node <= numVertices; ++node) {
                    allNodes[node - 1] = varMap(node, pos);
                }
                exactly_one_of(allNodes);
            }

            buildNonAdjacentEdges();
        }

        /**
         * 
         */
        private void buildNonAdjacentEdges() {
            edgeSet.clear();
            for (Edge edge : edges) {
                edgeSet.add(edgeMap(edge.from, edge.to));
                edgeSet.add(edgeMap(edge.to, edge.from));
            }

            edgeObjSet.clear();
            for (Edge edge : edges) {
                edgeObjSet.add(edge);
                Edge revEdge = new Edge(edge.to, edge.from);
                edgeObjSet.add(revEdge);
            }
            int[] nodeList = new int[numVertices];
            for (int i = 1; i <= numVertices; ++i)
                nodeList[i - 1] = i;
            List<int[]> allEdges = CombSubSet.getSubSet(nodeList, 2);
            for (int[] edge : allEdges) {
                int u = edge[0];
                int v = edge[1];
                if (!hasEdge(u, v)) {
                    // if u and v are not from the original graph
                    // they can be next to each other (pos - 1, pos)
                    for (int pos = 2; pos <= numVertices; ++pos) {

                        // u before v
                        int[] pair = new int[2];
                        pair[0] = -varMap(u, pos - 1);
                        pair[1] = -varMap(v, pos);
                        clauses.add(pair);

                        // v before u
                        pair = new int[2];
                        pair[0] = -varMap(v, pos - 1);
                        pair[1] = -varMap(u, pos);
                        clauses.add(pair);
                    }
                }
            }
        }

        private boolean hasEdge(int u, int v) {
            if (USE_OBJ_HASH)
                return edgeObjSet.contains(new Edge(u, v));
            else
                return edgeSet.contains(edgeMap(u, v));
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
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
