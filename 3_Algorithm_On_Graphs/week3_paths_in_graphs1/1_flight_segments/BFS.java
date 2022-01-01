import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class BFS {
//    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
//        //write your code here
//        return -1;
//    }
    private static int nVertices;

    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        LinkedList<Integer> bfsQ = new LinkedList<>();
        int dist[] = new int[nVertices];
        int prev[] = new int[nVertices];
        for (int i = 0; i < nVertices; ++i) {
            dist[i] = -1;
            prev[i] = -1;
        }
        bfsQ.add(s);
        dist[s] = 0;
        prev[s] = -1;
        while (!bfsQ.isEmpty()) {
            int parent = bfsQ.pop();
            for (int node : adj[parent]) {
                if (dist[node] < 0) {
                    dist[node] = dist[parent] + 1;
                    prev[node] = parent;
                    bfsQ.add(node);
                }
            }
        }
        return dist[t];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        nVertices = n;
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}
