import java.util.Scanner;

public class Fibonacci {
    static final boolean FULL_TEST = false;
    static final int FULL_TEST_MAX_N = 10;

    private static long calc_fib(int n) {
        if (n <= 1)
            return n;
        return calc_fib(n - 1) + calc_fib(n - 2);
    }

    private static long fast_fib(int n) {
        if (n <= 1)
            return n;
        long f_minus_1 = 0, f_val = 1;
        for (int i = 1; i < n; i++) {
            long temp = f_val;
            f_val = f_val + f_minus_1;
            f_minus_1 = temp;
        }
        return f_val;

    }

    public static void main(String args[]) {
        if (FULL_TEST) {
            boolean testPassed = true;
            for (int i = 0; i <= FULL_TEST_MAX_N; i++) {
                long calcVal = calc_fib(i);
                long fastVal = fast_fib(i);
                boolean valEqual;
                valEqual = calcVal == fastVal;
                testPassed &= valEqual;
                if (!valEqual)
                    System.out.println(String.format("calc_fib(%d) == %d, fast_fib(%d) == %d", i, calcVal, i, fastVal));
            }
            if (testPassed)
                System.out.println("Test passed");
            else
                System.out.println("Test failed");

        } else {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            System.out.println(fast_fib(n));

        }
    }
}
