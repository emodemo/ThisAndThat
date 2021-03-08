package io.creek;

import static io.creek.UtilGraph.loadDataInGraph;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import io.creek.Graph.GraphType;

class DijkstraSPTest {
	
	@Test
	void shortestPath() {
		
		double eps = 0.0001;
		Graph<Integer> graph = Graph.createGraph(GraphType.DIRECTED);
		loadDataInGraph("directed_weighted_graph_2.txt", " ", graph, (s -> Integer.parseInt(s)));
		
		assertEquals(0.00, DijkstraSP.shortestPath(graph, 0, 0).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(1.05, DijkstraSP.shortestPath(graph, 0, 1).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.26, DijkstraSP.shortestPath(graph, 0, 2).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.99, DijkstraSP.shortestPath(graph, 0, 3).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.38, DijkstraSP.shortestPath(graph, 0, 4).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.73, DijkstraSP.shortestPath(graph, 0, 5).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(1.51, DijkstraSP.shortestPath(graph, 0, 6).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
		assertEquals(0.60, DijkstraSP.shortestPath(graph, 0, 7).stream().map(e -> e.weight()).reduce(0.0, (d1, d2) -> d1 + d2), eps);
	}

}
