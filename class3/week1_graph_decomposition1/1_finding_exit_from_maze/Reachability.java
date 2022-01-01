import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Reachability {
//    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
//        //write your code here
//        return 0;
//    }

    private static HashMap<Integer, Integer> visited;
    private static List<Integer> gAdj[];
    private static List<Integer> reach;

    private static void visit(int node) {
        reach.add(node);
        visited.put(node, 1);
    }

    private static void explore(int start, List<Integer> gAdj[]) {
        for (int node : gAdj[start]) {
            if (!visited.containsKey(node)) {
                visit(node);
                explore(node, gAdj);
            }
        }
    }

    private static void initExplore() {
        visited = new HashMap<>();
        reach = new ArrayList<>();
    }

    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        // write your code here
        initExplore();
        explore(x, adj);
        return reach.contains(y) ? 1 : 0;
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
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
}
