import java.util.Scanner;

public class ChangeDP {
    private static final int COINS[] = { 1, 3, 4 };

    private static int getChange(int m) {
        // write your code here
        int minCoinCounts[] = new int[m + 1];
        minCoinCounts[0] = 0;
        for (int i = 1; i < (m + 1); ++i) {
            minCoinCounts[i] = Integer.MAX_VALUE;
            for (int coin : COINS) {
                if (i >= coin) {
                    int coinCount = minCoinCounts[i - coin] + 1;
                    minCoinCounts[i] = Math.min(coinCount, minCoinCounts[i]);
                }
            }
        }
        return minCoinCounts[m];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
    }
}
