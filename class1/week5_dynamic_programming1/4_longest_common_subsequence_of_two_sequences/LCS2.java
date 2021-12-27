import java.util.Scanner;

public class LCS2 {

    private static int lcs2(int[] a, int[] b) {
        // Write your code here
        int dist[][] = new int[a.length + 1][b.length + 1];
        for (int i = 0; i < (a.length + 1); ++i) {
            for (int j = 0; j < (b.length + 1); ++j) {
                if (i == 0 || j == 0) {
                    dist[i][j] = 0;
                } else if (a[i - 1] == b[j - 1]) {
                    dist[i][j] = dist[i - 1][j - 1] + 1;
                } else {
                    dist[i][j] = Math.max(dist[i - 1][j], dist[i][j - 1]);
                }
            }
        }
        return dist[a.length][b.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = scanner.nextInt();
        }

        System.out.println(lcs2(a, b));
    }
}
