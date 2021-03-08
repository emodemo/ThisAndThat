package io.creek;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.creek.Graph.Edge;
import io.creek.Graph.GraphType;

//TODO: update shortest paths BFS, Acyclic, Dijkstra, BellmanFord and Topological to give infinity when no path exists and to return cycles

public class AcyclicSP {

	/** for edge-weighted directed graphs - time E + V ; space V */
	public static <T> List<Edge<T>> shortestPath(Graph<T> graph, T from, T to) {
		return path(graph, from, to, Double.POSITIVE_INFINITY, naturalOrder());
	}

	/** for edge-weighted directed graphs - time E + V ; space V */
	public static <T> List<Edge<T>> longestPath(Graph<T> graph, T from, T to) {
		return path(graph, from, to, Double.NEGATIVE_INFINITY, reverseOrder());
	}

	/** for edge-weighted directed graphs - time E + V ; space V */
	private static <T> List<Edge<T>> path(Graph<T> graph, T from, T to, Double initialDistance, Comparator<Double> comparator) {
		if (graph.getType() == GraphType.UNDIRECTED) return Collections.emptyList();

		Map<T, Edge<T>> edgeTo = new HashMap<>();
		Map<T, Double> distanceTo = new HashMap<>();
		distanceTo.put(from, 0.0);

		List<T> sorted = TopologicalSort.sort(graph);

		for (T node : sorted) {
			for (Edge<T> edge : graph.getAdjacentEdges(node)) {
				double currentDistance = distanceTo.getOrDefault(edge.to(), initialDistance);
				double newDistance = distanceTo.getOrDefault(edge.from(), initialDistance) + edge.weight();
				if (comparator.compare(newDistance, currentDistance) == -1) { // 1st is less than second
					distanceTo.put(edge.to(), newDistance);
					edgeTo.put(edge.to(), edge);
				}
			}
		}

		List<Edge<T>> result = new ArrayList<>();
		Edge<T> edge = edgeTo.get(to);
		while (edge != null) {
			result.add(0, edge); // reverse order
			edge = edgeTo.get(edge.from());
		}
		return result;
	}
}
