package io.creek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.creek.Graph.GraphType;

public class TopologicalSort {

	/** @return the topological sort or empty list if it cannot be performed */
	public static <T> List<T> sort(Graph<T> graph) {
		if(graph.getType() == GraphType.UNDIRECTED) return Collections.emptyList();
		List<T> result = new ArrayList<>();
		List<T> visited = new ArrayList<>();
		for(T node : graph.getNodes()) {
			if(visited.contains(node)) continue;
			if(dfs(node, graph, result, visited, new ArrayList<>())) return Collections.emptyList();
		}
		return result;
	}

	/** @return {@code true} if cycle was detected, hence topological sort cannot be performed */
	private static <T> boolean dfs(T node, Graph<T> graph, List<T> result, List<T> visited, List<T> onStack) {
		visited.add(node);
		onStack.add(node);
		for(T adjacent : graph.getAdjacentNodes(node)) {
			if (onStack.contains(adjacent)) return true; // already on the current path
			if (visited.contains(adjacent)) continue; // already visited but not in the current path
			if(dfs(adjacent, graph, result, visited, onStack)) return true;
		}
		onStack.remove(node);
		result.add(0, node);  // reverse post-order
		return false;
	}
	
}
