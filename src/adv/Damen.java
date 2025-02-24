package adv;

import java.util.ArrayList;
import java.util.List;

public class Damen {
    private final int n; // Größe des Schachbretts
    private final List<List<String>> solutions; // Liste der Lösungen

    public Damen(int n) {
        this.n = n;
        this.solutions = new ArrayList<>();
    }

    public static void main(String[] args) {
        int n = 8; // Für das klassische 8-Damenproblem
        Damen nQueens = new Damen(n);
        nQueens.solve();
        nQueens.printSolutions();
        System.out.println("Anzahl der Lösungen: " + nQueens.solutions.size());
    }

    public void solve() {
        int[] board = new int[n]; // board[i] = Spalte der Dame in Zeile i
        placeQueens(board, 0);
    }

    private void placeQueens(int[] board, int row) {
        if (row == n) {
            addSolution(board);
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col)) {
                board[row] = col; // Dame setzen
                placeQueens(board, row + 1); // Nächste Zeile
            }
        }
    }

    private boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            // Überprüfen, ob eine Dame in der gleichen Spalte oder Diagonale ist
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    private void addSolution(int[] board) {
        List<String> solution = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char column = (char) ('A' + board[i]); // Spalte in Buchstaben umwandeln
            solution.add(column + "" + (n - i)); // Zeile umkehren (1-8)
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