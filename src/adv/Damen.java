package adv;

import java.util.ArrayList;

import java.util.List;

public class Damen {
    private final int n;
    private final List<List<String>> solutions;

    public Damen(int n) {
        this.n = n;
        this.solutions = new ArrayList<>();
    }

    public static void main(String[] args) {
        int n = 8;
        Damen nQueens = new Damen(n);
        nQueens.solve();
        nQueens.printSolutions();
        System.out.println("Anzahl der Lösungen: " + nQueens.solutions.size());
    }

    public void solve() {
        int[] board = new int[n];
        placeQueens(board, 0);
    }

    private void placeQueens(int[] board, int row) {
        if (row == n) {
            addSolution(board);
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col)) {
                board[row] = col;
                placeQueens(board, row + 1);
            }
        }
    }

    private boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    private void addSolution(int[] board) {
        List<String> solution = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char column = (char) ('A' + board[i]);
            solution.add(column + "" + (n - i));
        }
        solutions.add(solution);
    }

    public void printSolutions() {
        int count = 1;
        for (List<String> solution : solutions) {
            System.out.println("Lösung " + count + ": " + String.join(" ", solution));
            count++;
        }
    }
}