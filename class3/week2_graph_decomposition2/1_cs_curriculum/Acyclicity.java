import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Acyclicity {
    private static HashMap<Integer, Integer> visited;
    private static int nVertices;
    private static boolean hasCycle;

    private static void visit(int node, int color) {
        visited.put(node, color);
    }

    private static void explore(int start, List<Integer> gAdj[]) {
        visit(start, 1);
        for (int node : gAdj[start]) {
            if (!visited.containsKey(node)) {
                explore(node, gAdj);
            } else if (visited.containsKey(node) && visited.get(node) == 1) {
                hasCycle = true;
            }
        }
        visit(start, 2);
    }

    private static void initExplore() {
        visited = new HashMap<>();
        hasCycle = false;
    }

//    private static int acyclic(ArrayList<Integer>[] adj) {
//        // write your code here
//        return 0;
//    }

    private static int acyclic(ArrayList<Integer>[] adj) {
        initExplore();
        for (int i = 0; i < nVertices; ++i) {
            if (!visited.containsKey(i)) {
                explore(i, adj);
                if (hasCycle)
                    return 1;
            }
        }
        return 0;
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
        System.out.println(acyclic(adj));
    }
}
