import java.util.Scanner;

class EditDistance {
    public static int EditDistance(String s, String t) {
        // write your code here

        int dist[][] = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i < (s.length() + 1); ++i)
            dist[i][0] = i;
        for (int j = 0; j < (t.length() + 1); ++j)
            dist[0][j] = j;

        for (int i = 1; i < (s.length() + 1); ++i) {
            for (int j = 1; j < (t.length() + 1); ++j) {
                int insert = dist[i][j - 1] + 1;
                int delete = dist[i - 1][j] + 1;
                int match = dist[i - 1][j - 1];
                int mismatch = dist[i - 1][j - 1] + 1;
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dist[i][j] = Math.min(insert, Math.min(delete, match));
                } else {
                    dist[i][j] = Math.min(insert, Math.min(delete, mismatch));
                }
            }
        }
        return dist[s.length()][t.length()];
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(EditDistance(s, t));
    }

}
