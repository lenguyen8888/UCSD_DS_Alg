import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
    private static int nV;

    private static class Node implements Comparable<Node> {
        int id;
        int dist;

        Node(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            if (dist < o.dist)
                return -1;
            else if (dist > o.dist)
                return 1;
            else
                return 0;
        }
    }

    private static PriorityQueue<Node> distPQ;
    private static long[] dist;
    private static boolean[] visits;

    private static long distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        initDijikstra();

        distPQ.add(new Node(s, 0));
        dist[s] = 0;
        while (!distPQ.isEmpty()) {
            Node minNode = distPQ.remove();
            int u = minNode.id;
            if (!visits[u]) {
                visits[u] = true;
                for (int i = 0; i < adj[u].size(); ++i) {
                    int v = adj[u].get(i);
                    int w = cost[u].get(i);
                    if (dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        distPQ.add(new Node(v, (int) dist[v]));
                    }
                }
            }
        }
        if (dist[t] != Long.MAX_VALUE)
            return dist[t];
        else
            return -1;
    }

    /**
     * 
     */
    private static void initDijikstra() {
        distPQ = new PriorityQueue<>();
        dist = new long[nV];
        visits = new boolean[nV];
        for (int i = 0; i < dist.length; ++i) {
            dist[i] = Long.MAX_VALUE;
            visits[i] = false;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}
