package io.creek;

import static io.creek.UtilGraph.loadDataInGraph;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import io.creek.Graph.GraphType;

class ColoringTest {

	@Test
	void greedy() {
		Graph<Integer> graph = Graph.createGraph(GraphType.UNDIRECTED);
		loadDataInGraph("color_graph.txt", " ", graph, (s -> Integer.parseInt(s)));
		Map<Integer, List<Integer>> colors = Coloring.greedy(graph);
		assertEquals(4, colors.size());
		
		Map<Integer, Integer> nodeToColor = new HashMap<>();
		for(Entry<Integer, List<Integer>> entry : colors.entrySet()) {
			for(Integer node : entry.getValue()) {
				nodeToColor.put(node, entry.getKey());
			}
		}
		
		assertFalse(nodeToColor.get(1).equals(nodeToColor.get(2)));
		assertFalse(nodeToColor.get(1).equals(nodeToColor.get(3)));
		assertFalse(nodeToColor.get(1).equals(nodeToColor.get(4)));
		assertFalse(nodeToColor.get(2).equals(nodeToColor.get(5)));
		assertFalse(nodeToColor.get(3).equals(nodeToColor.get(4)));
		assertFalse(nodeToColor.get(3).equals(nodeToColor.get(6)));
		assertFalse(nodeToColor.get(3).equals(nodeToColor.get(7)));
		assertFalse(nodeToColor.get(4).equals(nodeToColor.get(5)));
		assertFalse(nodeToColor.get(4).equals(nodeToColor.get(6)));
		assertFalse(nodeToColor.get(4).equals(nodeToColor.get(7)));
		assertFalse(nodeToColor.get(5).equals(nodeToColor.get(8)));
		assertFalse(nodeToColor.get(6).equals(nodeToColor.get(7)));
		assertFalse(nodeToColor.get(7).equals(nodeToColor.get(8)));
	}
	
	@Test
	void degreeOfSaturation() {
		Graph<Integer> graph = Graph.createGraph(GraphType.UNDIRECTED);
		loadDataInGraph("color_graph.txt", " ", graph, (s -> Integer.parseInt(s)));
		Map<Integer, List<Integer>> colors = Coloring.degreeOfSaturation(graph);
		assertEquals(4, colors.size());
		
		Map<Integer, Integer> nodeToColor = new HashMap<>();
		for(Entry<Integer, List<Integer>> entry : colors.entrySet()) {
			for(Integer node : entry.getValue()) {
				nodeToColor.put(node, entry.getKey());
			}
		}
		
		assertFalse(nodeToColor.get(1).equals(nodeToColor.get(2)));
		assertFalse(nodeToColor.get(1).equals(nodeToColor.get(3)));
		assertFalse(nodeToColor.get(1).equals(nodeToColor.get(4)));
		assertFalse(nodeToColor.get(2).equals(nodeToColor.get(5)));
		assertFalse(nodeToColor.get(3).equals(nodeToColor.get(4)));
		assertFalse(nodeToColor.get(3).equals(nodeToColor.get(6)));
		assertFalse(nodeToColor.get(3).equals(nodeToColor.get(7)));
		assertFalse(nodeToColor.get(4).equals(nodeToColor.get(5)));
		assertFalse(nodeToColor.get(4).equals(nodeToColor.get(6)));
		assertFalse(nodeToColor.get(4).equals(nodeToColor.get(7)));
		assertFalse(nodeToColor.get(5).equals(nodeToColor.get(8)));
		assertFalse(nodeToColor.get(6).equals(nodeToColor.get(7)));
		assertFalse(nodeToColor.get(7).equals(nodeToColor.get(8)));
	}
}
