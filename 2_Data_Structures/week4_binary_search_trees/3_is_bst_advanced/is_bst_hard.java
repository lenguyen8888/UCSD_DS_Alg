import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class is_bst_hard {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        // minVal and maxVal are long to avoid the actual key is
        // Integer.MIN_VALUE or
        // Integer.MAX_VALUE
        private boolean checkTree(int root, long minVal, long maxVal) {
            boolean retVal;
            retVal = true;
            if (nodes == 0)
                return true;
            if (tree[root].key < minVal || tree[root].key >= maxVal)
                return false;
            if (tree[root].left != -1)
                retVal &= checkTree(tree[root].left, minVal, tree[root].key);
            if (tree[root].right != -1)
                retVal &= checkTree(tree[root].right, tree[root].key, maxVal);
            return retVal;
        }

//        boolean isBinarySearchTree() {
//            // Implement correct algorithm here
//            return true;
//        }
        boolean isBinarySearchTree() {
            // Implement correct algorithm here
            return checkTree(0, Long.MIN_VALUE, Long.MAX_VALUE);
        }

    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
