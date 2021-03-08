package io.creek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import io.creek.Graph.GraphType;

public class BFSSP {

	/** shortest path bfs for undirected, unweighted graph, time V + E for worst case */
	public static <T> List<T> shortestPath(Graph<T> graph, T from, T to){
		if(graph.getType() == GraphType.DIRECTED) return Collections.emptyList();

		Map<T, T> nodeAndParent = new HashMap<>();
		bfs(graph, from, nodeAndParent);	
		List<T> result = new ArrayList<>();
		T tmpNode = to;
		while(tmpNode.equals(from) == false) {
			result.add(0, tmpNode);
			tmpNode = nodeAndParent.get(tmpNode);
		}
		result.add(0, from); // reverese order
		return result;
	}
	
	private static <T> void bfs(Graph<T> graph, T from, Map<T, T> nodeAndParent) {
		nodeAndParent.put(from, from); // initial point
		Queue<T> queue = new LinkedList<>();
		queue.offer(from);
		while(queue.isEmpty() == false) {
			T tmpNode = queue.poll();
			for(T adj : graph.getAdjacentNodes(tmpNode)) {
				if(nodeAndParent.containsKey(adj)) continue;
				nodeAndParent.put(adj, tmpNode);
				queue.offer(adj);
			}
		}
	}
	
}
