package io.creek;

import static io.creek.UtilCompare.compare;
import static io.creek.UtilGraph.loadDataInGraph;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.creek.Graph.GraphType;

class BFSSPTest {

	@Test
	void graph() {
		Graph<Double> graph = Graph.createGraph(GraphType.UNDIRECTED);
		loadDataInGraph("graph.txt", " ", graph, (s -> Double.parseDouble(s)));
		List<Double> sort = BFSSP.shortestPath(graph, 0.0, 6.0);
		assertTrue(compare(sort, asList(new Double[]{0.0, 5.0, 6.0})));
	}

}
