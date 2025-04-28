package adv;

import java.io.*;
import java.util.List;

public class CrackySudoku {


    public static void main(String[] args) {
        List<String> files = List.of("resources/simple.sudoku", "resources/easy.sudoku", "resources/intermediate.sudoku", "resources/expert.sudoku");
        try (PrintWriter output = new PrintWriter(new FileWriter("sudoku.solved", true))) {
            for (String file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(":");
                        String name = parts[0].trim();
                        int[][] grid = parse(parts[1].trim());

                        System.out.println(name + ":");
                        print(grid);

                        int[][] solution = deepCopy(grid);
                        if (solve(solution)) {
                            System.out.println(name + " (gelöst):");
                            print(solution);
                            output.println(name + ";" + toString(grid) + ";" + toString(solution));
                        } else {
                            System.out.println(name + " konnte nicht gelöst werden.");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Fehler beim Lesen von " + file + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Lösungen: " + e.getMessage());
        }
    }

    static int[][] parse(String s) {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 81; i++) {
            char c = s.charAt(i);
            grid[i / 9][i % 9] = (c == '.') ? 0 : (c - '0');
        }
        return grid;
    }

    static String toString(int[][] grid) {
        StringBuilder sb = new StringBuilder(81);
        for (int[] row : grid)
            for (int val : row)
                sb.append(val == 0 ? '.' : (char) ('0' + val));
        return sb.toString();
    }

    static void print(int[][] grid) {
        System.out.println("+ --- + --- + --- +");
        for (int i = 0; i < 9; i++) {
            System.out.print("|");
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] == 0 ? "  " : " " + grid[i][j]);
                if (j % 3 == 2) System.out.print("|");
            }
            System.out.println();
            if (i % 3 == 2) System.out.println("+ --- + --- + --- +");
        }
    }

    static int[][] deepCopy(int[][] grid) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(grid[i], 0, copy[i], 0, 9);
        return copy;
    }

    static boolean solve(int[][] grid) {
        int[][] candidates = initCandidates(grid);
        return backtrack(grid, candidates);
    }

    static int[][] initCandidates(int[][] grid) {
        int[][] candidates = new int[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                candidates[row][col] = (grid[row][col] == 0) ? 0x1FF : 0;
        updateCandidates(grid, candidates);
        return candidates;
    }

    static void updateCandidates(int[][] grid, int[][] candidates) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int num = grid[row][col];
                if (num != 0) eliminate(candidates, row, col, num);
            }
        }
    }

    static void eliminate(int[][] candidates, int row, int col, int num) {
        int mask = ~(1 << (num - 1));
        for (int i = 0; i < 9; i++) {
            candidates[row][i] &= mask;
            candidates[i][col] &= mask;
            candidates[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] &= mask;
        }
    }

    static boolean backtrack(int[][] grid, int[][] candidates) {
        int minOptions = 10, targetRow = -1, targetCol = -1;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    int options = Integer.bitCount(candidates[row][col]);
                    if (options < minOptions) {
                        minOptions = options;
                        targetRow = row;
                        targetCol = col;
                        if (options == 1) break; // Schnell abbrechen bei 1 Option
                    }
                }
            }
            if (minOptions == 1) break;
        }

        if (targetRow == -1) return true; // Keine freie Zelle mehr

        int[][] state = deepCopy(candidates);
        int mask = candidates[targetRow][targetCol];
        for (int num = 1; num <= 9; num++) {
            if ((mask & (1 << (num - 1))) != 0) {
                grid[targetRow][targetCol] = num;
                candidates[targetRow][targetCol] = 0;
                eliminate(candidates, targetRow, targetCol, num);

                if (backtrack(grid, candidates)) return true;

                grid[targetRow][targetCol] = 0;
                candidates = deepCopy(state);
            }
        }
        return false;
    }
}