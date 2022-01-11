import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class RopeProblem {
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

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        int size;
        char ch;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, int size, char ch, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.size = size;
            this.ch = ch;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private int getSize(Vertex v) {
        if (v == null)
            return 0;
        return v.size;
    }

    void update(Vertex v) {
        if (v == null)
            return;
        v.size = getSize(v.left) + getSize(v.right) + 1;
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null)
            return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {
        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    Vertex find(Vertex node, int order_stat) {
        Vertex v = node;
        if (v == null)
            return null;
        int s;
        if (v.left == null)
            s = 0;
        else
            s = getSize(v.left);
        if (order_stat == s + 1)
            return v;
        else if (order_stat < s + 1)
            return splay(find(v.left, order_stat));
        else
            return splay(find(v.right, order_stat - s - 1));

    }

    VertexPair split(Vertex node) {

        if (node == null)
            return new VertexPair(node, null);

        // otherwise, splay the next bigger node and set right new tree node to that
        // node
        Vertex right = node;
        // left is a temp var for the child to the left of the splayed node
        Vertex left = right.left;
        right.left = null;

        // and ensure that left is now root node for its own tree too
        if (left != null)
            left.parent = null;
        update(left);
        update(right);
        return new VertexPair(left, right);
    }

    Vertex merge(Vertex left, Vertex right) {
        if (left == null)
            return right;
        if (right == null)
            return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }

    // Code that uses splay tree to solve the problem

//    void insert(int x) {
//        Vertex left = null;
//        Vertex right = null;
//        Vertex new_vertex = null;
//        VertexPair leftRight = split(root, x);
//        left = leftRight.left;
//        right = leftRight.right;
//        if (right == null || right.key != x) {
//            new_vertex = new Vertex(x, x, null, null, null);
//        }
//        root = merge(merge(left, new_vertex), right);
//    }

//    void erase(int x) {
//        // Implement erase yourself
//    }
//
//    boolean find(int x) {
//        // Implement find yourself
//        return false;
//    }
//
//    long sum(int from, int to) {
//        VertexPair leftMiddle = split(root, from);
//        Vertex left = leftMiddle.left;
//        Vertex middle = leftMiddle.right;
//        VertexPair middleRight = split(middle, to + 1);
//        middle = middleRight.left;
//        Vertex right = middleRight.right;
//        long ans = 0;
//        // Complete the implementation of sum
//
//        return ans;
//    }

//    void erase(int x) {
//        VertexPair leftMiddle = split(root, x);
//        Vertex left = leftMiddle.left;
//        Vertex middle = leftMiddle.right;
//        VertexPair middleRight = split(middle, x + 1);
//        middle = middleRight.left;
//        Vertex right = middleRight.right;
//
//        root = merge(left, right);
//    }

//    boolean find(int x) {
//        VertexPair pair = find(root, x);
//        Vertex result = pair.left;
//        root = pair.right;
//        if (result == null || result.key != x)
//            return false;
//        else
//            return true;
//    }

    class Rope {
        String s;
        private Vertex root;

//        void process(int i, int j, int k) {
//            // Replace this code with a faster implementation
//            String t = s.substring(0, i) + s.substring(j + 1);
//            s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
//        }
//        void process(int i, int j, int k) {
//            // Replace this code with a faster implementation//            String t = s.substring(0, i) + s.substring(j + 1);
//            s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
//        }
//
//        String result() {
//            return s;
//        }
//
//        Rope(String s) {
//            this.s = s;
//        }

        void process(int i, int j, int k) {
            i++;
            j++;
            k++;

            Vertex result = find(this.root, j + 1);
            VertexPair pair = split(result);
            Vertex left = pair.left;
            Vertex right = pair.right;
            Vertex mid;

            if (i == 1) {
                if (result == null)
                    return;
                Vertex tmp = find(right, k);
                if (tmp != null) {
                    VertexPair pair2 = split(tmp);
                    mid = pair2.left;
                    right = pair2.right;
                    left = merge(mid, left);
                    this.root = merge(left, right);
                } else {
                    this.root = merge(left, right);
                }
            } else {
                if (result != null) {
                    Vertex tmp = find(left, i);
                    VertexPair pair3 = split(tmp);
                    left = pair3.left;
                    mid = pair3.right;
                    Vertex tmp2 = find(left, k);
                    if (tmp2 != null) {
                        VertexPair pair4 = split(tmp2);
                        left = pair4.left;
                        right = pair4.right;
                        left = merge(left, mid);
                        this.root = merge(left, right);
                    } else {
                        this.root = merge(left, mid);
                    }
                } else {
                    Vertex tmp = find(left, i);
                    VertexPair pair5 = split(tmp);
                    left = pair5.left;
                    mid = pair5.right;
                    Vertex tmp2 = find(left, k);
                    if (tmp2 != null) {
                        VertexPair pair6 = split(tmp2);
                        left = pair6.left;
                        right = pair6.right;
                        this.root = merge(left, mid);
                    } else {
                        this.root = merge(left, right);
                    }
                }
            }
        }

        private List<Character> charList;

        private void dfsInOrder(Vertex v) {
            if (v == null)
                return;
            dfsInOrder(v.left);
            charList.add(v.ch);
            dfsInOrder(v.right);
        }

        String result() {
//            return s;
            if (charList == null)
                charList = new ArrayList<>();
            charList.clear();
            dfsInOrder(this.root);
            String s = charList.toArray().toString();
            return s;

        }

        Rope(String s) {
            this.s = s;
            for (int i = 0; i < s.length(); ++i) {
                Vertex v = new Vertex(i, 1, s.charAt(i), null, null, null);
                this.root = merge(this.root, v);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new RopeProblem().run();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i, j, k);
        }
        out.println(rope.result());
        out.close();
    }
}
