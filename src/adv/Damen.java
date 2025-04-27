package adv;

import java.util.ArrayList;
import java.util.List;

public class Damen {
    public static void main(String[] args) {
        int n = 8;
        Damen damen = new Damen();
        List<List<String>> result = damen.solveNQueens(n);
        for (List<String> res : result) {
            System.out.println(res);
        }
        System.out.println("Anzahl der Lösungen für " + n + "-Damen: " + result.size());
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        int limit = (1 << n) - 1;
        solve(0, 0, 0, 0, n, limit, new ArrayList<>(), solutions);
        return solutions;
    }

    private void solve(int row, int cols, int diag1, int diag2, int n, int limit, List<Integer> current, List<List<String>> solutions) {
        if (row == n) {
            solutions.add(buildChessNotation(current, n));
            return;
        }
        int available = ~(cols | diag1 | diag2) & limit;
        while (available != 0) {
            int pick = available & -available;
            available -= pick;
            current.add(Integer.bitCount(pick - 1));
            solve(row + 1, cols | pick, (diag1 | pick) << 1, (diag2 | pick) >> 1, n, limit, current, solutions);
            current.removeLast();
        }
    }

    private List<String> buildChessNotation(List<Integer> current, int n) {
        List<String> notation = new ArrayList<>();
        for (int row = 0; row < current.size(); row++) {
            int col = current.get(row);
            char columnLetter = (char) ('A' + col);
            int rowNumber = row + 1;
            notation.add("" + columnLetter + rowNumber);
        }
        return notation;
    }
}
