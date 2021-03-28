import java.util.Scanner;

public class GCD {
    static final boolean FULL_TEST = false;

    private static int gcd_naive(int a, int b) {
        int current_gcd = 1;
        for (int d = 2; d <= a && d <= b; ++d) {
            if (a % d == 0 && b % d == 0) {
                if (d > current_gcd) {
                    current_gcd = d;
                }
            }
        }

        return current_gcd;
    }

    private static int gcd(int a, int b) {
        if (a > b) {
            return gcd(b, a);
        }
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static void main(String args[]) {
        if (FULL_TEST) {
            int MAX_TEST_VAL = 20;
            for (int i = 1; i <= MAX_TEST_VAL; ++i) {
                for (int j = 1; j < MAX_TEST_VAL; j++)
                    System.out.println(
                            String.format("(%d,%d) gcd == %d, gcd_naive == %d", i, j, gcd(i, j), gcd_naive(i, j)));
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            System.out.println(gcd(a, b));
        }
    }
}
