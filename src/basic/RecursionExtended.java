package basic;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecursionExtended {
    public static double zahlHoch(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            double halfPower = zahlHoch(x, n / 2);
            return halfPower * halfPower;
        } else {
            return x * zahlHoch(x, n - 1);
        }
    }

    public static int findMaximumTotal(String filename) {
        try (BufferedReader b = Files.newBufferedReader(Path.of(filename))) {
            int[][] triangle = b.lines().map(line -> line.split(" ")).map(arr -> {
                int[] row = new int[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    row[i] = Integer.parseInt(arr[i]);
                }
                return row;
            }).toArray(int[][]::new);
            System.out.println(triangle.length);
            return findMaximumTotal(triangle, 0, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    private static int findMaximumTotal(int[][] triangle, int row, int col) {
        if (row == triangle.length - 1) {
            return triangle[row][col];
        }
        int leftPathSum = findMaximumTotal(triangle, row + 1, col);
        int rightPathSum = findMaximumTotal(triangle, row + 1, col + 1);
        return triangle[row][col] + Math.max(leftPathSum, rightPathSum);
    }

    public static void main(String[] args) {
        double x = 2;
        int n = 10;
        System.out.println(x + " hoch " + n + " ist " + zahlHoch(x, n));
        String filename = "resources/numberTriangle.txt";
        System.out.println("Maximum total in triangle is " + findMaximumTotal(filename));
        int[][] test = {
            {3},
            {7, 4},
            {2, 4, 6},
            {8, 5, 9, 3}
        };
        System.out.println(findMaximumTotal(test, 0, 0));
    }
}
