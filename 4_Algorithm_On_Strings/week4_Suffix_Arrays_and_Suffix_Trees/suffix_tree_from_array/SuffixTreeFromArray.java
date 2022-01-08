import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public class SuffixTreeFromArray {
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

    // Data structure to store edges of a suffix tree.
    public class Edge {
        // The ending node of this edge.
        int node;
        // Starting position of the substring of the text
        // corresponding to the label of this edge.
        int start;
        // Position right after the end of the substring of the text
        // corresponding to the label of this edge.
        int end;

        Edge(int node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }
    }

    private final static char[] ALPHABET = "$ACGT".toCharArray();

    private static char[] reverse(char[] a) {
        String aStr = new String(a);
        String revStr = new StringBuilder(aStr).reverse().toString();
        return revStr.toCharArray();
    }

    private final static char[] REV_ALPHABET = reverse(ALPHABET);

    private class SuffixTree {
        private final String text; // Make sure we only keep 1 copy to avoid stack overlow!
        private final int[] order;
        private final int[] lcp;

        private class Node {
            Node parent;
            Map<Character, Node> children;
            int depth;
            int start;
            int end;

            Node(Node parent, int depth, int start, int end) {
                this.parent = parent;
                this.depth = depth;
                this.start = start;
                this.end = end;
                this.children = new HashMap<>();
            }

        }

        SuffixTree(String text, int[] order, int[] lcp) {
            this.text = text;
            this.order = order;
            this.lcp = lcp;
        }

        public Node stFromSA() {
            Node root = new Node(null, 0, -1, -1);
            int lcpPrev = 0;
            Node curNode = root;
            int sLen = text.length();
            for (int i = 0; i < sLen; ++i) {
                int suffix = order[i];
                while (curNode.depth > lcpPrev) {
                    curNode = curNode.parent;
                }
                if (curNode.depth == lcpPrev) {
                    curNode = createNewLeaf(curNode, suffix);
                } else {
                    int edgeStart = order[i - 1] + curNode.depth;
                    int offset = lcpPrev - curNode.depth;
                    Node midNode = breakEdge(curNode, edgeStart, offset);
                    curNode = createNewLeaf(midNode, suffix);
                }
                if (i < sLen - 1) {
                    lcpPrev = lcp[i];
                }
            }
            return root;
        }

        public Node createNewLeaf(Node node, int suffix) {
            int sLen = text.length();
            Node leaf = new Node(node, sLen - suffix, suffix + node.depth, sLen);
            char leafChar = text.charAt(leaf.start);
            node.children.put(leafChar, leaf);
            return leaf;
        }

        public Node breakEdge(Node node, int start, int offset) {
            char startChar = text.charAt(start);
            char midChar = text.charAt(start + offset);
            Node midNode = new Node(node, node.depth + offset, start, start + offset);

            // save startChar Node and about to replace it
            Node startCharNode = node.children.get(startChar);
            midNode.children.put(midChar, startCharNode);

            // move startChar node to be a child for midNode
            startCharNode.parent = midNode;
            startCharNode.start += offset;

            // replace startChar Node with midNode in the original tree
            node.children.put(startChar, midNode);
            return midNode;
        }

        private static final boolean DEBUG = true;

    }

//    private void dfsEdge(SuffixTree.Node node) {
//        if (node.parent != null) {
//            System.out.println(node.start + " " + node.end);
//        }
//        for (char c : ALPHABET) {
//            if (node.children.containsKey(c)) {
//                SuffixTree.Node child = node.children.get(c);
//                dfsEdge(child);
//            }
//        }
//    }

    // Build suffix tree of the string text given its suffix array suffix_array
    // and LCP array lcp_array. Return the tree as a mapping from a node ID
    // to the list of all outgoing edges of the corresponding node. The edges in the
    // list must be sorted in the ascending order by the first character of the edge
    // label.
    // Root must have node ID = 0, and all other node IDs must be different
    // nonnegative integers.
    //
    // For example, if text = "ACACAA$", an edge with label "$" from root to a node
    // with ID 1
    // must be represented by new Edge(1, 6, 7). This edge must be present in the
    // list tree.get(0)
    // (corresponding to the root node), and it should be the first edge in the list
    // (because it has the smallest first character of all edges outgoing from the
    // root).
    Map<Integer, List<Edge>> SuffixTreeFromSuffixArray(int[] suffixArray, int[] lcpArray, final String text) {

        Map<Integer, List<Edge>> tree = new HashMap<Integer, List<Edge>>();

        SuffixTree sTree = new SuffixTree(text, suffixArray, lcpArray);
        SuffixTree.Node root = sTree.stFromSA();
        int nodeCount = -1;

        ArrayDeque<SuffixTree.Node> queue = new ArrayDeque<>();
        ArrayDeque<SuffixTree.Node> debugQ = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            SuffixTree.Node node = queue.pop();
            if (SuffixTree.DEBUG)
                System.out.println(">> " + node.start + "," + node.end);
            tree.put(++nodeCount, new ArrayList<>());
            int curNodeID = nodeCount;
            List<Edge> curList = tree.get(curNodeID);
            if (node.parent != null) {
                curList.add(new Edge(++nodeCount, node.start, node.end));
            }
            for (char c : ALPHABET) {
                if (node.children.containsKey(c)) {
                    SuffixTree.Node child = node.children.get(c);
                    curList.add(new Edge(++nodeCount, child.start, child.end));
                    if (child.children.size() > 0)
                        queue.add(child);
                }
            }
        }
        return tree;
    }

    private void dfsEdge(SuffixTree.Node node) {
        if (node.parent != null) {
            System.out.println(node.start + " " + node.end);
        }
        for (char c : ALPHABET) {
            if (node.children.containsKey(c)) {
                SuffixTree.Node child = node.children.get(c);
                dfsEdge(child);
            }
        }
    }

    private void iterative_dfsEdge(SuffixTree.Node node) {
        Stack<SuffixTree.Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            SuffixTree.Node cur = stack.pop();
            if (cur.parent != null) {
                System.out.println(cur.start + " " + cur.end);
            }

            // use heap/data stack would be a great start to avoid
            // system/code stack overflow
            //
            // to preserve calling order we need to iterate the
            // children list in reverse
            for (char c : REV_ALPHABET) {
                if (cur.children.containsKey(c)) {
                    SuffixTree.Node child = cur.children.get(c);
                    stack.push(child);
                }
            }
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixTreeFromArray().run();
    }

    public void print(ArrayList<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    final static private boolean USE_SIMPLE_PRINT = true;

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffixArray = new int[text.length()];
        for (int i = 0; i < suffixArray.length; ++i) {
            suffixArray[i] = scanner.nextInt();
        }
        int[] lcpArray = new int[text.length() - 1];
        for (int i = 0; i + 1 < text.length(); ++i) {
            lcpArray[i] = scanner.nextInt();
        }
        System.out.println(text);
        if (USE_SIMPLE_PRINT) {
            simplePrint(text, suffixArray, lcpArray);
        } else {
            templatePrint(text, suffixArray, lcpArray);
        }
    }

    /**
     * @param text
     * @param suffixArray
     * @param lcpArray
     */
    private void simplePrint(String text, int[] suffixArray, int[] lcpArray) {
        SuffixTree tree = new SuffixTree(text, suffixArray, lcpArray);
        SuffixTree.Node root = tree.stFromSA();
        iterative_dfsEdge(root);
    }

    /**
     * @param text
     * @param suffixArray
     * @param lcpArray
     */
    private void templatePrint(String text, int[] suffixArray, int[] lcpArray) {
        // Build the suffix tree and get a mapping from
        // suffix tree node ID to the list of outgoing Edges.
        Map<Integer, List<Edge>> suffixTree = SuffixTreeFromSuffixArray(suffixArray, lcpArray, text);
        ArrayList<String> result = new ArrayList<String>();
        // Output the edges of the suffix tree in the required order.
        // Note that we use here the contract that the root of the tree
        // will have node ID = 0 and that each vector of outgoing edges
        // will be sorted by the first character of the corresponding edge label.
        //
        // The following code avoids recursion to avoid stack overflow issues.
        // It uses two stacks to convert recursive function to a while loop.
        // This code is an equivalent of
        //
        // OutputEdges(tree, 0);
        //
        // for the following _recursive_ function OutputEdges:
        //
        // public void OutputEdges(Map<Integer, List<Edge>> tree, int nodeId) {
        // List<Edge> edges = tree.get(nodeId);
        // for (Edge edge : edges) {
        // System.out.println(edge.start + " " + edge.end);
        // OutputEdges(tree, edge.node);
        // }
        // }
        //
        int[] nodeStack = new int[text.length()];
        int[] edgeIndexStack = new int[text.length()];
        nodeStack[0] = 0;
        edgeIndexStack[0] = 0;
        int stackSize = 1;
        while (stackSize > 0) {
            int node = nodeStack[stackSize - 1];
            int edgeIndex = edgeIndexStack[stackSize - 1];
            stackSize -= 1;
            if (suffixTree.get(node) == null) {
                continue;
            }
            if (edgeIndex + 1 < suffixTree.get(node).size()) {
                nodeStack[stackSize] = node;
                edgeIndexStack[stackSize] = edgeIndex + 1;
                stackSize += 1;
            }
            result.add(suffixTree.get(node).get(edgeIndex).start + " " + suffixTree.get(node).get(edgeIndex).end);
            nodeStack[stackSize] = suffixTree.get(node).get(edgeIndex).node;
            edgeIndexStack[stackSize] = 0;
            stackSize += 1;
        }
        print(result);
    }
}
