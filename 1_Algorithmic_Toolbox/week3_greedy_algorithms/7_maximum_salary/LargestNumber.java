import java.util.Scanner;

public class LargestNumber {

    private static final boolean FULL_TEST = false;
    private static final int MAX_TEST_INT = 1000;

    private static boolean strGE(String a, String b) {
        String strAB = a + b;
        String strBA = b + a;
        int combinedLen = a.length() + b.length();
        for (int i = 0; i < combinedLen; i++) {
            char aFirst = strAB.charAt(i);
            char bFirst = strBA.charAt(i);
            if (aFirst > bFirst)
                return true;
            else if (aFirst < bFirst)
                return false;
        }
        return true;
    }

    private static boolean strJavaGE(String a, String b) {
        String strAB = a + b;
        String strBA = b + a;
        // Idea from Forum is to parseInt, but a simple String compare would work too
        return strAB.compareTo(strBA) >= 0;
//        return Integer.parseInt(strAB) >= Integer.parseInt(strBA);
    }

    private static String largestNumber(String[] a) {
        // write your code here
        String result = "";
        int numStr = a.length;
        int aLength = a.length;
        while (numStr > 0) {
            String maxStr = "";
            int iMax = 0;
            for (int i = 0; i < aLength; i++) {
                if (a[i].length() > 0) {
                    if (maxStr.length() == 0 || strJavaGE(a[i], maxStr)) {
                        maxStr = a[i];
                        iMax = i;
                    }
                }
            }
            if (maxStr.length() > 0)
                result += maxStr;
            a[iMax] = ""; // Remove maxStr
            numStr--;
        }
        return result;
    }

    public static void main(String[] args) {
        if (FULL_TEST) {
            boolean testPassed = true;
            for (int i = 0; i < MAX_TEST_INT; i++) {
                String strI = "" + i;
                for (int j = 0; j < MAX_TEST_INT; j++) {
                    String strJ = "" + j;
                    if (strGE(strI, strJ) != strJavaGE(strI, strJ)) {
                        System.out
                                .println(String.format("mismatch (i, j) == (%d, %d) %b", i, j, strJavaGE(strI, strJ)));
                        testPassed = false;
                    }
                }
            }
            if (testPassed)
                System.out.println("test passed");
        } else {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            String[] a = new String[n];
            for (int i = 0; i < n; i++) {
                a[i] = scanner.next();
            }
            System.out.println(largestNumber(a));
        }
    }
}
