import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class CircuitDesign {

    static class StronglyConnected {
        private static int[] visited;
        private static int nVertices;

        private static void initDfs() {
            visited = new int[nVertices];
            Arrays.fill(visited, 0);
        }

        private final static int DFS_START = 1;
        private final static int DFS_DONE = 2;

        private static void visit(int node, int color) {
            visited[node] = color;
        }

        private static boolean hasVisited(int node) {
            return visited[node] != 0;
        }

        private static Deque<Integer> toposort(ArrayList<Integer>[] adj) {
            Deque<Integer> order = new ArrayDeque<Integer>();
            initDfs();
            for (int i = 0; i < nVertices; ++i) {
                if (!hasVisited(i))
                    dfs(adj, order, i);
            }
            return order;
        }

        private static void dfs(ArrayList<Integer>[] adj, Deque<Integer> order, int s) {
            visit(s, DFS_START);
            for (int node : adj[s]) {
                if (visited[node] == 0) {
                    dfs(adj, order, node);
                } else if (visited[node] == DFS_START) {
                    assert (false);
                }
            }
            order.addFirst(s);
            visit(s, DFS_DONE);
        }

        private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] adj) {
            ArrayList<Integer>[] revAdj = (ArrayList<Integer>[]) new ArrayList[nVertices];
            for (int i = 0; i < nVertices; ++i) {
                revAdj[i] = new ArrayList<Integer>();
            }
            for (int i = 0; i < nVertices; ++i) {
                for (int j : adj[i]) {
                    revAdj[j].add(i);
                }
            }
            return revAdj;
        }

        private static int findSCCs(ArrayList<Integer>[] adj, List<Deque<Integer>> components, int[] sccNumber) {
            // write your code here
            ArrayList<Integer>[] revAdj = reverseGraph(adj);
            Deque<Integer> order = toposort(revAdj);

            initDfs();
            Deque<Integer> newOrder = new ArrayDeque<>();
            int numSCC = 0;
            for (int node : order) {
                if (visited[node] == 0) {
                    Deque<Integer> scc = new ArrayDeque<>();
                    dfs(adj, scc, node);
                    components.add(scc);
                    for (int i : scc) {
                        newOrder.addFirst(i);
                        sccNumber[i] = numSCC;
                    }
                    numSCC++;
                }
            }
            return numSCC;
        }
    }

    private final InputReader reader;
    private final OutputWriter writer;

    public CircuitDesign(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CircuitDesign(reader, writer).run();
        writer.writer.flush();
    }

    class Clause {
        int firstVar;
        int secondVar;
    }

    class CCompPair {
        Deque<Integer> comp;
        int ccNum;

        CCompPair(Deque<Integer> comp, int ccNum) {
            this.comp = comp;
            this.ccNum = ccNum;
        }
    }

    class TwoSatisfiability {
        int numVars;
        Clause[] clauses;

        final private static boolean USE_NEW_CODE = true;
        final private static boolean TEST_VAR_MAPPING = true;
        final private static boolean USE_2X_P2_MAP = true;

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }
        }

        boolean isSatisfiable(int[] result) {
            if (!USE_NEW_CODE)
                return origIsSatisfiable(result);

            final int nVertices = USE_2X_P2_MAP ? 2 * numVars + 2 : 2 * numVars;
            Deque<Integer> testVar = new ArrayDeque<>();
            testVarToNodeMapping(testVar);
            StronglyConnected.nVertices = nVertices;
            ArrayList<Integer>[] edges = (ArrayList<Integer>[]) new ArrayList[nVertices];
            for (int i = 0; i < edges.length; ++i)
                edges[i] = new ArrayList<>();
            for (Clause clause : clauses) {
                edges[varToNode(-clause.firstVar)].add(varToNode(clause.secondVar));
                edges[varToNode(-clause.secondVar)].add(varToNode(clause.firstVar));
            }

            List<Deque<Integer>> SCComps = new ArrayList<Deque<Integer>>();
            int[] sccNumber = new int[nVertices];
            int numSCC = StronglyConnected.findSCCs(edges, SCComps, sccNumber);
            for (int i = 1; i <= numVars; ++i) {
                if (sccNumber[varToNode(i)] == sccNumber[varToNode(-i)])
                    return false;
            }

            ArrayList<Integer>[] ccEdges = (ArrayList<Integer>[]) new ArrayList[numSCC];
            buildCCGraph(nVertices, edges, sccNumber, ccEdges);

            List<Deque<Integer>> revCComps = new ArrayList<Deque<Integer>>();

            reverseSortCCGraph(SCComps, numSCC, ccEdges, revCComps);

            boolean[] assigned = new boolean[nVertices];
            if (USE_2X_P2_MAP) {
                assigned[0] = assigned[1] = true;
            }
            int[] solution = new int[nVertices];

            for (Deque<Integer> scc : revCComps) {
                for (int node : scc) {
                    if (!assigned[node]) {
                        assigned[node] = true;
                        assigned[node ^ 1] = true;
                        solution[node] = 1;
                        solution[node ^ 1] = 0;
                    }
                }
            }
            for (int i = 1; i <= result.length; ++i)
                result[i - 1] = solution[varToNode(i)];
            return true;
        }

        /**
         * @param SCComps
         * @param numSCC
         * @param ccEdges
         * @param revCComps
         */
        private void reverseSortCCGraph(List<Deque<Integer>> SCComps, int numSCC, ArrayList<Integer>[] ccEdges,
                List<Deque<Integer>> revCComps) {
            StronglyConnected.nVertices = numSCC;
            Deque<Integer> post_order = StronglyConnected.toposort(ccEdges);
            Deque<Integer>[] compArray = (Deque<Integer>[]) new Deque[numSCC];
            int compIndex = 0;
            for (Deque<Integer> scc : SCComps)
                compArray[compIndex++] = scc;
            for (int i : post_order) {
                revCComps.add(compArray[i]);
            }
        }

        /**
         * @param nVertices
         * @param edges
         * @param sccNumber
         * @param ccEdges
         */
        private void buildCCGraph(final int nVertices, ArrayList<Integer>[] edges, int[] sccNumber,
                ArrayList<Integer>[] ccEdges) {
            for (int i = 0; i < ccEdges.length; ++i)
                ccEdges[i] = new ArrayList<>();
            for (int i = 0; i < nVertices; ++i) {
                for (int j : edges[i]) {
                    int u = sccNumber[i];
                    int v = sccNumber[j];
                    if (u != v) {
                        ccEdges[u].add(v);
                    }
                }
            }
        }

        /**
         * @param testVar
         */
        private void testVarToNodeMapping(Deque<Integer> testVar) {
            if (!TEST_VAR_MAPPING)
                return;
            for (int i = 1; i <= numVars; ++i) {
                testVar.add(varToNode(i));
                testVar.add(varToNode(-i));
            }
            Deque<Integer> revTest = new ArrayDeque<>();
            for (int i : testVar) {
                revTest.add(nodeToVar(i));
            }
        }

        private int nodeToVar(int node) {
            if (node < 0) {
                throw new IllegalArgumentException("node must be >= 0 " + node);
            }
            if (USE_2X_P2_MAP) {
                if ((node % 2) == 0)
                    return (node / 2);
                else
                    return -(node / 2);
            } else {
                if ((node % 2) == 0)
                    return (node / 2) + 1;
                else
                    return -((node - 1) / 2 + 1);
            }
        }

        private int varToNode(int i) {
            // mapping node..1 -> 0
            // ............ -1 -> 1
            // ............. 2 -> 2
            // ............ -2 -> 3
            // f(i) = i > 0 ? 2 * (i-1) :
            // ...... i < 0 ? 2 * (-i - 1) + 1
            // => f(i) = f(-i) ^ 1;

            int nodeNum;
            if (i == 0) {
                throw new IllegalArgumentException("node must be != 0 " + i);
            }
            if (USE_2X_P2_MAP) {
                if (i > 0) {
                    nodeNum = 2 * i;
                } else {
                    nodeNum = 2 * (-i) ^ 1;
                }
            } else {
                if (i > 0) {
                    nodeNum = 2 * (i - 1);
                } else {
                    nodeNum = 2 * (-i - 1) ^ 1;
                }
            }
            return nodeNum;
        }

        /**
         * @param result
         * @return
         */
        private boolean origIsSatisfiable(int[] result) {
            // This solution tries all possible 2^n variable assignments.
            // It is too slow to pass the problem.
            // Implement a more efficient algorithm here.
            for (int mask = 0; mask < (1 << numVars); ++mask) {
                for (int i = 0; i < numVars; ++i) {
                    result[i] = (mask >> i) & 1;
                }

                boolean formulaIsSatisfied = true;

                for (Clause clause : clauses) {
                    boolean clauseIsSatisfied = false;
                    if ((result[Math.abs(clause.firstVar) - 1] == 1) == (clause.firstVar < 0)) {
                        clauseIsSatisfied = true;
                    }
                    if ((result[Math.abs(clause.secondVar) - 1] == 1) == (clause.secondVar < 0)) {
                        clauseIsSatisfied = true;
                    }
                    if (!clauseIsSatisfied) {
                        formulaIsSatisfied = false;
                        break;
                    }
                }

                if (formulaIsSatisfied) {
                    return true;
                }
            }
            return false;
        }

    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].firstVar = reader.nextInt();
            twoSat.clauses[i].secondVar = reader.nextInt();
        }

        int result[] = new int[n];
        if (twoSat.isSatisfiable(result)) {
            writer.printf("SATISFIABLE\n");
            for (int i = 1; i <= n; ++i) {
                if (result[i - 1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
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
