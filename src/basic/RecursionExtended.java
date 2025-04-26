package basic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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


    private static int[][] readTriangleFromFile(String filename) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filename));
            int[][] triangle = new int[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(" ");
                triangle[i] = new int[parts.length];
                for (int j = 0; j < parts.length; j++) {
                    triangle[i][j] = Integer.parseInt(parts[j]);
                }
            }
            return triangle;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findMaxTotal(int[][] triangle) {
        return computeBottomUp(triangle, triangle.length - 2);
    }

    private static int computeBottomUp(int[][] triangle, int row) {
        if (row < 0) {
            return triangle[0][0];
        }
        for (int col = 0; col < triangle[row].length; col++) {
            triangle[row][col] += Math.max(triangle[row + 1][col], triangle[row + 1][col + 1]);
        }
        return computeBottomUp(triangle, row - 1);
    }


    public static void main(String[] args) {
        double x = 2;
        int n = 10;
        System.out.println(x + " hoch " + n + " ist " + zahlHoch(x, n));
        String filename = "resources/numberTriangle.txt";//asdfasf
        System.out.println("Maximum total in triangle is " + findMaxTotal(readTriangleFromFile(filename)));
        Math.pow(2, 10);
    }
}
