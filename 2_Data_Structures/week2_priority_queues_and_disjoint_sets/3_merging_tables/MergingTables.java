import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    // Ported from C++ templates
    class DisjointSets {
        class DisjointSetsElement {
            int size, parent, rank;

            public DisjointSetsElement() {
                size = 0;
                parent = -1;
                rank = 0;
            }
        }

        int size;
        int max_table_size;
        DisjointSetsElement[] sets;

        DisjointSets(int size) {
            this.size = size;
            max_table_size = 0;
            sets = new DisjointSetsElement[size];
            for (int i = 0; i < sets.length; ++i) {
                sets[i] = new DisjointSetsElement();
                sets[i].parent = i;
            }
        }

        int getParent(int table) {
            if (table != sets[table].parent)
                sets[table].parent = getParent(sets[table].parent);
            return sets[table].parent;
        }

        void merge(int destination, int source) {
            int pDest = getParent(destination);
            int pSource = getParent(source);
            if (pDest != pSource) {
                DisjointSetsElement src = sets[pSource];
                DisjointSetsElement dst = sets[pDest];
                if (src.rank > dst.rank) {
                    dst.parent = pSource;
                    src.size += dst.size;
                    dst.size = 0;
                    max_table_size = Math.max(max_table_size, src.size);
                } else {
                    src.parent = pDest;
                    dst.size += src.size;
                    src.size = 0;
                    max_table_size = Math.max(max_table_size, dst.size);
                    if (dst.rank == src.rank)
                        dst.rank++;

                }
            }
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        DisjointSets tables = new DisjointSets(n);
        for (int i = 0; i < n; i++) {
            int numberOfRows = reader.nextInt();
            tables.sets[i].size = numberOfRows;
            tables.max_table_size = Math.max(tables.max_table_size, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            tables.merge(destination, source);
            writer.printf("%d\n", tables.max_table_size);
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
