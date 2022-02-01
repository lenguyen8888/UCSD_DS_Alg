import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class EnergyValues {

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
            used_rows = new boolean[size];
            used_columns = new boolean[size];
            pivot_element = new Position(0, 0);
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

            used_columns = new boolean[size];
            used_rows = new boolean[size];
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

        void PrintColumn(double column[]) {
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

    public static void main(String[] args) throws IOException {
        GaussianElimination gauss = initGuassian();
        double[] solution = gauss.SolveEquation();
        gauss.PrintColumn(solution);
    }

    /**
     * @return
     * @throws IOException
     */
    private static GaussianElimination initGuassian() throws IOException {
        GaussianElimination.Equation equation = GaussianElimination.ReadEquation();
        int size = equation.a.length;
        GaussianElimination gauss = new GaussianElimination(size);
        for (int row = 0; row < size; ++row) {
            gauss.updateRow(row, equation.a[row], equation.b[row]);
        }
        return gauss;
    }
}
