import java.util.Scanner;

public class FibonacciSumSquares {
    static final boolean FULL_TEST = false;
    static final long FULL_TEST_MAX = 20;
    static final long DECIMAL_MOD = 10;

    static final long DECIMAL_PERIOD = calcFibPeriod(DECIMAL_MOD);

    private static long getFibonacciSumSquaresNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;
        long sum = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
            sum += current * current;
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

    private static long getFibonacciSumSquares(long n) {
        if (n <= 1)
            return n;
        long fN = fastFibonacciLastDig(n);
        long fNP1 = fastFibonacciLastDig(n + 1);
        return (fN * fNP1) % DECIMAL_MOD;
    }

    public static void main(String[] args) {
        if (FULL_TEST) {
            boolean testPassed = true;
            for (long n = 0; n < FULL_TEST_MAX; ++n) {
                long naiveVal = getFibonacciSumSquaresNaive(n);
                long fastVal = getFibonacciSumSquares(n);
                testPassed &= naiveVal == fastVal;
                if (!testPassed) {
                    System.out.println(String.format("Mismatched for n = %d, Naive %d, Fast %d", n, naiveVal, fastVal));
                }
            }

            if (testPassed)
                System.out.println("Ok, test passed");
        } else {
            Scanner scanner = new Scanner(System.in);
            long n = scanner.nextLong();
            long s = getFibonacciSumSquares(n);
            System.out.println(s);
        }
    }
}
