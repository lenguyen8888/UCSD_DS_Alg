import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NonSharedSubstring implements Runnable {
    private Node root;

    private class Node {
        public String lab;
        public Map<Character, Node> next;
        public char type;
        public boolean visited;

        public Node parent;

        Node(String label) {
            this.lab = label;
            next = new HashMap<>();
            type = 'L';
            visited = false;
            parent = null;
        }

        public boolean hasChar(char c) {
            return next.containsKey(c);
        }

        public void add(char c, String label) {
            this.next.put(c, new Node(label));
        }

        public void add(char c, Node node) {
            this.next.put(c, node);
        }

        public Node get(char c) {
            return next.get(c);
        }

        public int count() {
            return next.size();
        }
    }

    private int prefixMatchCnt(String a, String b, int start) {
        int minLen = Math.min(a.length(), b.length());
        int matchCount = 0;
        for (int i = start; i < minLen; ++i) {
            if (a.charAt(i) == b.charAt(i))
                matchCount++;
            else
                break;
        }
        return matchCount;
    }

    /**
     * @param text
     */
    private void buildSuffixTree(String text) {
        root = new Node("");
        root.add(text.charAt(0), text);
        for (int i = 1; i < text.length(); ++i) {
            Node cur = root;
            int j = i;
            while (j < text.length()) {
                char cj = text.charAt(j);
                if (cur.hasChar(cj)) {
                    Node child = cur.get(cj);
                    String lab = child.lab;
                    int k = j + 1;
                    // Count matching charaters for subtext and label
                    // skipping the 1s character (to be used as next pointer if matched
                    int matchCount = prefixMatchCnt(text.substring(j), lab, 1);
                    if (matchCount + 1 == lab.length()) {
                        cur = child; // pick current child to traverse down
                        j += lab.length(); // advance j past lab string
                    } else {
                        // Break the label
                        k += matchCount;
                        char cExist = lab.charAt(k - j);
                        char cNew = text.charAt(k);
                        Node mid = new Node(lab.substring(0, k - j));
                        mid.add(cNew, text.substring(k));

                        // Update child.label
                        child.lab = lab.substring(k - j);
                        mid.add(cExist, child);
                        cur.add(cj, mid);
                    }
                } else {
                    cur.add(cj, text.substring(j));
                }
            }
        }
    }

    private void explore(Node cur, List<Node> LLeaves) {
        cur.visited = true;
        if (cur.count() == 0) {
            if (cur.lab.indexOf('#') == -1) {
                cur.type = 'R';
            } else {
                LLeaves.add(cur);
            }
        } else {
            for (Node node : cur.next.values()) {
                if (!node.visited) {
                    node.parent = cur;
                    explore(node, LLeaves);
                }
            }
            for (Node node : cur.next.values()) {
                if (node.type == 'R')
                    cur.type = 'R';
            }
        }

    }

//    String solve (String p, String q) {
//		String result = p;
//		return result;
//	}

    String solve(String p, String q) {
        List<Node> LLeaves = new ArrayList<>();
        String treeText = p + "#" + q + "$";
        buildSuffixTree(treeText);
        explore(root, LLeaves);
        List<String> results = new ArrayList<>();
        for (Node leaf : LLeaves) {
            String firstCharStr = "";
            String stringAcc = "";
            Node cur = leaf.parent;
            if (leaf.lab.charAt(0) == '#' && cur.type == 'R') {
                continue;
            } else if (cur.type == 'R') {
                firstCharStr += leaf.lab.charAt(0);
            }
            while (cur != root) {
                stringAcc = cur.lab + stringAcc;
                cur = cur.parent;
            }
            stringAcc += firstCharStr;
            results.add(stringAcc);
        }
        String result = "";
        int lastStrLen = -1;
        for (String str : results) {
            if (lastStrLen < 0 || str.length() < result.length())
                result = str;
            lastStrLen = str.length();
        }
        return result;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String p = in.readLine();
            String q = in.readLine();

            String ans = solve(p, q);

            System.out.println(ans);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new Thread(new NonSharedSubstring()).start();
    }
}
