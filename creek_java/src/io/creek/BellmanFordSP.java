package io.creek;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


import io.creek.Graph.Edge;

public class BellmanFordSP {

	// another possibility is Floyd Warshall - time V^3
	/** shortest path bfs for directed, weighted graph, time E V, space V */
	public static <T> List<Edge<T>> shortestPath(Graph<T> graph, T from, T to){
		
		Map<T, Edge<T>> edgeTo = new HashMap<>();
		Map<T, Double> distanceTo = new HashMap<>();
		distanceTo.put(from, 0.0);

		// instead of queue a getNodes^2 will give the same result  
		Queue<T> queue = new LinkedList<>();
		queue.offer(from);
		while(queue.isEmpty() == false) {
			for(Edge<T> edge : graph.getAdjacentEdges(queue.poll())) {
				double currentDistance = distanceTo.getOrDefault(edge.to(), Double.POSITIVE_INFINITY);
				double newDistance = distanceTo.getOrDefault(edge.from(), Double.POSITIVE_INFINITY) + edge.weight();
				if(newDistance < currentDistance) {
					distanceTo.put(edge.to(), newDistance);
					edgeTo.put(edge.to(), edge);
					if(queue.contains(edge.to()) == false) queue.offer(edge.to());
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
}
