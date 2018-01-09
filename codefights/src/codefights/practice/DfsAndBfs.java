package codefights.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DfsAndBfs {

	private class Tree<T> {
		T value;
		Tree<T> left;
		Tree<T> right;
	}

	int[] traverseTree(Tree<Integer> t) {
		if (t == null)
			return new int[] {};
		List<Integer> result = new ArrayList<>();
		Queue<Tree<Integer>> q = new LinkedList<>();
		q.add(t);
		while (!q.isEmpty()) {
			Tree<Integer> tmp = q.poll();
			result.add(tmp.value);
			if (tmp.left != null)
				q.add(tmp.left);
			if (tmp.right != null)
				q.add(tmp.right);
		}
		int[] i = new int[result.size()];
		int index = 0;
		for (Integer j : result) {
			i[index] = j;
			index++;
		}
		return i;
	}

	int[] largestValuesInTreeRows(Tree<Integer> t) {
		List<Integer> maxes = new ArrayList<>();
		dfs(t, maxes, 0);
		int[] arr = maxes.stream().mapToInt(i -> i).toArray();
		return arr;
	}

	private void dfs(Tree<Integer> node, List<Integer> scale, int level) {
		if (node == null)
			return;
		if (level == scale.size())
			scale.add(node.value);
		else {
			scale.set(level, Math.max(scale.get(level), node.value));
		}
		dfs(node.left, scale, level + 1);
		dfs(node.right, scale, level + 1);
	}

	long digitTreeSum(Tree<Integer> t) {
		List<String> maxes = new ArrayList<>();
		dfs(t, maxes, "0");
		return maxes.stream().map(max -> Long.parseLong(max))
				.reduce(0L, (a, b) -> a + b);
	}

	private void dfs(Tree<Integer> node, List<String> scale, String current) {
		if (node == null)
			return;
		if (node.left == null && node.right == null)
			scale.add(current + node.value);
		else {
			dfs(node.left, scale, current + node.value);
			dfs(node.right, scale, current + node.value);
		}
	}

	// due to platform limitations scenarios were updated with different
	// separators
	// so pay attention to the levels of s.lastIndexOf
	int lengthLongestPath(String fileSystem) {
		// from dev machine use \f instead of \\f
		String[] split = fileSystem.split("\\f");
		System.out.println(java.util.Arrays.toString(split));
		int[] stack = new int[split.length + 1];
		int max = 0;
		for (String s : split) {
			int lev = s.lastIndexOf("\t") + 1;
			stack[lev + 1] = stack[lev] + s.length() - lev + 1;
			int curLen = stack[lev + 1];
			if (s.contains(".")) {
				max = Math.max(max, curLen - 1);
			}
		}
		return max;
	}

	int[] graphDistances(int[][] g, int s) {
		int[] visited = new int[g.length];
		int[] distance = new int[g.length];
		// default values
		java.util.Arrays.fill(visited, 0);
		java.util.Arrays.fill(distance, Integer.MAX_VALUE);
		// bfs // with minPQ based on distances
		List<Integer> queue = new ArrayList<>();
		queue.add(s);
		distance[s] = 0;
		while (!queue.isEmpty()) {
			int i = getMinimum(queue, distance);
			visited[i] = 1;
			for (int j = 0; j < g.length; j++) {
					int weight = g[i][j];
					if (weight == -1)
						continue;
					if (distance[i] + weight < distance[j])
						distance[j] = distance[i] + weight;
					if (visited[j] == 0 && !queue.contains(j))
						queue.add(j);
			}
		}
		// if no connections set to -1
		for (int i : distance) {
			if (i == Integer.MAX_VALUE)
				i = -1;
		}
		return distance;
	}
	// instead of minimum PriorityQueue
	private int getMinimum(List<Integer> queue, int[] distance) {
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < queue.size(); i++) {
			int tmpDist = distance[queue.get(i)];
			if (tmpDist <= min) {
				min = tmpDist;
				minIndex = i;
			}
		}
		Integer result = queue.get(minIndex);
		queue.remove(minIndex);
		return result;
	}

}
