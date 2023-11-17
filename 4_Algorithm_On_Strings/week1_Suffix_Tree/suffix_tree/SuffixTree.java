import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SuffixTree {
    private static final boolean DEBUG = false;

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

    private Node root;

    private class Node {
        public static final int NUM_LETTERS = 5;
        public static final int NA = -1;
        String lab;
        public Node next[];

        Node(String label) {
            this.lab = label;
            next = new Node[NUM_LETTERS];
        }

        public boolean hasChar(char c) {
            return next[letterToIndex(c)] != null;
        }

        public void add(char c, String label) {
            this.next[letterToIndex(c)] = new Node(label);
        }

        public void add(char c, Node node) {
            this.next[letterToIndex(c)] = node;
        }

        public Node get(char c) {
            return next[letterToIndex(c)];
        }
    }

    static int letterToIndex(char letter) {
        switch (letter) {
        case 'A':
            return 0;
        case 'C':
            return 1;
        case 'G':
            return 2;
        case 'T':
            return 3;
        case '$':
            return 4;
        default:
            assert (false);
            return Node.NA;
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
                    if (DEBUG) {
                        char ck = text.charAt(k);
                        System.out.print(">>> j " + j + " K " + k);
                        System.out.print(" label " + child.lab);
                        System.out.println(" s[k:] " + text.substring(k));
                    }
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

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding
    // substrings of the text) in any order.
//    public List<String> computeSuffixTreeEdges(String text) {
//        List<String> result = new ArrayList<String>();
//        // Implement this function yourself
//        return result;
//    }
    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<String>();
        buildSuffixTree(text);
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.pop();
            if (n != root) {
                result.add(n.lab);
            }
            for (Node node : n.next) {
                if (node != null) {
                    queue.add(node);
                }
            }
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
