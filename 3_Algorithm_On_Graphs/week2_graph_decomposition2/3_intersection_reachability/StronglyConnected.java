import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class StronglyConnected {
    private static int[] visited;
    private static int nVertices;

    private static void initDfs() {
        visited = new int[nVertices];
        Arrays.fill(visited, 0);
    }

    private final static int DFS_START = 1;
    private final static int DFS_DONE = 2;

    private static void visit(int node, int color) {
        visited[node] = color;
    }

    private static boolean hasVisited(int node) {
        return visited[node] != 0;
    }

    private static Deque<Integer> toposort(ArrayList<Integer>[] adj) {
        Deque<Integer> order = new ArrayDeque<Integer>();
        initDfs();
        for (int i = 0; i < nVertices; ++i) {
            if (!hasVisited(i))
                dfs(adj, order, i);
        }
        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, Deque<Integer> order, int s) {
        visit(s, DFS_START);
        for (int node : adj[s]) {
            if (visited[node] == 0) {
                dfs(adj, order, node);
            } else if (visited[node] == DFS_START) {
                assert (false);
            }
        }
        order.addFirst(s);
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

    private static int findSCCs(ArrayList<Integer>[] adj, List<Deque<Integer>> components, int[] sccNumber) {
        // write your code here
        ArrayList<Integer>[] revAdj = reverseGraph(adj);
        Deque<Integer> order = toposort(revAdj);

        initDfs();
        Deque<Integer> newOrder = new ArrayDeque<>();
        int numSCC = 0;
        for (int node : order) {
            if (visited[node] == 0) {
                Deque<Integer> scc = new ArrayDeque<>();
                dfs(adj, scc, node);
                components.add(scc);
                for (int i : scc) {
                    newOrder.addFirst(i);
                    sccNumber[i] = numSCC;
                }
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
        List<Deque<Integer>> components = new ArrayList<Deque<Integer>>();
        int[] sccNumbers = new int[nVertices];
        System.out.println(findSCCs(adj, components, sccNumbers));
    }
}
