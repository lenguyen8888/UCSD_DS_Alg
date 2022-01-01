import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    // Add AssignedJob class definition from Python starter code
    static class AssignedJob {
        int worker;
        long started_at;

        public AssignedJob(int worker, long started_at) {
            this.worker = worker;
            this.started_at = started_at;
        }
    }

    private AssignedJob jobQ[];

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

//    private void assignJobs() {
//        // TODO: replace this code with a faster algorithm.
//        assignedWorker = new int[jobs.length];
//        startTime = new long[jobs.length];
//        long[] nextFreeTime = new long[numWorkers];
//        for (int i = 0; i < jobs.length; i++) {
//            int duration = jobs[i];
//            int bestWorker = 0;
//            for (int j = 0; j < numWorkers; ++j) {
//                if (nextFreeTime[j] < nextFreeTime[bestWorker])
//                    bestWorker = j;
//            }
//            assignedWorker[i] = bestWorker;
//            startTime[i] = nextFreeTime[bestWorker];
//            nextFreeTime[bestWorker] += duration;
//        }
//    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        initJobQ();
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = getBestWorker(duration);
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    /**
     * 
     */
    private void initJobQ() {
        jobQ = new AssignedJob[numWorkers];
        for (int i = 0; i < numWorkers; ++i)
            jobQ[i] = new AssignedJob(i, 0);
    }

    private void siftDown(int i) {
        int minIndex = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < jobQ.length) {
            if (jobQ[minIndex].started_at > jobQ[left].started_at) {
                minIndex = left;
            } else if (jobQ[minIndex].started_at == jobQ[left].started_at) {
                if (jobQ[minIndex].worker > jobQ[left].worker) {
                    minIndex = left;
                }
            }
        }
        if (right < jobQ.length) {
            if (jobQ[minIndex].started_at > jobQ[right].started_at) {
                minIndex = right;
            } else if (jobQ[minIndex].started_at == jobQ[right].started_at) {
                if (jobQ[minIndex].worker > jobQ[right].worker) {
                    minIndex = right;
                }
            }
        }
        if (minIndex != i) {
            AssignedJob temp = jobQ[minIndex];
            jobQ[minIndex] = jobQ[i];
            jobQ[i] = temp;
            siftDown(minIndex);
        }
    }

    private int getBestWorker(long duration) {
        int bestWorker = jobQ[0].worker;
        jobQ[0].started_at += duration;
        siftDown(0);
        return bestWorker;
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
