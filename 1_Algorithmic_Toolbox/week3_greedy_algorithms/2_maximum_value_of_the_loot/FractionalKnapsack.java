import java.util.Scanner;

public class FractionalKnapsack {

    static final boolean USE_CLASS_SORT = false;
    // Using sort would result in this error:
    // Failed case #4/13: Wrong answer
    // wrong output format: list index out of range
    // (Time used: 0.07/1.50, memory used: 25632768/671088640.)

    // Simple search implementation (not using sort)
    private static int findHighestPIndex(double[] prices) {
        double maxP = Double.MIN_VALUE;
        int maxI = 0;
        for (int i = 0; i < prices.length; ++i)
            if (maxP < prices[i]) {
                maxI = i;
                maxP = prices[i];
            }
        return maxI;
    }

    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        // write your code here

        int numItems = values.length;
        double[] unitP = new double[numItems];
        for (int i = 0; i < numItems; ++i)
            unitP[i] = (double) values[i] / weights[i];
        for (int i = 0; i < numItems && capacity > 0; ++i) {
            int maxI = findHighestPIndex(unitP);
            double maxP = unitP[maxI];
            unitP[maxI] = 0.0; // Taken the unit out of consideration
            int w = Math.min(capacity, weights[maxI]);
            capacity -= w;
            value += (w * maxP);
        }
        return value;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }

        double val = getOptimalValue(capacity, values, weights);
        System.out.println(String.format("%.4f", val));

        scanner.close();
    }
}
