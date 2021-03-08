package io.creek;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.creek.Graph.Edge;
import io.creek.Graph.GraphType;

class PrimMSTTest {

	@Test
	void mst() {
		double eps = 0.0001;
		Graph<Integer> graph = Graph.createGraph(GraphType.UNDIRECTED);
		UtilGraph.loadDataInGraph("mst.txt", " ", graph, (s -> Integer.parseInt(s)));
		
		List<Edge<Integer>> mst = PrimMST.minimumSpanningTree(graph);
		Double sum = mst.stream().map(edge -> edge.weight()).reduce(0.0, (d1, d2) -> d1 + d2);
		assertEquals(7, mst.size()); // n of edges in the path
		assertEquals(1.81, sum, eps); // the total path
		
		mst.sort(Comparator.comparing(edge -> edge.weight()));
		assertEquals(0.16, mst.get(0).weight(), eps);  // 0 - 7
		assertEquals(0.17, mst.get(1).weight(), eps);  // 2 - 3
		assertEquals(0.19, mst.get(2).weight(), eps);  // 7 - 1
		assertEquals(0.26, mst.get(3).weight(), eps);  // 2 - 0
		assertEquals(0.28, mst.get(4).weight(), eps);  // 7 - 5
		assertEquals(0.35, mst.get(5).weight(), eps);  // 5 - 4
		assertEquals(0.40, mst.get(6).weight(), eps);  // 2 - 6
	}
	
}
