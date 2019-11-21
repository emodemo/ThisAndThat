package datastructures.arrays;

public class SparseArrays {
	

	static int[] sparseArray(String[] strings, String[] queries) {

		int[] result = new int[queries.length];
		int index = 0;
		int tmpresult = 0;
		for(String query : queries) {
			for(String str : strings) {
				if(query.equals(str)) tmpresult ++;
			}
			result[index] = tmpresult;
			index ++;
			tmpresult = 0;
		}
		
		return result;
		
	}

}
