import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class CircuitDesign {

    /**
     * Adapted from Robert Sedgewick Digraph class implementation
     * 
     * The {@code Digraph} class represents a directed graph of vertices named 0
     * through <em>V</em> - 1. It supports the following two primary operations: add
     * an edge to the digraph, iterate over all of the vertices adjacent from a
     * given vertex. It also provides methods for returning the indegree or
     * outdegree of a vertex, the number of vertices <em>V</em> in the digraph, the
     * number of edges <em>E</em> in the digraph, and the reverse digraph. Parallel
     * edges and self-loops are permitted.
     * <p>
     * This implementation uses an <em>adjacency-lists representation</em>, which is
     * a vertex-indexed array of {@link Bag} objects. It uses &Theta;(<em>E</em> +
     * <em>V</em>) space, where <em>E</em> is the number of edges and <em>V</em> is
     * the number of vertices. The <code>reverse()</code> method takes
     * &Theta;(<em>E</em> + <em>V</em>) time and space; all other instancce methods
     * take &Theta;(1) time. (Though, iterating over the vertices returned by
     * {@link #adj(int)} takes time proportional to the outdegree of the vertex.)
     * Constructing an empty digraph with <em>V</em> vertices takes
     * &Theta;(<em>V</em>) time; constructing a digraph with <em>E</em> edges and
     * <em>V</em> vertices takes &Theta;(<em>E</em> + <em>V</em>) time.
     * <p>
     * For additional documentation, see
     * <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
     * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
     *
     * @author Robert Sedgewick
     * @author Kevin Wayne
     */
    class Digraph {
        private int V;
        private int E;
        private Set<Integer>[] adj;
        private int[] indegree;

        Digraph(int V) {
            this.V = V;
            adj = (Set<Integer>[]) new HashSet[V];
            for (int i = 0; i < adj.length; ++i) {
                adj[i] = new HashSet<>();
            }
            indegree = new int[V];
        }

        /**
         * Initializes a new digraph that is a deep copy of the specified digraph.
         *
         * @param G the digraph to copy
         * @throws IllegalArgumentException if {@code G} is {@code null}
         */
        public Digraph(Digraph G) {
            if (G == null)
                throw new IllegalArgumentException("argument is null");

            this.V = G.V();
            this.E = G.E();
            if (V < 0)
                throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");

            // update indegrees
            indegree = new int[V];
            for (int v = 0; v < V; v++)
                this.indegree[v] = G.indegree(v);

            // update adjacency lists
            adj = (Set<Integer>[]) new HashSet[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new HashSet<Integer>();
            }

            for (int v = 0; v < G.V(); v++) {
                // reverse so that adjacency list is in same order as original
                Stack<Integer> reverse = new Stack<Integer>();
                for (int w : G.adj[v]) {
                    reverse.push(w);
                }
                for (int w : reverse) {
                    adj[v].add(w);
                }
            }
        }

        int V() {
            return V;
        }

        int E() {
            return E;
        }

        /**
         * Returns the vertices adjacent from vertex {@code v} in this digraph.
         *
         * @param v the vertex
         * @return the vertices adjacent from vertex {@code v} in this digraph, as an
         *         iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Integer> adj(int v) {
            validateVertex(v);
            return adj[v];
        }

        /**
         * Returns the number of directed edges incident from vertex {@code v}. This is
         * known as the <em>outdegree</em> of vertex {@code v}.
         *
         * @param v the vertex
         * @return the outdegree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int outdegree(int v) {
            validateVertex(v);
            return adj[v].size();
        }

        /**
         * Returns the number of directed edges incident to vertex {@code v}. This is
         * known as the <em>indegree</em> of vertex {@code v}.
         *
         * @param v the vertex
         * @return the indegree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int indegree(int v) {
            validateVertex(v);
            return indegree[v];
        }

        /**
         * Returns the reverse of the digraph.
         *
         * @return the reverse of the digraph
         */
        public Digraph reverse() {
            Digraph reverse = new Digraph(V);
            for (int v = 0; v < V; v++) {
                for (int w : adj(v)) {
                    reverse.addEdge(w, v);
                }
            }
            return reverse;
        }

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }

        /**
         * Adds the directed edge v→w to this digraph.
         *
         * @param v the tail vertex
         * @param w the head vertex
         * @throws IllegalArgumentException unless both {@code 0 <= v < V} and
         *                                  {@code 0 <= w < V}
         */
        public void addEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            adj[v].add(w);
            indegree[w]++;
            E++;
        }
    }

    class DigraphDFS {
        private int clock;
        private boolean[] visited;
        private int[] post;
        private Digraph graph;
        private Deque<Integer> postQueue;

        class IntPair {
            int node;
            int clkVal;

            IntPair(int node, int clkVal) {
                this.node = node;
                this.clkVal = clkVal;
            }
        }

        DigraphDFS(Digraph graph) {
            this.graph = new Digraph(graph);
            initDFS();
            DFS();
        }

        /**
         * 
         */
        private void initDFS() {
            clock = 1;
            final int V = this.graph.V();
            visited = new boolean[V];
            post = new int[V];
            postQueue = new ArrayDeque<>();
        }

        private void dfsHelper(int node) {
            visited[node] = true;
            for (int v : graph.adj(node)) {
                if (!visited[v])
                    dfsHelper(v);
            }
            post[node] = clock;
            clock++;
        }

        private void DFS() {
            for (int node = 0; node < graph.V(); ++node)
                dfsHelper(node);
            IntPair[] postArray = new IntPair[graph.V()];
            Arrays.sort(postArray, (IntPair a, IntPair b) -> {
                return Integer.signum(a.clkVal - b.clkVal);
            });
            postQueue.clear();
            for (IntPair val : postArray)
                postQueue.add(val.node);
        }

        public Iterable<Integer> postOrder() {
            return postQueue;
        }

        public Iterable<Integer> revPostOrder() {
            Stack<Integer> stack = new Stack<>();
            for (int v : postQueue)
                stack.push(v);
            return stack;
        }

    }

    class DigraphSCC {
        private Digraph graph;
        private boolean[] visited;

        int[] SCC_number;
        Deque<Set<Integer>> SCC;

        DigraphSCC(Digraph graph) {
            this.graph = new Digraph(graph);
            SCC_number = new int[graph.V()];
            SCC = new ArrayDeque<Set<Integer>>();
            visited = new boolean[graph.V()];

            findSCC();
        }

        /**
         * 
         */
        void findSCC() {
            DigraphDFS revDFS = new DigraphDFS(this.graph.reverse());
            int u = 1;
            for (int v : revDFS.revPostOrder()) {
                if (!visited[v]) {
                    Set<Integer> sccSet = new HashSet<Integer>();
                    Explore(v, sccSet, u);
                    SCC.add(sccSet);
                    u++;
                }
            }
        }

        void Explore(int i, Set<Integer> sccSet, int u) {
            visited[i] = true;
            sccSet.add(i);
            SCC_number[i] = u;
            for (int v : graph.adj(i)) {
                if (!visited[v])
                    Explore(v, sccSet, u);
            }

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

    class TwoSatisfiability {
        int numVars;
        Clause[] clauses;

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }
        }

        boolean isSatisfiable(int[] result) {
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
