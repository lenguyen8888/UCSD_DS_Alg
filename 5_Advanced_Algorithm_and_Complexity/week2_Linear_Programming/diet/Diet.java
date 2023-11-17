import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Diet {

    static class CombSubSet {

        CombSubSet(int n, int r) {
        }

        // nCr = n!/((n-r)!*r!)
        static int size(int n, int r) {
            long numer = 1, deno = 1;

            // numer == n!/k! == n*(n-1)...*(k+1)
            for (int i = r + 1; i <= n; ++i)
                numer *= i;

            // deno == (n-2)! == 1 * 2 ... * (n-r)
            for (int i = 1; i <= n - r; ++i)
                deno *= i;

            return (int) (numer / deno);
        }

        private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
            if (index == data.length) {
                int[] combination = data.clone();
                combinations.add(combination);
            } else if (start <= end) {
                data[index] = start;
                helper(combinations, data, start + 1, end, index + 1);
                helper(combinations, data, start + 1, end, index);
            }
        }

        static List<int[]> getSubSet(int n, int r) {
            List<int[]> combinations = new ArrayList<>();
            helper(combinations, new int[r], 0, n - 1, 0);
            return combinations;
        }
    }

    static class GaussianElimination {
        static class Equation {
            Equation(double a[][], double b[]) {
                this.a = a;
                this.b = b;
            }

            double a[][];
            double b[];
        }

        static class Position {
            Position(int column, int row) {
                this.column = column;
                this.row = row;
            }

            int column;
            int row;
        }

        Equation equation;
        boolean[] used_rows;
        boolean[] used_columns;
        Position pivot_element;

        public GaussianElimination(Equation equation) {
            this.equation = new Equation(equation.a, equation.b);
            int size = this.equation.a.length;
            this.used_rows = new boolean[size];
            this.used_columns = new boolean[size];
            this.pivot_element = new Position(0, 0);
        }

        public GaussianElimination(int n) {
            double[][] a = new double[n][n];
            double[] b = new double[n];
            this.equation = new Equation(a, b);
            int size = this.equation.a.length;
            used_rows = new boolean[size];
            used_columns = new boolean[size];
            pivot_element = new Position(0, 0);
        }

        Position SelectPivotElement() {
            // This algorithm selects the first free element.
            // You'll need to improve it to pass the problem.
            int maxLen = equation.a.length;
            while (pivot_element.column < maxLen && used_columns[pivot_element.column])
                ++pivot_element.column;
            while (used_rows[pivot_element.row] || equation.a[pivot_element.row][pivot_element.column] == 0) {
                ++pivot_element.row;
                if (pivot_element.row >= maxLen)
                    return null;
            }
            return pivot_element;
        }

        void SwapLines() {
            int size = equation.a.length;

            for (int column = 0; column < size; ++column) {
                double tmpa = equation.a[pivot_element.column][column];
                equation.a[pivot_element.column][column] = equation.a[pivot_element.row][column];
                equation.a[pivot_element.row][column] = tmpa;
            }

            double tmpb = equation.b[pivot_element.column];
            equation.b[pivot_element.column] = equation.b[pivot_element.row];
            equation.b[pivot_element.row] = tmpb;

            boolean tmpu = used_rows[pivot_element.column];
            used_rows[pivot_element.column] = used_rows[pivot_element.row];
            used_rows[pivot_element.row] = tmpu;

            pivot_element.row = pivot_element.column;
        }

        void ProcessPivotElement() {
            // Write your code here
            double pivotFactor = equation.a[pivot_element.row][pivot_element.column];
            assert (pivotFactor != 0.0);
            // fix pivot row
            if (pivotFactor != 1.0) {
                for (int i = 0; i < equation.a.length; ++i)
                    equation.a[pivot_element.row][i] /= pivotFactor;
                equation.b[pivot_element.row] /= pivotFactor;
            }

            for (int row = 0; row < equation.a.length; ++row) {
                if (row != pivot_element.row) {
                    double rowFactor = equation.a[row][pivot_element.column];
                    for (int column = 0; column < equation.a[row].length; ++column)
                        equation.a[row][column] -= equation.a[pivot_element.row][column] * rowFactor;
                    equation.b[row] -= equation.b[pivot_element.row] * rowFactor;
                }
            }

        }

        void MarkPivotElementUsed() {
            used_rows[pivot_element.row] = true;
            used_columns[pivot_element.column] = true;
        }

        double[] SolveEquation() {
            int size = equation.a.length;

            Arrays.fill(used_columns, false);
            Arrays.fill(used_rows, false);
            pivot_element = new Position(0, 0);
            for (int step = 0; step < size; ++step) {
                pivot_element = SelectPivotElement();
                if (pivot_element == null)
                    return null;
                SwapLines();
                ProcessPivotElement();
                MarkPivotElementUsed();
            }

            return equation.b;
        }

        static void PrintColumn(double column[]) {
            int size = column.length;
            for (int row = 0; row < size; ++row)
                System.out.printf("%.6f ", column[row]);
        }

        static Equation ReadEquation() throws IOException {
            Scanner scanner = new Scanner(System.in);
            int size = scanner.nextInt();

            double a[][] = new double[size][size];
            double b[] = new double[size];
            for (int row = 0; row < size; ++row) {
                for (int column = 0; column < size; ++column)
                    a[row][column] = scanner.nextInt();
                b[row] = scanner.nextInt();
            }
            scanner.close();
            return new Equation(a, b);
        }

        void updateRow(int row, double[] aRow, double bVal) {
            int size = this.equation.a.length;
            boolean valid = 0 <= row && row < size && aRow.length == size;
            assert (valid);
            if (valid) {
                this.equation.a[row] = Arrays.copyOf(aRow, size);
                this.equation.b[row] = bVal;
            }
        }
    }

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    final double INFINITY_VAL = 1E9;

    /**
     * @param n : number of constraints
     * @param m : number of variables
     * @param A : Matrix for all constraints inequalities
     * @param b : Column vector for contraints' constants
     * @param c : objective constant vector (objective == x dot x)
     * @param x
     * @return
     */
    int solveDietProblem(int n, int m, double A[][], double[] b, double[] c, double[] x) {
//        Arrays.fill(x, 1);
        // Write your code here
        double[][] LP_a;
        double[] LP_b;
        // we have n constraints, m variables + 1 last constraints for Inf checking
        LP_a = new double[n + m + 1][m];
        LP_b = new double[n + m + 1];
        builFullEq(n, m, A, b, LP_a, LP_b);
        int numVar = m;
        GaussianElimination gauss = new GaussianElimination(numVar);
        List<double[]> solutionList = solvePolyVertices(LP_a, LP_b, gauss);
        if (solutionList.size() == 0) {
            return -1; // No solution
        } else {
            double[] bestSol = new double[numVar];
            double maxObj = Double.NEGATIVE_INFINITY;
            boolean xIsInf = false;
            for (double[] sol : solutionList) {
                double dotP = 0.0;
                for (int i = 0; i < sol.length; ++i) {
                    dotP += c[i] * sol[i];
                }
                if (maxObj < dotP) {
                    maxObj = dotP;
                    bestSol = sol;
                }
            }
            double solInfCheck = 0.0;
            for (int i = 0; i < bestSol.length; ++i) {
                x[i] = bestSol[i];
                solInfCheck += x[i];
            }
            if (solInfCheck > INFINITY_VAL)
                return 1; // Infinity
        }
        return 0;
    }

    /**
     * @param n
     * @param m
     * @param LP_a
     * @param LP_b
     * @param gauss
     */
    List<double[]> solvePolyVertices(double[][] LP_a, double[] LP_b, GaussianElimination gauss) {
        List<int[]> eqSets = CombSubSet.getSubSet(LP_a.length, LP_a[0].length);
        List<double[]> solutionList = new ArrayList<>();
        for (int[] equations : eqSets) {
            int row = 0;
            for (int lpRow : equations) {
                gauss.updateRow(row, LP_a[lpRow], LP_b[lpRow]);
                row++;
            }
            double[] solution = gauss.SolveEquation();
            if (solution != null) {
                boolean goodSolution = checkSolution(equations, LP_a, LP_b, solution);
                if (goodSolution) {
                    double[] goodSol = Arrays.copyOf(solution, solution.length);
                    solutionList.add(goodSol);
                }
            }
        }
        return solutionList;
    }

    /**
     * @param LP_a
     * @param LP_b
     * @param solution
     */
    boolean checkSolution(int[] equations, double[][] LP_a, double[] LP_b, double[] solution) {
        final double EPSILON = 1E-6;
        int numVar = LP_a[0].length;
        int numConst = LP_a.length - numVar - 1;
        // Need to skip over original equations to avoid round off error
        HashSet<Integer> skipCheck = new HashSet<>();
        for (int eq : equations)
            skipCheck.add(eq);
        for (int i = 0; i < LP_a.length; ++i) {
            if (skipCheck.contains(i))
                continue;
            double dotP = 0.0;// dot product
            for (int j = 0; j < numVar; ++j)
                dotP += LP_a[i][j] * solution[j];
            if ((dotP - LP_b[i]) > EPSILON) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param n
     * @param m
     * @param A
     * @param b
     * @param LP_a
     * @param LP_b
     */
    private void builFullEq(int n, int m, double[][] A, double[] b, double[][] LP_a, double[] LP_b) {
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < A[i].length; ++j)
                LP_a[i][j] = A[i][j];
            LP_b[i] = b[i];
        }

        // All the equations Xi <= 0
        for (int i = 0; i < m; ++i) {
            int row = i + n;
            for (int j = 0; j < m; ++j)
                LP_a[row][j] = i == j ? -1.0 : 0.0;
            LP_b[row] = 0.0;
        }
        for (int j = 0; j < m; ++j)
            LP_a[n + m][j] = 1.0;
        LP_b[n + m] = INFINITY_VAL + 1;
    }

    void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        double[][] A = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = nextInt();
            }
        }
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = nextInt();
        }
        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = nextInt();
        }
        double[] ansx = new double[m];
        int anst = solveDietProblem(n, m, A, b, c, ansx);
        if (anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (anst == 1) {
            out.printf("Infinity\n");
            return;
        }
    }

    Diet() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Diet();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
}
