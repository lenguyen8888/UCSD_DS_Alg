import java.util.Arrays;
import java.util.Scanner;

public class FractionalKnapsack {

    static final long ROUND_SCALE = 10000;

    static private class itemVal implements Comparable<itemVal> {
        private int weight;
        private final double unitPrice;

        itemVal(int value, int weight) {
            if (weight <= 0)
                throw new IllegalArgumentException("expected positive weight got " + weight);
            if (value <= 0)
                throw new IllegalArgumentException("expected positive value got " + value);
            this.weight = weight;
            unitPrice = (double) value / weight;
        }

        @Override
        public int compareTo(itemVal o) {
            // TODO Auto-generated method stub
            return Double.compare(o.unitPrice, this.unitPrice);
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public int getValue() {
            return (int) Math.round(unitPrice * weight);
        }

    }

    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        // write your code here

        itemVal[] items = new itemVal[values.length];
        for (int i = 0; i < values.length; ++i)
            items[i] = new itemVal(values[i], weights[i]);
        Arrays.sort(items);
        for (int i = 0; i < items.length; ++i) {
            itemVal item = items[i];
            if (capacity > 0) {
                int itemW = Math.min(capacity, item.getWeight());
//                item.setWeight(item.getWeight() - itemW);
                capacity -= itemW;
                value += (itemW * item.getUnitPrice());
            }
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
        // System.out.println(getOptimalValue(capacity, values, weights));
        double val = getOptimalValue(capacity, values, weights);
        System.out.println(String.format("%.4f", val));

        scanner.close();
    }
}
