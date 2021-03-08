package io.creek;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.creek.Graph.Edge;

public class PrimMST {

	/** minimum spanning tree for weighted graph, time E log V, space V */
	public static <T> List<Edge<T>> minimumSpanningTree(Graph<T> graph){
		// distanceTo and minDistanceTo contain the same information, just the 2nd one is a queue used for looping
		// probably could be optimized with a kind of hash that can serves the queue requirements, without removing them.
		
		Set<T> visited = new HashSet<>();
		Set<Edge<T>> distanceTo = new HashSet<>();
		
		// minimum spanning forest. if graph has no islands, it will have only one starting node.
		for(T startNode : graph.getNodes()) {
			if(visited.contains(startNode)) continue;
			visited.add(startNode);
			
			// minimum spanning tree algorithm
			// put all adjacents as a starting point
			Map<T, Edge<T>> minDistanceFromTo = new HashMap<>();
			graph.getAdjacentEdges(startNode).forEach(edge -> minDistanceFromTo.put(edge.to(), edge));
			while(minDistanceFromTo.isEmpty() == false) {
				Map.Entry<T, Edge<T>> closerToTree = findBy(minDistanceFromTo, comparing(e -> e.getValue().weight()));
				Edge<T> closerEdge = closerToTree.getValue();
				distanceTo.add(closerEdge);
				minDistanceFromTo.remove(closerEdge.to());
				visited.add(closerEdge.to());
				// go through edges and update eligible if path to new node or shorter to already eligible node is found
				for(Edge<T> edge : graph.getAdjacentEdges(closerEdge.to())) {
					if(visited.contains(edge.to())) continue; // skip already visited
					// add if missing, update if closer one is found
					Edge<T> match = minDistanceFromTo.get(edge.to());
					if(match == null || edge.weight() < match.weight()) minDistanceFromTo.put(edge.to(), edge);
				}
			}
		}
		
		return distanceTo.stream().collect(Collectors.toList());
	}
	
	private static <T> Map.Entry<T, Edge<T>> findBy(Map<T, Edge<T>> map, Comparator<Map.Entry<T, Edge<T>>> comparator){
		Map.Entry<T, Edge<T>> result = null;
		for(Map.Entry<T, Edge<T>> entry : map.entrySet()) {
			if(result == null || comparator.compare(entry, result) == -1) {
				result = entry;
			}
		}
		return result;
	}
}
