import java.util.Scanner;

public class Change {
    private static final int COIN_BASE[] = { 10, 5, 1 };

    private static int getChange(int m) {
        int coinCnt = 0;
        while (m > 0) {
            for (int i = 0; i < COIN_BASE.length; ++i) {
                int coinVal = COIN_BASE[i];
                if (m >= coinVal) {
                    int maxChangeForCoin = (m / coinVal) * coinVal;
                    coinCnt += maxChangeForCoin / coinVal;
                    m -= maxChangeForCoin;
                }
            }
        }
        return coinCnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
        scanner.close();
    }
}
