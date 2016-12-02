package hackerrank.ctci;

import java.util.Scanner;

/**
 * See <a href=
 * "https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid">https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid</a>
 * </br>
 * <b>Sample Input:</b> </br>
 * 4 </br>
 * 4 </br>
 * 1 1 0 0 </br>
 * 0 1 1 0 </br>
 * 0 0 1 0 </br>
 * 1 0 0 0 </br>
 * <b>Sample Output:</b> </br>
 * 5 </br>
 * 
 * @author emo
 *
 */
public class DFSConnectedCellInAGrid {
	public static int[] row_indexes = { -1, 0, 1 };
	public static int[] col_indexes = { -1, 0, 1 };

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		int matrix[][] = new int[n][m];
		boolean[][] visited = new boolean[n][m];

		int distance = 0;

		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				matrix[row][col] = in.nextInt();
			}
		}

		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				int result = dfs(matrix, visited, row, col);
				distance = distance < result ? result : distance;
			}
		}

		System.out.println(distance);
		in.close();
	}

	public static int dfs(int[][] matrix, boolean[][] visited, int row, int col) {
		if (!visited[row][col] && matrix[row][col] == 1) {
			int result = 1;
			visited[row][col] = true;
			for (int rowIndex = 0; rowIndex < row_indexes.length; rowIndex++) {
				for (int colIndex = 0; colIndex < col_indexes.length; colIndex++) {
					int newRow = row + row_indexes[rowIndex];
					int newCol = col + col_indexes[colIndex];
					// sanity checks
					if (newRow > -1 && newRow < matrix.length && newCol > -1 && newCol < matrix[0].length) {
						result += dfs(matrix, visited, newRow, newCol);
					}
				}
			}
			return result;
		}
		return 0;
	}
}
