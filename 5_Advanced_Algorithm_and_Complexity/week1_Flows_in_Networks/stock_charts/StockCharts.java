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

public class StockCharts {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new StockCharts().solve();
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        int[][] stockData = readData();
        int result = minCharts(stockData);
        writeResponse(result);
        out.close();
    }

    int[][] readData() throws IOException {
        int numStocks = in.nextInt();
        int numPoints = in.nextInt();
        int[][] stockData = new int[numStocks][numPoints];
        for (int i = 0; i < numStocks; ++i)
            for (int j = 0; j < numPoints; ++j)
                stockData[i][j] = in.nextInt();
        return stockData;
    }

//    private int minCharts(int[][] stockData) {
//        // Replace this incorrect greedy algorithm with an
//        // algorithm that correctly finds the minimum number
//        // of charts on which we can put all the stock data
//        // without intersections of graphs on one chart.
//
//        int numStocks = stockData.length;
//        // Each chart is a list of indices of its stocks.
//        List<List<Integer>> charts = new ArrayList<>();
//        for (int i = 0; i < numStocks; ++i) {
//            boolean added = false;
//            for (List<Integer> chart : charts) {
//                boolean canAdd = true;
//                for (int index : chart)
//                    if (!compare(stockData[i], stockData[index]) && !compare(stockData[index], stockData[i])) {
//                        canAdd = false;
//                        break;
//                    }
//                if (canAdd) {
//                    added = true;
//                    chart.add(i);
//                    break;
//                }
//            }
//            if (!added) {
//                List<Integer> newChart = new ArrayList<Integer>();
//                newChart.add(i);
//                charts.add(newChart);
//            }
//        }
//        return charts.size();
//    }
    private int minCharts(int[][] stockData) {
        // Replace this incorrect greedy algorithm with an
        // algorithm that correctly finds the minimum number
        // of charts on which we can put all the stock data
        // without intersections of graphs on one chart.

        int numStocks = stockData.length;
        FlowGraph graph = buildChartDAG(stockData);
        final int SOURCE = 0;
        final int SINK = graph.size() - 1;
        int maxFlow = graph.maxFlow(SOURCE, SINK);
        return numStocks - maxFlow;
    }

    /**
     * @param stockData
     */
    FlowGraph buildChartDAG(int[][] stockData) {
        int numStocks = stockData.length;
        FlowGraph graph = new FlowGraph(2 * numStocks + 2); // Create Bipartite Graph
        final int SOURCE = 0;
        final int SINK = graph.size() - 1;
        for (int i = 0; i < numStocks; ++i)
            graph.addEdge(SOURCE, i + 1, 1);
        for (int i = 0; i < numStocks; ++i) {
            int shareChartId = numStocks + 1 + i;
            graph.addEdge(shareChartId, SINK, 1);
        }
        // let's take numStock == 5, the bipartite node mapping
        // Stock(i) Stock(j)
        // ........./- 1 ??? 6 -\
        // ......../-- 2 ??? 7 --\
        // src(0)->... 3 ??? 8 --->snk(11)
        // ........\-- 4 ??? 9 --/
        // .........\- 5 ?? 10 -/
        // we need to get the left index and right index calculation right

        for (int i = 0; i < numStocks; ++i) {
            for (int j = i + 1; j < numStocks; ++j) {
                // Relation is a strict order from chart above to the one below
                // That way the relation is transitive
                if (above(stockData[i], stockData[j])) {
                    int leftIndex = i + 1;
                    int rightIndex = numStocks + j + 1;
                    graph.addEdge(leftIndex, rightIndex, 1);
                } else if (below(stockData[i], stockData[j])) {
                    int leftIndex = j + 1;
                    int rightIndex = numStocks + i + 1;
                    graph.addEdge(leftIndex, rightIndex, 1);
                }
            }
        }
        return graph;
    }

    boolean compare(int[] stock1, int[] stock2) {
        for (int i = 0; i < stock1.length; ++i)
            if (stock1[i] >= stock2[i])
                return false;
        return true;
    }

    boolean above(int[] stock1, int[] stock2) {
        assert (stock1.length == stock2.length);

        boolean above = true;
        // Check if stock1 chart is always above stock2 chart
        for (int i = 0; i < stock1.length && above; ++i) {
            above &= stock1[i] > stock2[i];
        }
        return above;
    }

    boolean below(int[] stock1, int[] stock2) {
        assert (stock1.length == stock2.length);

        boolean below = true;
        // Check if stock1 chart is always below stock2 chart
        for (int i = 0; i < stock1.length && below; ++i) {
            below &= stock1[i] < stock2[i];
        }
        return below;
    }

    private void writeResponse(int result) {
        out.println(result);
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
