import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Trie {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

//    List<Map<Character, Integer>> buildTrie(String[] patterns) {
//        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
//
//        // write your code here
//
//        return trie;
//    }
    Map<Integer, Map<Character, Integer>> buildTrie(String[] patterns) {
        Map<Integer, Map<Character, Integer>> trie = new HashMap<>();
        int new_node = 0;
        for (String pat : patterns) {
            int current_node = 0;
            for (int i = 0; i < pat.length(); ++i) {
                char current_symbol = pat.charAt(i);
                if (trie.containsKey(current_node) && trie.get(current_node).containsKey(current_symbol)) {
                    current_node = trie.get(current_node).get(current_symbol);
                } else {
                    new_node++;
                    if (!(trie.containsKey(current_node))) {
                        trie.put(current_node, new HashMap<>());
                    }
                    trie.get(current_node).put(current_symbol, new_node);
                    current_node = new_node;
                }

            }
        }
        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(Map<Integer, Map<Character, Integer>> trie) {
        for (int i : trie.keySet()) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        Map<Integer, Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
