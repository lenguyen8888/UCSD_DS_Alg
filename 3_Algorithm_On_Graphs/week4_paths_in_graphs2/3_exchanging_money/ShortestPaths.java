import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortestPaths {

//    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {
//      //write your code here
//    }
    private static int nV;
    private static final long MAX_WEIGHT = Math.round(1E4);

    /**
     * 
     */
    private static void initData() {
    }

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance,
            int[] reachable, int[] shortest) {
        ArrayDeque<Integer> negLoop = new ArrayDeque<>();
        distance[s] = 0;
        reachable[s] = 1;
        for (int i = 0; i < nV; ++i) {
            for (int u = 0; u < nV; ++u) {
                for (int j = 0; j < adj[u].size(); ++j) {
                    int v = adj[u].get(j);
                    int w = cost[u].get(j);
                    if (reachable[u] != 0 && distance[v] > distance[u] + w) {
                        distance[v] = distance[u] + w;
                        reachable[v] = 1;
                        if (i == nV - 1) {
                            negLoop.add(v);
                            shortest[v] = 0;
                        }
                    }
                }
            }
        }
        // mark nodes reachable by negLoop
        boolean[] visited = new boolean[nV];
        for (int i = 0; i < nV; ++i)
            visited[i] = false;
        while (!negLoop.isEmpty()) {
            int u = negLoop.pop();
            for (int v : adj[u]) {
                if (!visited[v]) {
                    shortest[v] = 0;
                    negLoop.add(v);
                    visited[v] = true;
                }
            }
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

}
