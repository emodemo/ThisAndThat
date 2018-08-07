package codefights.practice;

import java.util.ArrayList;

public class Sorting {

	ArrayList<Integer> mergeKArrays(int[][] arrays) {
		int[] firstUnused = new int[arrays.length];
		ArrayList<Integer> result = new ArrayList<>();
		int n = 0;
		for (int i = 0; i < arrays.length; i++) {
			n += arrays[i].length;
		}
		for (int i = 0; i < n; i++) {
			int minIndex = -1, minValue = 0;
			for (int j = 0; j < arrays.length; j++) {
				if (firstUnused[j] < arrays[j].length) {
					if (minIndex == -1 || minValue > arrays[j][firstUnused[j]]) {
						minIndex = j;
						minValue = arrays[j][firstUnused[j]];
					}
				}
			}
			result.add(minValue);
			firstUnused[minIndex]++;
		}
		return result;
	}
	
	
	int[] quickSort(int[] a, int l, int r) {

		  if (l >= r) {
		    return a;
		  }

		  int x = a[l];
		  int i = l;
		  int j = r;

		  while (i <= j) {
		    while (a[i] < x) {
		      i++;
		    }
		    while (a[j] > x) {
		      j--;
		    }
		    if (i <= j) {
		      int t = a[i];
		      a[i] = a[j];
		      a[j] = t;
		      i++;
		      j--;
		    }
		  }

		  if (l < i - 1)
		        quickSort(a, l, i - 1);
		  if (i < r)
		        quickSort(a, i, r);

		  return a;
		}
	
	int higherVersion2(String ver1, String ver2) {
	    String[] s1 = ver1.split("\\.");
	    String[] s2 = ver2.split("\\.");
	    for(int i = 0; i < s1.length; i++){
	    	int i1 = Integer.parseInt(s1[i]);
	    	int i2 = Integer.parseInt(s2[i]);
	    	if(i1 > i2) return 1;
	    	if(i1 < i2) return -1;
	    }
	    return 0;
	}
	
	String sortByString(String s, String t) {
		char[] result = new char[s.length()];
		char[] ss = s.toCharArray();
		int index = 0;
		for(char c : t.toCharArray()){
			for(char sss : ss){
				if(c == sss){
					result[index++] = sss;
				}
			}
		}
		return new String(result);
	}
	
	// use two pointers - start and end
	// array = [-6, -4, 1, 2, 3, 5]
	// [36, 16, 1, 4, 9, 25]
	// [1, 4, 9, 16, 25, 36]
	int[] sortedSquaredArray(int[] array) {
		int start = 0;
		int end = array.length - 1;
		int index = array.length - 1;
		int[] result = new int[array.length];
		while(end >= start){
			if(Math.abs(array[start]) < Math.abs(array[end])){
				result[index--] = array[end] * array[end];
				end--;

			}else{
				result[index--] = array[start] * array[start];
				start++;
			}				
		}
		return result;
	}
	
}
