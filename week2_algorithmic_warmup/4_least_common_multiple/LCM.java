import java.util.Scanner;

public class LCM {
    private static long lcm_naive(int a, int b) {
        for (long l = 1; l <= (long) a * b; ++l)
            if (l % a == 0 && l % b == 0)
                return l;

        return (long) a * b;
    }

    private static int gcd(int a, int b) {
        if (a > b) {
            return gcd(b, a);
        }
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    private static long lcm(int a, int b) {
        long lcm_val = (long) a / gcd(a, b) * b;
        return lcm_val;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(lcm(a, b));
    }
}
