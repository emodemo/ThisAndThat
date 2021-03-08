package io.creek;

import static io.creek.Graph.GraphType.DIRECTED;
import static io.creek.Graph.GraphType.UNDIRECTED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Graph interface.</br>
 * {@code Graph<String> graph = Graph.createGraph(GraphType.UNDIRECTED);}
 * 
 * @param <T> the graph type
 */
public interface Graph<T> {

	List<T> getNodes();

	List<T> getAdjacentNodes(T node);

	List<Edge<T>> getAdjacentEdges(T node);

	void addEdge(T from, T to, double weight);

	void addEdge(T from, T to);

	GraphType getType();

	/** create {@link Graph} instance */
	static <T> Graph<T> createGraph(GraphType type) {
		switch (type) {
		case DIRECTED:
			return new DirectedGraph<T>();
		case UNDIRECTED:
			return new UndirectedGrpah<T>();
		default:
			return null;
		}
	}

	public enum GraphType {
		DIRECTED, UNDIRECTED;
	}

	abstract class AbstractGraph<T> implements Graph<T> {

		private Map<T, List<Edge<T>>> nodes = new HashMap<>();

		protected void addNode(T node) {
			nodes.putIfAbsent(node, new ArrayList<>());
		}

		protected void addEdge(T node, Edge<T> edge) {
			nodes.computeIfAbsent(node, list -> new ArrayList<>()).add(edge);
		}

		@Override
		public List<T> getNodes() {
			return new ArrayList<>(nodes.keySet().stream().collect(Collectors.toList()));
		}

		@Override
		public List<Edge<T>> getAdjacentEdges(T node) {
			return nodes.getOrDefault(node, new ArrayList<>());
		}

		@Override
		public List<T> getAdjacentNodes(T node) {
			return getAdjacentEdges(node).stream().filter(edge -> edge.to != null).map(edge -> edge.to)
					.collect(Collectors.toList());
		}

		@Override
		public void addEdge(T from, T to) {
			addEdge(from, to, 0.0);
		}
	}

	class DirectedGraph<T> extends AbstractGraph<T> {

		@Override
		public void addEdge(T from, T to, double weight) {
			if (from != null) addNode(from);
			if (to != null) addNode(to);
			if (from != null && to != null) addEdge(from, new Edge<T>(from, to, weight));
		}

		@Override
		public GraphType getType() {
			return DIRECTED;
		}
	}

	class UndirectedGrpah<T> extends AbstractGraph<T> {

		@Override
		public void addEdge(T from, T to, double weight) {
			if (from != null) addNode(from);
			if (to != null) addNode(to);
			if (from != null && to != null) {
				addEdge(from, new Edge<T>(from, to, weight));
				addEdge(to, new Edge<T>(to, from, weight));
			}
		}

		@Override
		public GraphType getType() {
			return UNDIRECTED;
		}
	}

	public static class Edge<T> {
		private T from;
		private T to;
		private double weight;

		private Edge(T from, T to, double weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		public T from() {
			return from;
		}

		public T to() {
			return to;
		}

		public double weight() {
			return weight;
		}

		@Override
		public String toString() {
			return "[ " + from + " - " + to + " : " + weight + "]";
		}
	}
}
