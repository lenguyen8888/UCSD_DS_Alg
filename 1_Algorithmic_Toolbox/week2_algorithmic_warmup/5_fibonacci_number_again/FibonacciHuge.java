import java.util.Scanner;

public class FibonacciHuge {
    static final boolean FULL_TEST = false;
    static final long TEST_RANGE = 100;
    static final long TEST_MOD = 9;

    private static long getFibonacciHugeNaive(long n, long m) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % m;
        }

        return current % m;
    }

    private static long fastFibonacciMod(long n, long m) {
        if (n <= 1)
            return n;
        long f_minus_1 = 0, f_val = 1;
        for (int i = 1; i < n; i++) {
            long temp = f_val;
            f_val = (f_val + f_minus_1) % m;
            f_minus_1 = temp;
        }
        return f_val;
    }

    private static long calcFibonacciPeriod(long m) {
        if (m < 2 || 1000 < m) {
            throw new IllegalArgumentException("m out of range [2,100] " + m);
        }
        long f_minus_1 = 0, f_val = 1;
        long res = 3;
        for (long i = 1; i <= m * m; i++) {
            long temp = f_val;
            f_val = (f_val + f_minus_1) % m;
            f_minus_1 = temp;
            if (f_val == 1 && f_minus_1 == 0) {
                res = i;
                break;
            }
        }
        return res;
    }

    private static long getFibonacciHuge(long n, long m) {
        long pisanoPer = calcFibonacciPeriod(m);
        long nVal = n % pisanoPer;
        return fastFibonacciMod(nVal, m);
    }

    public static void main(String[] args) {
        quick_test();
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHuge(n, m));
    }

    /**
     * 
     */
    private static void quick_test() {
        if (FULL_TEST) {

            boolean testPassed = true;
            for (long n = 0; n < TEST_RANGE; ++n) {
                long naiveVal = getFibonacciHugeNaive(n, TEST_MOD);
                long fastVal = getFibonacciHuge(n, TEST_MOD);
                testPassed &= naiveVal == fastVal;
                if (!testPassed) {
                    System.out.println(String.format("Mismatched for n = %d, m = %d, Naive %d, Fast %d", n, TEST_MOD,
                            naiveVal, fastVal));
                }
            }

            if (testPassed)
                System.out.println("Ok, test passed");
        }
    }
}
