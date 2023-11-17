import java.util.Scanner;

public class FibonacciLastDigit {
    static final boolean FULL_TEST = false;
    static final boolean SKIP_SLOW = true;
    static final int FULL_TEST_MAX_N = 10;

    private static int getFibonacciLastDigitNaive(int n) {
        if (n <= 1)
            return n;

        int previous = 0;
        int current = 1;

        for (int i = 0; i < n - 1; ++i) {
            int tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
        }

        return current % 10;
    }

    private static int fastFibonacciLastDigit(int n) {
        if (n <= 1)
            return n;
        int f_minus_1 = 0, f_val = 1;
        for (int i = 1; i < n; i++) {
            int temp = f_val;
            f_val = (f_val + f_minus_1) % 10;
            f_minus_1 = temp;
        }
        return f_val;

    }

    public static void main(String[] args) {
        quick_test();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int c = fastFibonacciLastDigit(n);
        System.out.println(c);
        scanner.close();
    }

    /**
     * 
     */
    private static void quick_test() {
        if (FULL_TEST) {
            int testLimit = SKIP_SLOW ? FULL_TEST_MAX_N * 10 : FULL_TEST_MAX_N;
            boolean testPassed = true;
            for (int i = 0; i <= testLimit; i++) {
                int fastVal = fastFibonacciLastDigit(i);
                if (SKIP_SLOW) {
                    System.out.println(String.format("FibLastD(%d) == %d", i, fastVal));

                } else {
                    int naiveVal = getFibonacciLastDigitNaive(i);
                    boolean equalVal = fastVal == naiveVal;
                    testPassed &= equalVal;
                    if (!equalVal)
                        System.out.println(String.format("Mismatched calc_fib(%d) == %d, slow_fib(%d) == %d", i,
                                fastVal, i, naiveVal));
                }
            }
            if (testPassed)
                System.out.println("Test passed");
            else
                System.out.println("Test failed");
        }
    }
}
