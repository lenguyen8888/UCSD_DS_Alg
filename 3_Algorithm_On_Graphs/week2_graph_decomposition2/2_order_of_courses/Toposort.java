import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Toposort {
    private static HashMap<Integer, Integer> visited;
    private static int nVertices;

    private static void initDfs() {
        visited = new HashMap<>();
    }

    private final static int DFS_START = 1;
    private final static int DFS_DONE = 2;

    private static void visit(int node, int color) {
        visited.put(node, color);
    }

//    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
//        int used[] = new int[adj.length];
//        ArrayList<Integer> order = new ArrayList<Integer>();
//        // write your code here
//        return order;
//    }
//
//    private static void dfs(ArrayList<Integer>[] adj, int[] used, ArrayList<Integer> order, int s) {
//        // write your code here
//    }

    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        int used[] = new int[adj.length];
        ArrayList<Integer> order = new ArrayList<Integer>();
        // write your code here
        initDfs();
        for (int i = 0; i < nVertices; ++i) {
            if (!visited.containsKey(i))
                dfs(adj, used, order, i);
        }
        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, int[] used, ArrayList<Integer> order, int s) {
        visit(s, DFS_START);
        for (int node : adj[s]) {
            if (!visited.containsKey(node)) {
                dfs(adj, used, order, node);
            } else if (visited.get(node) == DFS_START) {
                assert (false);
            }
        }
        order.add(0, s);
        visit(s, DFS_DONE);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
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
        }
        nVertices = n;
        ArrayList<Integer> order = toposort(adj);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
    }
}
