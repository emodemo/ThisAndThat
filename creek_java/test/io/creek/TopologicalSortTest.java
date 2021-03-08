package io.creek;

import static io.creek.UtilCompare.compare;
import static io.creek.UtilGraph.loadDataInGraph;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.creek.Graph.GraphType;

class TopologicalSortTest {
	
	@Test
	void courses() {
		Graph<String> graph = Graph.createGraph(GraphType.DIRECTED);
		loadDataInGraph("topological_courses.txt", " / ", graph, (s -> s));
		List<String> sort = TopologicalSort.sort(graph);
		String[] expected = {"Databases", "Robotics", "Neural Networks", "Machine Learning", "Computational Biology", "Scientific Computing", "Artificial Intelligence", "Theoretical CS", "Linear Algebra", "Calculus", "Algorithms", "Advanced Programming", "Introduction to CS"};
		assertTrue(compare(sort, asList(expected)));
	}
	
	@Test
	void buildCycle() {
		Graph<String> graph = Graph.createGraph(GraphType.DIRECTED);
		loadDataInGraph("topological_tasks.txt", " ", graph, (s -> s));
		List<String> sort = TopologicalSort.sort(graph);
		String[] expected = {"clean", "lint", "build", "test", "deploy"};
		assertTrue(compare(sort, asList(expected)));
	}

}
