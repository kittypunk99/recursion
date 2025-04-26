package adv;
import java.io.*;
import java.util.*;

public class Sudoku {

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
        for (int i = 0; i < 81; i++)
            grid[i/9][i%9] = s.charAt(i) == '.' ? 0 : s.charAt(i) - '0';
        return grid;
    }

    static String toString(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : grid)
            for (int val : row)
                sb.append(val == 0 ? "." : val);
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
            copy[i] = Arrays.copyOf(grid[i], 9);
        return copy;
    }

    static boolean solve(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (valid(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solve(grid)) return true;
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    static boolean valid(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
            if (grid[(row/3)*3+i/3][(col/3)*3+i%3] == num) return false;
        }
        return true;
    }
}

