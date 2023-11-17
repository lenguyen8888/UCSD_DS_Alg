import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {
//    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
//        // write your code here
//        return 0;
//    }

    private static int nV;
    private static long[] dist;
    private static final long MAX_WEIGHT = Math.round(1E4);

    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        initData();
        startFromNegEdge(cost);
        for (int i = 0; i < nV; ++i) {
            for (int u = 0; u < nV; ++u) {
                for (int j = 0; j < adj[u].size(); ++j) {
                    int v = adj[u].get(j);
                    int w = cost[u].get(j);
                    // We need to
                    if (dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        if (i == nV - 1)
                            return 1;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * @param cost
     */
    private static void startFromNegEdge(ArrayList<Integer>[] cost) {
        for (int u = 0; u < nV; ++u) {
            for (int j = 0; j < cost[u].size(); ++j) {
                if (cost[u].get(j) < 0)
                    dist[u] = 0;
            }
        }
    }

    /**
     * 
     */
    private static void initData() {
        dist = new long[nV];
        for (int i = 0; i < dist.length; ++i) {
            dist[i] = MAX_WEIGHT + 1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        nV = n;
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}
