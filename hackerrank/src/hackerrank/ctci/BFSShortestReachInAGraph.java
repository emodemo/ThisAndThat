package hackerrank.ctci;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach">https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach</a>
 * </br>
 * <b>Sample Input:</b> </br>
 * 2 </br>
 * 4 2 </br>
 * 1 2 </br>
 * 1 3 </br>
 * 1 </br>
 * 3 1 </br>
 * 2 3 </br>
 * 2 </br>
 * <b>Sample Output:</b> </br>
 * 6 6 -1 </br>
 * -1 6 </br>
 * 
 * @author emo
 *
 */
public class BFSShortestReachInAGraph {

	private static final int DISTANCE_UNIT = 6;

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int q = in.nextInt();
		for (int i = 0; i < q; i++) {
			int n = in.nextInt();
			GraphNode[] nodes = new GraphNode[n];
			boolean[] visited = new boolean[n];
			for (int j = 0; j < nodes.length; j++) {
				nodes[j] = new GraphNode(j);
				visited[j] = false;
			}

			int m = in.nextInt();
			for (int k = 0; k < m; k++) {
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				nodes[u].adjacents.add(nodes[v]);
				nodes[v].adjacents.add(nodes[u]);
			}

			int searchNodeIndex = in.nextInt() - 1;
			;
			bfs(nodes, searchNodeIndex, visited);

			System.out
					.println(String.join(" ", IntStream.range(0, nodes.length).filter(index -> index != searchNodeIndex)
							.mapToObj(index -> String.valueOf(nodes[index].distance)).collect(Collectors.toList())));
		}

		in.close();
	}

	public static void bfs(GraphNode[] nodes, int searchNodeIndex, boolean[] visited) {
		Queue<GraphNode> queue = new LinkedList<>();
		GraphNode searchNode = nodes[searchNodeIndex];
		searchNode.distance = 0;
		visited[searchNode.id] = true;
		queue.offer(searchNode);
		while (!queue.isEmpty()) {
			GraphNode currentNode = queue.poll();
			for (GraphNode node : currentNode.adjacents) {
				if (!visited[node.id]) {
					queue.offer(node);
					// to prevent cycles
					visited[node.id] = true;
					node.distance = DISTANCE_UNIT + currentNode.distance;
				}
			}
		}
	}
}

class GraphNode {
	public int id;
	public int distance = -1;
	public List<GraphNode> adjacents = new ArrayList<>();

	public GraphNode(int id) {
		this.id = id;
	}
}