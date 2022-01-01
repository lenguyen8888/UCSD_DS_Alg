import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StronglyConnected {
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

    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        ArrayList<Integer> order = new ArrayList<Integer>();
        initDfs();
        for (int i = 0; i < nVertices; ++i) {
            if (!visited.containsKey(i))
                dfs(adj, order, i);
        }
        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, ArrayList<Integer> order, int s) {
        visit(s, DFS_START);
        for (int node : adj[s]) {
            if (!visited.containsKey(node)) {
                dfs(adj, order, node);
            } else if (visited.get(node) == DFS_START) {
                assert (false);
            }
        }
        order.add(0, s);
        visit(s, DFS_DONE);
    }

    private static ArrayList<Integer>[] reverseGraph(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] revAdj = (ArrayList<Integer>[]) new ArrayList[nVertices];
        for (int i = 0; i < nVertices; ++i) {
            revAdj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < nVertices; ++i) {
            for (int j : adj[i]) {
                revAdj[j].add(i);
            }
        }
        return revAdj;
    }

    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        // write your code here
        ArrayList<Integer>[] revAdj = reverseGraph(adj);
        ArrayList<Integer> order = toposort(revAdj);

        initDfs();
        ArrayList<Integer> newOrder = new ArrayList<>();
        int numSCC = 0;
        for (int node : order) {
            if (!visited.containsKey(node)) {
                dfs(adj, newOrder, node);
                numSCC++;
            }
        }
        return numSCC;
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
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}
