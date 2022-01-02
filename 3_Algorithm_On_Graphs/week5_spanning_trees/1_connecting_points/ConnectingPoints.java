import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ConnectingPoints {
//    private static double minimumDistance(int[] x, int[] y) {
//        double result = 0.;
//        //write your code here
//        return result;
//    }
    private static int nV;
    private static int[] parent, rank;

    private static Edge[] adj;

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        double distTo(Point o) {
            return Math.sqrt((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y));
        }
    }

    static class Edge {
        int u;
        int v;
        double w;

        Edge(int u, int v, double w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    /**
     * @param x
     * @param y
     */
    private static void buildEdgeList(int[] x, int[] y) {
        LinkedList<Edge> edgeList = new LinkedList<>();
        for (int i = 0; i < nV; ++i) {
            Point iPt = new Point(x[i], y[i]);
            for (int j = i + 1; j < nV; ++j) {
                Point jPt = new Point(x[j], y[j]);
                edgeList.add(new Edge(i, j, iPt.distTo(jPt)));
            }
        }
        adj = new Edge[edgeList.size()];
        for (int i = 0; i < adj.length; ++i) {
            adj[i] = edgeList.pop();
        }
        Arrays.sort(adj, (Edge a, Edge b) -> {
            if (a.w < b.w)
                return -1;
            if (a.w > b.w)
                return 1;
            return 0;
        });
    }

    /**
     * 
     */
    private static void makeDSet() {
        parent = new int[nV];
        rank = new int[nV];
        for (int i = 0; i < parent.length; ++i) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    private static int findDSet(int u) {
        if (u != parent[u]) {
            parent[u] = findDSet(parent[u]);
        }
        return parent[u];
    }

    private static void unionDSet(int u, int v) {
        int uParent = findDSet(u);
        int vParent = findDSet(v);
        if (vParent != uParent) {
            if (rank[uParent] > rank[vParent]) {
                parent[vParent] = parent[uParent];
            } else {
                parent[uParent] = parent[vParent];
                if (rank[uParent] == rank[vParent])
                    rank[vParent]++;
            }
        }
    }

    private static double minimumDistance(int[] x, int[] y) {
        makeDSet();
        buildEdgeList(x, y);

        double result = 0.0;
        for (Edge e : adj) {
            if (findDSet(e.u) != findDSet(e.v)) {
                result += e.w;
                unionDSet(e.u, e.v);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        nV = n;
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }
}
