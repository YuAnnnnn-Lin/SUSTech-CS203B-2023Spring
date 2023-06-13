import java.util.Scanner;

public class Matrix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        int count = 1;
        int rowStart = 0;
        int rowEnd = n - 1;
        int colStart = 0;
        int colEnd = m - 1;
        while (rowStart <= rowEnd && colStart <= colEnd) {
            for (int i = rowStart; i <= rowEnd; i++) {
                matrix[i][colStart] = count++;
            }
            colStart++;
            for (int i = colStart; i <= colEnd; i++) {
                matrix[rowEnd][i] = count++;
            }
            rowEnd--;
            if (colStart <= colEnd) {
                for (int i = rowEnd; i >= rowStart; i--) {
                    matrix[i][colEnd] = count++;
                }
                colEnd--;
            }
            if (rowStart <= rowEnd) {
                for (int i = colEnd; i >= colStart; i--) {
                    matrix[rowStart][i] = count++;
                }
                rowStart++;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
