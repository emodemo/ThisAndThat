package codefights.arcade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Graphs {


	boolean[][] greatRenaming(boolean[][] roadRegister) {
		 int n = roadRegister.length;
		 boolean[][] newRoadRegister = new boolean[n][n];
		 
		 for(int i = 0; i < n; i++) {
			 for(int j = 0; j < n; j++) {
				 int row = i + 1, col = j + 1;
				 if(i == n - 1) row = 0;
				 if(j == n - 1) col = 0;
				 newRoadRegister[row][col] = roadRegister[i][j];
				 roadRegister.toString();
			 }
		 }
		 return newRoadRegister;
	}

	
	boolean namingRoads(int[][] roads) {
		Arrays.sort(roads, new ArrayComparator());
	    
		for (int i = 1; i < roads.length; i++) {
	        if ((roads[i][0] == roads[i-1][0]) ||
	            (roads[i][0] == roads[i-1][1]) ||
	            (roads[i][1] == roads[i-1][0]) ||
	            (roads[i][1] == roads[i-1][1])) {
	            return false;
	        }
	    }
	    return true;
	} 
	
	class ArrayComparator implements Comparator<int[]>{
		@Override
		public int compare(int[] int1, int[] int2) {
			 return Integer.compare(int1[2], int2[2]);
		}
		
	}
	
	boolean[][][] financialCrisis(boolean[][] roadRegister) {
	    int n = roadRegister.length;
	    boolean[][][] result = new boolean[n][n-1][n-1];
	    
	    for (int i = 0; i < n; i++) {
	        for (int r = 0; r < n; r++) {
	            for (int c = 0; c < n; c++) {
	                if (r != i && c != i) {
	                    result[i][r > i ? r-1 : r][c > i ? c-1 : c] = roadRegister[r][c];
	                }
	            }
	        }
	    }
	    return result;
	}
	
	int[][] roadsBuilding(int cities, int[][] roads) {
		int[][] adjMatrix = new int[cities][cities];
		for(int[] road : roads) {
			adjMatrix[road[0]][road[1]] = 1;
			adjMatrix[road[1]][road[0]] = 1;
		}
		
		List<int[]> newRoads = new ArrayList<>();
		for(int i = 0; i < cities; i++) {
			for(int j = i + 1; j < cities; j++) {
				if(adjMatrix[i][j] == 0) newRoads.add(new int[] {i, j});
			}
		}
		return newRoads.toArray(new int[newRoads.size()][2]);
	}
	
	
	boolean newRoadSystem(boolean[][] roadRegister) {
	    int[] inout = new int[roadRegister.length];
	    Arrays.fill(inout, 0);
	    for(int row = 0; row < roadRegister.length; row++){
	    	for(int col = 0; col < roadRegister.length; col++){
	    		if(roadRegister[row][col] == true){
	    			inout[col] ++;
	    			inout[row] --;
	    		}
	    	}
	    }
	    for(int i : inout){
	    	if(i != 0) return false;
	    }
	    return true;
	}
}
