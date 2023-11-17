import java.util.Scanner;

public class CarFueling {
    static int computeMinRefills(int dist, int tank, int[] stops) {
        int maxNumStops = stops.length;
        int[] stopVals = new int[maxNumStops + 2];
        stopVals[0] = 0;
        for (int i = 0; i < maxNumStops; ++i)
            stopVals[i + 1] = stops[i];
        stopVals[maxNumStops + 1] = dist;
        int currRefill = 0;
        int numRefill = 0;
        while (currRefill <= maxNumStops) {
            int lastRefill = currRefill;
            while (currRefill <= maxNumStops && (stopVals[currRefill + 1] - stopVals[lastRefill]) <= tank)
                ++currRefill;
            if (currRefill == lastRefill)
                return -1;
            if (currRefill <= maxNumStops)
                ++numRefill;

        }
        return numRefill;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));

        scanner.close();
    }
}
