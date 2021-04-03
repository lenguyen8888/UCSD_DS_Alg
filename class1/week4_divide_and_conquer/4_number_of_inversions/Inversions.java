import java.util.Scanner;

public class Inversions {

    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, b, left, ave);
        numberOfInversions += getNumberOfInversions(a, b, ave, right);
        // write your code here
        // Selection step
        int i = left, j = ave;
        for (int k = left; k < right; k++) {
            if (j >= right || i < ave && a[i] <= a[j]) {
                b[k] = a[i++];
            } else {
                if (i < ave)
                    numberOfInversions += (j - k);
                b[k] = a[j++];
            }
        }
        // copy back
        for (int k = left; k < right; k++)
            a[k] = b[k];
        return numberOfInversions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        System.out.println(getNumberOfInversions(a, b, 0, a.length));
    }
}
