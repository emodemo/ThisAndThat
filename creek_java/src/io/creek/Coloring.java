package io.creek;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Coloring {

	/** time N^2, @return color to nodes map */
	public static <T> Map<Integer, List<T>> greedy(Graph<T> graph){
		Map<T, Integer> nodeToColor = new HashMap<>();
		Map<Integer, List<T>> colorToNodes = new HashMap<>();
		
		List<T> nodes = graph.getNodes();
		// start with the 1st available node, but in the future use a seed
		nodeToColor.put(nodes.get(0), 0);
		List<T> list = colorToNodes.getOrDefault(0, new ArrayList<>());
		list.add(nodes.get(0));
		colorToNodes.put(0, list);

		// go trough remaining nodes, check their color suitability, check if already colored
		for(int i = 1; i < nodes.size(); i++) {
			T node = nodes.get(i);
			if(nodeToColor.containsKey(node)) continue;
			Integer minColor = minAvailableColor(graph, node, nodeToColor);
			nodeToColor.put(node, minColor);
			// only used for returning in (color -> nodes), otherwise the (node -> color) could be returned
			List<T> colorToNodeList = colorToNodes.getOrDefault(minColor, new ArrayList<>());
			colorToNodeList.add(node);
			colorToNodes.put(minColor, colorToNodeList);
		}
		
		return colorToNodes;
	}
	
	// 1st criterion - max saturation degree, 0 at the beginning
	// 2nd criterion - max N of degree
	// 3rd criterion - random
	/** time N^2, @return color to nodes map */
	public static <T> Map<Integer, List<T>> degreeOfSaturation(Graph<T> graph){
		// TODO: only one of these two maps is needed. it is a matter of API
		Map<T, Integer> nodeToColor = new HashMap<>();
		Map<Integer, List<T>> colorToNodes = new HashMap<>();
				
		//at the beginning the saturation is 0 
		Map<T, Tuple> sorted = new HashMap<>();		
		for(T node : graph.getNodes()){
			sorted.put(node, new Tuple(graph.getAdjacentNodes(node).size(), 0));
		}

		Comparator<Tuple> saturation = comparing(Tuple::getSaturation);
		Comparator<Tuple> degree = comparing(Tuple::getDegree);
		
		while(sorted.isEmpty() == false) {
			// choose a node respecting algorithm order: saturation, degree, reversed
			T maxNode = findBy(sorted, Entry.comparingByValue(saturation.thenComparing(degree).reversed())).getKey();
			sorted.remove(maxNode);			
			// set the color in the 1st map
			Integer minColor = minAvailableColor(graph, maxNode, nodeToColor);
			nodeToColor.put(maxNode, minColor);
			// set the color in the 2nd map
			List<T> colorToNodeList = colorToNodes.getOrDefault(minColor, new ArrayList<>());
			colorToNodeList.add(maxNode);
			colorToNodes.put(minColor, colorToNodeList);
			
			// update unvisited neighbours
			for(T adjacent : graph.getAdjacentNodes(maxNode)) {
				Tuple tuple = sorted.get(adjacent);
				if(tuple == null) continue;
				sorted.put(adjacent, new Tuple(tuple.degree, tuple.saturation + 1));
			}
		}
		return colorToNodes;
	}
	
	private static <T> Integer minAvailableColor(Graph<T> graph, T node, Map<T, Integer> nodeToColor) {
		List<Integer> coloredAdjacents = new ArrayList<>();
		// get colors of all adjacent
		for(T adjacent : graph.getAdjacentNodes(node)) {
			int color = nodeToColor.getOrDefault(adjacent, -1);
			if(color > -1) coloredAdjacents.add(color);
		}
		int minColor = 0;
		coloredAdjacents.sort(Integer::compare);
		for(Integer color : coloredAdjacents) {
			if(minColor >= color) minColor = color + 1;
			else if( minColor < color) return minColor;
		}
		return minColor;
	}
	
	private static <T> Map.Entry<T, Tuple> findBy(Map<T, Tuple> map, Comparator<Map.Entry<T, Tuple>> comparator){
		Map.Entry<T, Tuple> result = null;
		for(Map.Entry<T, Tuple> entry : map.entrySet()) {
			if(result == null || comparator.compare(entry, result) == -1) {
				result = entry;
			}
		}
		return result;
	}
	
	private static class Tuple{
		private int degree, saturation;
		private Tuple(int degree, int saturation) {
			super();
			this.degree = degree;
			this.saturation = saturation;
		}
		public int getDegree() {
			return degree;
		}
		public int getSaturation() {
			return saturation;
		}
	}
}
