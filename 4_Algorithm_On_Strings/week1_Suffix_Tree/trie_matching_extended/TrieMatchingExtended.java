import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieMatchingExtended implements Runnable {
    class Node {
        public static final int Letters = 4;
        public static final int NA = -1;
        public int next[];
        public boolean patternEnd;

        Node() {
            next = new int[Letters];
            Arrays.fill(next, NA);
            patternEnd = false;
        }
    }

    int letterToIndex(char letter) {
        switch (letter) {
        case 'A':
            return 0;
        case 'C':
            return 1;
        case 'G':
            return 2;
        case 'T':
            return 3;
        default:
            assert (false);
            return Node.NA;
        }
    }

//    List<Integer> solve(String text, int n, List<String> patterns) {
//        List<Integer> result = new ArrayList<Integer>();
//
//        // write your code here
//
//        return result;
//    }

    Map<Integer, Map<Character, Integer>> buildTrie(List<String> patterns) {
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

    private boolean prefixTrieMatching(String text, Map<Integer, Map<Character, Integer>> trie) {
        int v = 0;
        for (int i = 0; i < text.length(); ++i) {
            char symbol = text.charAt(i);
            if (trie.containsKey(v) && trie.get(v).containsKey(symbol)) {
                v = trie.get(v).get(symbol);
                if (trie.containsKey(v) && trie.get(v).containsKey('$'))
                    return true;
            } else {
                return false;
            }
        }
        assert (false);
        return false; // Should never get here
    }

    private List<Integer> trieMatching(String text, Map<Integer, Map<Character, Integer>> trie) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < text.length(); ++i) {
            String suffix = text.substring(i);
            if (prefixTrieMatching(suffix, trie))
                positions.add(i);
        }
        return positions;
    }

    List<Integer> solve(String text, int n, List<String> patterns) {
        Map<Integer, Map<Character, Integer>> trie = buildTrie(patterns);
        List<Integer> result = trieMatching(text, trie);
        return result;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String text = in.readLine();
            text += "$";
            int n = Integer.parseInt(in.readLine());
            List<String> patterns = new ArrayList<String>();
            for (int i = 0; i < n; i++) {
                patterns.add(in.readLine() + "$");
            }

            List<Integer> ans = solve(text, n, patterns);

            for (int j = 0; j < ans.size(); j++) {
                System.out.print("" + ans.get(j));
                System.out.print(j + 1 < ans.size() ? " " : "\n");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Thread(new TrieMatchingExtended()).start();
    }
}
