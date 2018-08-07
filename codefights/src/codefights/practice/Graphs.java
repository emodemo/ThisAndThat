package codefights.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Graphs {

	class Node{
		String name;
		int start, end;
		List<Node> adjacents = new ArrayList<>();
		
		public Node(String name, int start, int end) {
			this.name = name;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + end;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + start;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Node other = (Node) obj;
			if (end != other.end) return false;
			if (name == null) {
				if (other.name != null)	return false;
			} else if (!name.equals(other.name)) return false;
			if (start != other.start) return false;
			return true;
		}

		@Override
		public String toString() {
			return name + " " + start + " " + end + " " + adjacents.size();
		}

	}
	
	
	private class NodeComaprator implements Comparator<Node>{
		@Override
		public int compare(Node node1, Node node2) {
			return Integer.compare(node1.end, node2.end);
		}
	}
	
	private class G {
		int vertexN;
		List<Edge> edges = new ArrayList<>();
	}
	
	private class Edge {
		int from, to;
		double weight;
		public Edge(int from, int to, double weight) {
			this.from = from; this.to = to; this.weight = weight;
		}
	}
	
	boolean currencyArbitrage(double[][] exchange) {
		G g = new G();
		for(int i = 0; i < exchange.length; i++) {
			for(int j = 0; j < exchange.length; j++) {
				if(i == j) continue;
				// negative log of each weight
				Edge edge = new Edge(i, j, -Math.log(exchange[i][j]));
				g.edges.add(edge);
			}
		}
		g.vertexN = exchange.length;
		
		// bellman ford
		boolean bellmanFord = bellmanFord(g, 0);
		return bellmanFord;
	}
	
	private boolean bellmanFord(G g, int source) {
		double[] distFromStart = new double[g.vertexN];
		Arrays.fill(distFromStart, Double.POSITIVE_INFINITY);
		int[] previousNodeOnPath = new int[g.vertexN];
		Arrays.fill(previousNodeOnPath, 0);
		// mark the src node
		distFromStart[source] = 0;
		
		//relax edges |V| - 1 times
		for(int i = 0; i < g.vertexN -1; i++) {
			for(Edge e : g.edges) {
				if(distFromStart[e.to] > distFromStart[e.from] + e.weight) {
					distFromStart[e.to] = distFromStart[e.from] + e.weight;
					previousNodeOnPath[e.to] = e.from;
				}
			}
		}
		// relax edges one last time to detect cycle
		for(Edge e : g.edges) {
			if(distFromStart[e.to] > distFromStart[e.from] + e.weight) {
				return true;
			}
		}
		return false;
	}
	
	String flightPlan(String[][] times, String source, String dest) {
		List<Node> graph = new ArrayList<>();
		for(String[] flight : times) {
			Node node = new Node(flight[0], 0, 0);
			int index = graph.indexOf(node);
			if(index != -1) node = graph.get(index);
			else graph.add(node);
			// +100 accounts for 1h btwn previous end and current start
			int start = Integer.parseInt(flight[2].replace(":", ""));
			int end = Integer.parseInt(flight[3].replace(":", "")) + 100;
			node.adjacents.add(new Node(flight[1], start, end));
		}
		
		PriorityQueue<Node> minBfsPQ = new PriorityQueue<>(new NodeComaprator());
		// add the source
		for(Node node : graph) {
			if(node.name.equals(source)) minBfsPQ.add(node);
		}
		// if no source return -1
		if(minBfsPQ.isEmpty()) return "-1";
		// bfs
		int minTime = Integer.MAX_VALUE;
		while(minBfsPQ.isEmpty() == false) {
			Node poll = minBfsPQ.poll();
			for(Node adj : poll.adjacents) {
				// if already have better time skip it
				if(adj.end - 100 > minTime) continue;
				// if cannot take it, skip it
				if(poll.end > adj.start) continue;
				// if destination
				if(adj.name.equals(dest)) {
					if(minTime > adj.end - 100) minTime = adj.end - 100;
					continue;
				}
				// if not the destination add to queue
				for(Node node : graph) {
					if(node.name.equals(adj.name)) {
						adj.adjacents = node.adjacents;
						minBfsPQ.add(adj);
						break;
					}
				}
			}
		}
		
		if(minTime == Integer.MAX_VALUE) return "-1";
		String time = Integer.toString(minTime);
		String hours = time.substring(0, time.length() - 2);
		String mins = time.substring(time.length() - 2, time.length());
		if(hours.length() == 1) hours = "0" + hours;
		return hours + ":" + mins;
	}
	
	
	int feedingTime(String[][] classes) {
		//graph with nodes days and edges in case of same animal	
		int[][] nodes = new int[classes.length][classes.length];
		for(int[] node : nodes){
			Arrays.fill(node, 0);
		}
		// fill in the graph
		for(int i = 0; i < classes.length; i++){
			for(int j = i + 1; j < classes.length; j++){
				if(compareArrays(classes[i], classes[j])){
					nodes[i][j] = 1;
				}
			}
		}
		// set the colors
		int max = 0; // the max ID of color
		int[] colors = new int[nodes.length];
		for(int row = 0; row < nodes.length; row++){
			for(int col = row + 1; col < nodes.length; col++){
				if(nodes[row][col] == 1 && colors[row] == colors[col]){
					colors[col] ++;
					if(max < colors[col]){
						max = colors[col];
					}
				}
			}
		}
		return (max < 5) ? max + 1 : -1;
	}
	
	private boolean compareArrays(String[] first, String[] second){
		for(String s1 : first) {
			for(String s2 : second) {
				if(s1.equals(s2)) return true;
			}
		}
		return false;
	}
	
	
	int singlePointOfFailure(int[][] connections) {
		if (connections == null || connections.length == 0) return 0;
		
		// prepare the data. the step an be omitted, but the dfs should be updated
		// for larger structures => remove this step
		int[][] c = new int [connections.length][connections.length];
		int rowIndex = 0;
		int colIndex = 0;
		for(int row = 0; row < connections.length; row ++){
			for(int col = 0; col < connections.length; col++){
				if(connections[row][col] == 1){
					c[rowIndex][colIndex] = col;
					colIndex++;
				}
			}
			for(int col = colIndex; col < connections.length; col++){
				c[rowIndex][colIndex] = -1;
				colIndex++;
			}
			colIndex = 0;
			rowIndex ++;
		}
		
		// tmp structures
		int[] d = new int[c.length]; // visiting order
		int[] low = new int[c.length]; // lowest visited connected node
		Arrays.fill(d, -1);
		Arrays.fill(low, -1);
		int[] time = {1};
		
		// run the algorithm
		for(int i = 0; i < c.length; i++){
			if(d[i] == -1){
				dfs(c, i, i, time, d, low);
			}
		}
		int bridges = 0;
		// skip the 1st one as low[d] = d[d] always
		for(int i = 1; i < c.length; i++){
			if(low[i] == d[i]) bridges++;
		}
		return bridges;
	}
	
	private void dfs(int[][] connections, int parent, int node, int[] time, int[] d, int[] low){
		d[node] = low[node] = time[0];
		time[0] = time[0] + 1;
		for(int i = 0; i < connections[node].length; i++){
			int child = connections[node][i];
			if(child == -1) continue;
			if(d[child] == -1){ // not visited
				dfs(connections, node, child, time, d, low);
				low[node] = Math.min(low[node], low[child]); // tree edge
			}
			if(child != parent){
				low[node] = Math.min(low[node], d[child]); // back edge (ancestor to descendant, but nor parent child)
			}
		}
	}

	// cycle detection 1) dfs - visited 2) see current path for repeated nodes - onStack 
	boolean hasDeadlock(int[][] connections) {
		if (connections == null || connections.length == 0) return false;
		boolean[] visited = new boolean[connections.length];
		boolean[] hasCycle = new boolean[1];
		for(int i = 0; i < connections.length; i++){
			boolean[] onStack = new boolean[connections.length];
			if(visited[i] == false){
				dfs(connections, visited, i, onStack, hasCycle);
				if(hasCycle[0]) return true;
			}
		}
		return false;
	}
	
	private void dfs(int[][] connections, boolean[] visited, int node,  boolean[] onStack, boolean[] hasCycle){
		if(hasCycle[0]) return;
		visited[node] = true;
		onStack[node] = true;
		for(int i = 0; i < connections[node].length; i++){
			if(visited[connections[node][i]] == false)
				dfs(connections, visited, connections[node][i], onStack, hasCycle);
			else if(onStack[connections[node][i]]){
				hasCycle[0] = true;
			}
			onStack[connections[node][i]] = false;
		}
	}
	
}
