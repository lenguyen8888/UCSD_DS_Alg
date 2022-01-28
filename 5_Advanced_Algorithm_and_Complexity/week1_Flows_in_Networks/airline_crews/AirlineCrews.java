import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

//public class MaxMatching {
public class AirlineCrews {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new AirlineCrews().solve();
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        boolean[][] bipartiteGraph = readData();
        int[] matching = findMatching(bipartiteGraph);
        writeResponse(matching);
        out.close();
    }

    boolean[][] readData() throws IOException {
        int numLeft = in.nextInt();
        int numRight = in.nextInt();
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (in.nextInt() == 1);
        return adjMatrix;
    }

//    private int[] findMatching(boolean[][] bipartiteGraph) {
//        // Replace this code with an algorithm that finds the maximum
//        // matching correctly in all cases.
//        int numLeft = bipartiteGraph.length;
//        int numRight = bipartiteGraph[0].length;
//
//        int[] matching = new int[numLeft];
//        Arrays.fill(matching, -1);
//        boolean[] busyRight = new boolean[numRight];
//        for (int i = 0; i < numLeft; ++i)
//            for (int j = 0; j < numRight; ++j)
//                if (bipartiteGraph[i][j] && matching[i] == -1 && !busyRight[j]) {
//                    matching[i] = j;
//                    busyRight[j] = true;
//                }
//        return matching;
//    }

    private int[] findMatching(boolean[][] bipartiteGraph) {
        // Replace this code with an algorithm that finds the maximum
        // matching correctly in all cases.
        int numFlight = bipartiteGraph.length;
        int numCrew = bipartiteGraph[0].length;

        FlowGraph graph = new FlowGraph(numFlight + numCrew + 2); // 2 nodes for source and sink
        final int SOURCE = 0;
        final int SINK = graph.size() - 1;
        for (int i = 0; i < numFlight; ++i)
            graph.addEdge(SOURCE, i + 1, 1);
        for (int i = 0; i < numCrew; ++i) {
            int crewId = numFlight + 1 + i;
            graph.addEdge(crewId, SINK, 1);
        }

        for (int i = 0; i < numFlight; ++i) {
            for (int j = 0; j < numCrew; ++j) {
                if (bipartiteGraph[i][j]) {
                    int flightNode = i + 1;
                    int crewNode = numFlight + 1 + j;
                    graph.addEdge(flightNode, crewNode, 1);
                }
            }
        }
        int maxFlow = graph.maxFlow(SOURCE, SINK);
        int[] matching = new int[numFlight];
        Arrays.fill(matching, -1);
        for (int i = 0; i < numFlight; ++i) {
            int flightNode = i + 1;
            for (Integer edgeId : graph.getIds(flightNode)) {
                if (graph.edges.get(edgeId).flow == 1) {
                    int crew = graph.edges.get(edgeId).to - numFlight - 1;
                    matching[i] = crew;
                }

            }
        }
        return matching;
    }

    private void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                out.print(" ");
            }
            if (matching[i] == -1) {
                out.print("-1");
            } else {
                out.print(matching[i] + 1);
            }
        }
        out.println();
    }

    // FlowGraph implementation

    /*
     * This class implements a bit unusual scheme to store the graph edges, in order
     * to retrieve the backward edge for a given edge quickly.
     */
    static class FlowGraph {
        private static class Edge {
            int from, to, capacity, flow;

            public Edge(int from, int to, int capacity) {
                this.from = from;
                this.to = to;
                this.capacity = capacity;
                this.flow = 0;
            }
        }

        /* List of all - forward and backward - edges */
        private List<Edge> edges;

        /* These adjacency lists store only indices of edges from the edges list */
        private List<Integer>[] graph;

        private int[] prev;

        private boolean[] visited;

        public FlowGraph(int n) {
            this.graph = (ArrayList<Integer>[]) new ArrayList[n];
            for (int i = 0; i < n; ++i)
                this.graph[i] = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

        public void addEdge(int from, int to, int capacity) {
            /*
             * Note that we first append a forward edge and then a backward edge, so all
             * forward edges are stored at even indices (starting from 0), whereas backward
             * edges are stored at odd indices.
             */
            Edge forwardEdge = new Edge(from, to, capacity);
            Edge backwardEdge = new Edge(to, from, 0);
            graph[from].add(edges.size());
            edges.add(forwardEdge);
            graph[to].add(edges.size());
            edges.add(backwardEdge);
        }

        public int size() {
            return graph.length;
        }

        public List<Integer> getIds(int from) {
            return graph[from];
        }

        public Edge getEdge(int id) {
            return edges.get(id);
        }

        public void addFlow(int id, int flow) {
            /*
             * To get a backward edge for a true forward edge (i.e id is even), we should
             * get id + 1 due to the described above scheme. On the other hand, when we have
             * to get a "backward" edge for a backward edge (i.e. get a forward edge for
             * backward - id is odd), id - 1 should be taken.
             *
             * It turns out that id ^ 1 works for both cases. Think this through!
             */
            edges.get(id).flow += flow;
            edges.get(id ^ 1).flow -= flow;
        }

        // private helper functions

        int maxFlow(int from, int to) {
            int flow = 0;
            List<Integer> edgeList = new ArrayList<>();
            while (true) {
                // Implement Edmond-Karp BFS augmented class selection
                bfsPath(from, to, edgeList);
                if (edgeList.size() > 0) {
                    int minFlow = findMinFlow(edgeList);
                    flow += minFlow;
                    for (int edgeId : edgeList) {
                        addFlow(edgeId, minFlow);
                    }
                } else {
                    break;
                }
            }
            return flow;
        }

        /**
         * Must handle parallel edges => we need to return edgeList
         * 
         * @param from
         * @param to
         * @param edgeList
         */
        private void bfsPath(int from, int to, List<Integer> edgeList) {
            initBFS();

            Deque<Integer> queue = new ArrayDeque<>();

            // Save the edge we arrive at node v
            int[] arriveEdgeMap = new int[size()];
            Arrays.fill(arriveEdgeMap, -1);
            // BFS traversal
            queue.add(from);
            int u, v;
            while (!queue.isEmpty()) {
                u = queue.remove();
                visited[u] = true;
                if (u == to)
                    break;
                for (int edgeId : getIds(u)) {
                    Edge edge = edges.get(edgeId);
                    v = edge.to;
                    if (!visited[v] && hasFlow(edgeId)) {
                        queue.add(v);
                        prev[v] = u;
                        arriveEdgeMap[v] = edgeId;
                    }
                }
            }

            // Collect BFS path in stack to reverse
            Deque<Integer> stack = new ArrayDeque<>();
            if (visited[to]) {
                v = to;
                stack.push(v);
                while (v != from) {
                    v = prev[v];
                    stack.push(v);
                }
            }
            Integer[] path = new Integer[stack.size()];
            path = stack.toArray(path);
            edgeList.clear();
            for (int i = 1; i < path.length; ++i)
                edgeList.add(arriveEdgeMap[path[i]]);
        }

        private int findMinFlow(List<Integer> edgeList) {
            int minFlow = Integer.MAX_VALUE;
            for (int edgeId : edgeList) {
                if (minFlow > edgeMaxFlow(edgeId))
                    minFlow = edgeMaxFlow(edgeId);
            }
            return minFlow;
        }

        private int edgeMaxFlow(int edgeId) {
            return edges.get(edgeId).capacity - edges.get(edgeId).flow;
        }

        private boolean hasFlow(int edgeId) {
            return edgeMaxFlow(edgeId) > 0;
        }

        /**
         * 
         */
        private void initBFS() {
            prev = new int[size()];
            visited = new boolean[size()];
            for (int i = 0; i < size(); ++i) {
                prev[i] = -1;
                visited[i] = false;
            }
        }

    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
