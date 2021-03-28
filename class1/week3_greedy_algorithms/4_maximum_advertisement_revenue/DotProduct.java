import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class DotProduct {
    private static long maxDotProduct(int[] a, int[] b) {
        // write your code here
        long result = 0;
        Integer[] aTemp, bTemp;
        aTemp = new Integer[a.length];
        for (int i = 0; i < aTemp.length; ++i)
            aTemp[i] = a[i];

        bTemp = new Integer[b.length];
        for (int i = 0; i < bTemp.length; ++i)
            bTemp[i] = b[i];

        Arrays.sort(aTemp, Collections.reverseOrder());
        Arrays.sort(bTemp, Collections.reverseOrder());
        for (int i = 0; i < aTemp.length; i++) {
            result += ((long) aTemp[i] * bTemp[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));
        scanner.close();
    }
}
