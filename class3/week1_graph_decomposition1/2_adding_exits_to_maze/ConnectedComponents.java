import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ConnectedComponents {
    private static HashMap<Integer, Integer> visited;
    private static List<Integer> gAdj[];
    private static List<Integer> reach;
    private static int connCount;
    private static int nVertices;

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
        connCount = 0;
    }

//    private static int numberOfComponents(ArrayList<Integer>[] adj) {
//        int result = 0;
//        //write your code here
//        return result;
//    }

    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        int result = 0;
        // write your code here
        initExplore();
        for (int i = 0; i < nVertices; ++i) {
            if (!visited.containsKey(i)) {
                ++connCount;
                explore(i, adj);
            }
        }
        return connCount;
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
        nVertices = n;
        System.out.println(numberOfComponents(adj));
    }
}
