import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Knapsack {
    static int optimalWeight(int W, int[] w) {
        // write you code here
        int result = 0;
        List<Integer> goodW = new ArrayList<Integer>();
        for (int wVal : w) {
            if (wVal <= W)
                goodW.add(wVal);
        }
        int item[] = new int[goodW.size() + 1];
        item[0] = 0;
        int itemIndex = 1;
        for (int val : goodW) {
            item[itemIndex++] = val;
        }
        int weights[][] = new int[W + 1][item.length];
        for (int i = 0; i < (W + 1); i++) {
            for (int j = 0; j < item.length; j++) {
                if (i == 0 || j == 0) {
                    weights[i][j] = 0;
                } else {
                    int p = weights[i][j - 1];
                    int c = item[j];
                    if (i >= item[j])
                        c += weights[i - item[j]][j - 1];
                    weights[i][j] = c > i ? p : Math.max(p, c);
                }
            }
        }
        return weights[W][item.length - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}
