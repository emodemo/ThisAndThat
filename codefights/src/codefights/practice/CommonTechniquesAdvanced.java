package codefights.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class CommonTechniquesAdvanced {

	class Point{
		double x, y;
		boolean start;
		Point(double x, double y, boolean start){
			this.x = x;
			this.y = y;
			this.start = start;
		}
		
		@Override
		public String toString() {
			return String.format("%1$.1f %2$.1f %3$s", x, y, start);
		}
	}

	// rolling window, but with a rolling hash could be faster
	String[] repeatedDNASequences(String s) {
		if(s.length() < 10) return new String[] {};

		List<String> result = new ArrayList<>();
		Set<String> set = new HashSet<>();
		for(int i = 0; i < s.length() - 9; i++) {
			String sequence = s.substring(i, i+10);
			if(set.contains(sequence) && result.contains(sequence) == false) {
				result.add(sequence);
			} else {
				set.add(sequence);
			}
		}
		result.sort(Comparator.naturalOrder());
		return result.toArray(new String[] {});
	}
	
	// does not work for the last case. Probably related to the comparison method in the sorter
	// => see the 2nd solution that does not use Point but separate start and end points by difference in the sign.
	// here with PQ, but as pq.remove is slow a TreeMap could lead to faster results
	double[][] getSkyline(double[][] buildings) {
		List<Point> points = new ArrayList<>();
		for (double[] d : buildings) {
			points.add(new Point(d[0], d[2], true));
			points.add(new Point(d[0] + d[1], d[2], false));
		}
		// sort from left to right
		Collections.sort(points, (p1, p2) -> {
			if(p1.x != p2.x) return Double.compare(p1.x, p2.x);
			if(p1.y != p2.y) return Double.compare(p1.y, p2.y);
			return p1.start ? 1 : -1;
	    });
		
		List<double []> result = new ArrayList<double[]>(); 
		
		Queue<Double> pq = new PriorityQueue<>((a, b) -> Double.compare(b, a));
		pq.offer(0.0);
		double prev = 0.0;
		// on start point => add the rectangle to the heap (key is height)
		// on end point => remove the rectangle from the heap
		for(Point p : points) {
			if(p.start) pq.offer(p.y);
			else pq.remove(p.y);
			double current = pq.peek();
			if(prev != current) {
				result.add(new double[]{p.x, current});
				prev = current;
			}
		}
		return result.toArray(new double[result.size()][]);
	}
	
	double[][] getSkyline2(double[][] buildings) {
		List<double[]> result = new ArrayList<>();
		List<double[]> height = new ArrayList<>();
		for(double[] b:buildings) {
	        height.add(new double[]{b[0], -b[2]});
		    height.add(new double[]{b[0] + b[1], b[2]});
		}
		Collections.sort(height, (a, b) -> {
	         if(a[0] != b[0]) return Double.compare(a[0], b[0]);
		     return Double.compare(a[1], b[1]);
		});
		Queue<Double> pq = new PriorityQueue<>((a, b) -> Double.compare(b, a));
		pq.offer(0.0);
		double prev = 0.0;
		for(double[] h : height) {
		   if(h[1] < 0) pq.offer(-h[1]);
		   else pq.remove(h[1]);
		   double cur = pq.peek();
		   if(prev != cur) {
		      result.add(new double[]{h[0], cur});
		      prev = cur;
		   }
		}
		return result.toArray(new double[result.size()][]);
	}

}
