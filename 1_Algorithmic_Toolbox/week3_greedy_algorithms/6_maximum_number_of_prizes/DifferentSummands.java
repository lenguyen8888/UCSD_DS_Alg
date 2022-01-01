import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DifferentSummands {

    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<Integer>();
        // write your code here
        // find x such that
        // 1 + 2 + 3 + 4 + ... + x < n
        // so x is the integer solution to
        // x^2 + x - 2*n = 0
        // x = (-1 + sqrt(1 + 8*n))
        long x = (long) ((-1.0 + Math.sqrt(1.0 + 8.0 * n)) / 2.0);
        for (int i = 1; i < x; i++) {
            n -= i;
            summands.add(i);
        }
        if (n > 0)
            summands.add(n);
        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
        scanner.close();
    }
}
