import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PrimitiveCalculator {

    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        int num[] = solution(n);
        while (n >= 1) {
            sequence.add(n);
            if ((n % 3 == 0) && (num[n / 3] == (num[n] - 1))) {
                n /= 3;
            } else if ((n % 2 == 0) && (num[n / 2] == (num[n] - 1))) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }

    private static int[] solution(int n) {
        int solTable[] = new int[n + 1];
        solTable[0] = 0;
        for (int i = 1; i < (n + 1); ++i) {
            solTable[i] = solTable[i - 1] + 1;
            if ((i % 2) == 0) {
                solTable[i] = Math.min(solTable[i / 2] + 1, solTable[i]);
            }
            if ((i % 3) == 0) {
                solTable[i] = Math.min(solTable[i / 3] + 1, solTable[i]);
            }
        }
        return solTable;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}
