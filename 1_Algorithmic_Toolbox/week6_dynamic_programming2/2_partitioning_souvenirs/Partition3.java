import java.util.Scanner;

public class Partition3 {
    private static int partition3(int[] A) {
        // write your code here
        int sumA = 0;
        for (int i = 0; i < A.length; i++)
            sumA += A[i];
        int W = sumA % 3 != 0 ? 0 : sumA / 3;
        if (A.length < 3 || W == 0)
            return 0;
        int count = 0;
        int weights[][] = new int[W + 1][A.length + 1];
        for (int i = 0; i <= W; i++) {
            for (int j = 0; j <= A.length; j++) {
                if (i == 0 || j == 0)
                    weights[i][j] = 0;
                else {
                    weights[i][j] = weights[i][j - 1];
                    if (i >= A[j - 1]) {
                        int temp = weights[i - A[j - 1]][j - 1] + A[j - 1];
                        if (temp > weights[i][j])
                            weights[i][j] = temp;
                        if (weights[i][j] == W)
                            count++;
                    }
                }
            }
        }
        if (count < 3)
            return 0;
        else
            return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        System.out.println(partition3(A));
    }
}
