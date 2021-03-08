package io.creek;

import java.util.List;

public class UtilCompare {

	public static <T> boolean compare(List<T> a, List<T> b) {
		if(a.size() != b.size()) return false;
		for(int i = 0; i < a.size(); i++) {
			if(!a.get(i).equals(b.get(i))) return false;
		}
		return true;
	}
	
	public static <T> boolean compare(T[] a, T[] b) {
		if(a.length != b.length) return false;
		for(int i = 0; i < a.length; i++) {
			if(!a[i].equals(b[i])) return false;
		}
		return true;
	}
	
}
