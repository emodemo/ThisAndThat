package io.creek;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import io.creek.Graph.Edge;
import io.creek.Graph.GraphType;

public class DijkstraSP {

	/** shortest path bfs for directed, weighted graph, time E log V, space V */
	public static <T> List<Edge<T>> shortestPath(Graph<T> graph, T from, T to){
		if(graph.getType() == GraphType.UNDIRECTED) return Collections.emptyList();
		
		// distanceTo and minDistanceTo contain the same information, just the 2nd one is a queue used for looping
		// probably could be optimized with a kind of hash that can serves the queue requirements, without removing them.
		
		Map<T, Edge<T>> edgeTo = new HashMap<>();
		Map<T, Double> distanceTo = new HashMap<>();
		distanceTo.put(from, 0.0);
		
		PriorityQueue<Tuple<T>> minDistanceTo = new PriorityQueue<>(comparing(t -> t.weight));
		minDistanceTo.add(new Tuple<T>(from, 0.0));
		
		while(minDistanceTo.isEmpty() == false) { 
			T node = minDistanceTo.poll().node;
			for(Edge<T> edge : graph.getAdjacentEdges(node)) {
				double currentDistance = distanceTo.getOrDefault(edge.to(), Double.POSITIVE_INFINITY);
				double newDistance = distanceTo.getOrDefault(edge.from(), Double.POSITIVE_INFINITY) + edge.weight();
				if(newDistance < currentDistance) {
					distanceTo.put(edge.to(), newDistance);
					edgeTo.put(edge.to(), edge);
					minDistanceTo.offer(new Tuple<T>(edge.to(), newDistance));
				}
			}
		}
		
		List<Edge<T>> result = new ArrayList<>();
		Edge<T> edge = edgeTo.get(to);
		while(edge != null) {
			result.add(0, edge); // reverse order
			edge = edgeTo.get(edge.from()); 
		}
		return result;
	}
	
	private static class Tuple<T> {
		private T node;
		private double weight;
		
		private Tuple(T node, double weight) {
			super();
			this.node = node;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return node.toString() + " : " + weight;  
		}
	}
}
