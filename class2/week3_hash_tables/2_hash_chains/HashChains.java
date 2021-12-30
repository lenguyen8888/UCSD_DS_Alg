import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    private Vector<ArrayList<String>> table;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int) hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

//    private void processQuery(Query query) {
//        switch (query.type) {
//            case "add":
//                if (!elems.contains(query.s))
//                    elems.add(0, query.s);
//                break;
//            case "del":
//                if (elems.contains(query.s))
//                    elems.remove(query.s);
//                break;
//            case "find":
//                writeSearchResult(elems.contains(query.s));
//                break;
//            case "check":
//                for (String cur : elems)
//                    if (hashFunc(cur) == query.ind)
//                        out.print(cur + " ");
//                out.println();
//                // Uncomment the following if you want to play with the program interactively.
//                // out.flush();
//                break;
//            default:
//                throw new RuntimeException("Unknown query: " + query.type);
//        }
//    }

    private void processQuery(Query query) {
        List<String> mList;
        switch (query.type) {
        case "add":
            mList = table.get(hashFunc(query.s));
            if (!mList.contains(query.s))
                mList.add(0, query.s);
            break;
        case "del":
            mList = table.get(hashFunc(query.s));
            if (mList.contains(query.s))
                mList.remove(query.s);
            break;
        case "find":
            mList = table.get(hashFunc(query.s));
            writeSearchResult(mList.contains(query.s));
            break;
        case "check":
            mList = table.get(query.ind);
            for (String cur : mList)
                if (hashFunc(cur) == query.ind)
                    out.print(cur + " ");
            out.println();
            // Uncomment the following if you want to play with the program interactively.
            // out.flush();
            break;
        default:
            throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        initTable();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    /**
     * 
     */
    private void initTable() {
        table = new Vector<>();
        for (int i = 0; i < bucketCount; ++i) {
            table.add(new ArrayList<>());
        }
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
