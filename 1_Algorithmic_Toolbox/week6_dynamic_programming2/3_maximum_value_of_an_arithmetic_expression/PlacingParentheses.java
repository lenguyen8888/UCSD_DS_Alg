import java.util.Scanner;

public class PlacingParentheses {
    private static int digits[];
    private static char operations[];
    private static long mMax[][];
    private static long mMin[][];

    private static void updateMaxMin(int i, int j) {
        long minimum = Long.MAX_VALUE;
        long maximum = Long.MIN_VALUE;
        for (int k = i; k < j; k++) {
            long a = eval(mMax[i][k], mMax[k + 1][j], operations[k]);
            long b = eval(mMin[i][k], mMin[k + 1][j], operations[k]);
            long c = eval(mMax[i][k], mMin[k + 1][j], operations[k]);
            long d = eval(mMin[i][k], mMax[k + 1][j], operations[k]);
            maximum = Math.max(maximum, Math.max(Math.max(a, b), Math.max(c, d)));
            minimum = Math.min(minimum, Math.min(Math.min(a, b), Math.min(c, d)));
        }
        mMax[i][j] = maximum;
        mMin[i][j] = minimum;
    }

    private static long getMaximValue(String exp) {
        digits = new int[exp.length() / 2 + 1];
        operations = new char[exp.length() / 2];
        for (int i = 0; i < exp.length(); i++) {
            if (i % 2 == 0) {
                digits[i / 2] = Integer.parseInt("" + exp.charAt(i));
            } else {
                operations[(i - 1) / 2] = exp.charAt(i);
            }
        }
        int tableSize = digits.length;
        mMax = new long[tableSize][tableSize];
        mMin = new long[tableSize][tableSize];
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                mMax[i][j] = 0;
                mMin[i][j] = 0;
            }
        }

        for (int i = 0; i < tableSize; i++) {
            mMax[i][i] = digits[i];
            mMin[i][i] = digits[i];
        }

        for (int s = 1; s < tableSize; s++) {
            for (int i = 0; i < tableSize - s; i++) {
                int j = i + s;
                updateMaxMin(i, j);
            }
        }

        return mMax[0][tableSize - 1];
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}
