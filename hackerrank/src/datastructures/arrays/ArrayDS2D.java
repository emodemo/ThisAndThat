package datastructures.arrays;

public class ArrayDS2D {

	public static void main(String[] args) {
		int[][] test = {{-9,-9,-9,1,1,1},
						{0,-9,0,4,3,2},
						{-9,-9,-9,1,2,3},
						{0,0,8,6,6,0},
						{0,0,0,-2,0,0},
						{0,0,1,2,4,0}};
		
		int result = arrya2d(test);
		System.out.println(result);
		
	}
	
	static int arrya2d(int[][] input) {
		
		int sum = -63;
		int tmpsum = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				tmpsum += input[i][j]; //a
				tmpsum += input[i][j+1]; //b
				tmpsum += input[i][j+2]; //c
				tmpsum += input[i+1][j+1]; //d
				tmpsum += input[i+2][j]; //e
				tmpsum += input[i+2][j+1]; //f
				tmpsum += input[i+2][j+2]; //g
				if(tmpsum > sum) sum = tmpsum;
				tmpsum = 0;
			}
		}
		
		return sum;
	}

}
