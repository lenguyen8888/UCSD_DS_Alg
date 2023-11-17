import java.util.Scanner;

public class FibonacciPartialSum {
    static final boolean FULL_TEST = false;
    static final long FULL_TEST_MAX = 20;
    static final long DECIMAL_MOD = 10;

    static final long DECIMAL_PERIOD = calcFibPeriod(DECIMAL_MOD);

    private static long getFibonacciPartialSumNaive(long from, long to) {
        long sum = 0;

        long current = 0;
        long next = 1;

        for (long i = 0; i <= to; ++i) {
            if (i >= from) {
                sum += current;
            }

            long new_current = next;
            next = next + current;
            current = new_current;
        }

        return sum % 10;
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

    private static long calcFibPeriod(long m) {
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

    private static long fastFibonacciLastDig(long n) {
        long nVal = n % DECIMAL_PERIOD;
        return fastFibonacciMod(nVal, DECIMAL_MOD);
    }

    private static long getFibonacciSum(long n) {
        long result = fastFibonacciLastDig(n + 2);
        // -1 in Modulo arithmetic is the same as
        // adding MOD - 1
        result += (DECIMAL_MOD - 1);
        result %= DECIMAL_MOD;
        return result;
    }

    private static long getFibonacciPartialSum(long from, long to) {
        long sumFrom = from > 0 ? getFibonacciSum(from - 1) : 0;
        long sumTo = getFibonacciSum(to);
        long result = (sumTo + (DECIMAL_MOD - sumFrom)) % DECIMAL_MOD;
        return result;
    }

    public static void main(String[] args) {
        quick_test();
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSum(from, to));
    }

    /**
     * 
     */
    private static void quick_test() {
        if (FULL_TEST) {
            boolean testPassed = true;
            for (long to = 1; to < FULL_TEST_MAX; ++to) {
                for (long from = 0; from <= to; ++from) {
                    long naiveVal = getFibonacciPartialSumNaive(from, to);
                    long fastVal = getFibonacciPartialSumNaive(from, to);
                    boolean equalVal = naiveVal == fastVal;
                    testPassed &= equalVal;
                    if (!equalVal) {
                        System.out.println(String.format("Mismatched for (from, to) (%d, %d), Naive %d, Fast %d", from,
                                to, naiveVal, fastVal));
                    }
                }
            }

            if (testPassed)
                System.out.println("Ok, test passed");
        }
    }
}
