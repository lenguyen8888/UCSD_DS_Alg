import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Bipartite {
    private static int nVertices;

//    private static int bipartite(ArrayList<Integer>[] adj) {
//        //write your code here
//        return -1;
//    }
    private static int bipartite(ArrayList<Integer>[] adj) {
        LinkedList<Integer> bfsQ = new LinkedList<>();
        int dist[] = new int[nVertices];
        for (int i = 0; i < nVertices; ++i) {
            dist[i] = -1;
        }
        for (int start = 0; start < nVertices; ++start) {
            if (dist[start] < 0) {
                bfsQ.add(start);
                dist[start] = 0;
            }
            while (!bfsQ.isEmpty()) {
                int parent = bfsQ.pop();
                for (int node : adj[parent]) {
                    if (dist[node] < 0) {
                        dist[node] = dist[parent] + 1;
                        bfsQ.add(node);
                    } else {
                        if (dist[parent] == dist[node])
                            return 0;
                    }
                }
            }
        }
        return 1;
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
        System.out.println(bipartite(adj));
    }
}
