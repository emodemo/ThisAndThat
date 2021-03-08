package io.creek;

import static io.creek.UtilGraph.loadDataInGraph;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.creek.Graph.GraphType;

class AcyclicSPTest {

	@Test
	void shortestPath() {
		
		double eps = 0.0001;
		Graph<Integer> graph = Graph.createGraph(GraphType.DIRECTED);
		loadDataInGraph("directed_weighted_graph_acyclic.txt", " ", graph, (s -> Integer.parseInt(s)));
		
		assertEquals(0.73, AcyclicSP.shortestPath(graph, 5, 0).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.32, AcyclicSP.shortestPath(graph, 5, 1).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.62, AcyclicSP.shortestPath(graph, 5, 2).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.61, AcyclicSP.shortestPath(graph, 5, 3).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.35, AcyclicSP.shortestPath(graph, 5, 4).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.00, AcyclicSP.shortestPath(graph, 5, 5).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(1.13, AcyclicSP.shortestPath(graph, 5, 6).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.28, AcyclicSP.shortestPath(graph, 5, 7).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
	}
	
	@Test
	void longestPath() {
		
		double eps = 0.0001;
		Graph<Integer> graph = Graph.createGraph(GraphType.DIRECTED);
		loadDataInGraph("directed_weighted_graph_acyclic.txt", " ", graph, (s -> Integer.parseInt(s)));
		
		assertEquals(2.44, AcyclicSP.longestPath(graph, 5, 0).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.32, AcyclicSP.longestPath(graph, 5, 1).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(2.77, AcyclicSP.longestPath(graph, 5, 2).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.61, AcyclicSP.longestPath(graph, 5, 3).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(2.06, AcyclicSP.longestPath(graph, 5, 4).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.00, AcyclicSP.longestPath(graph, 5, 5).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(1.13, AcyclicSP.longestPath(graph, 5, 6).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(2.43, AcyclicSP.longestPath(graph, 5, 7).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
	}

}
