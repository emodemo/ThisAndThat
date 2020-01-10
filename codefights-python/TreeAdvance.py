# needs to be done with stack instead
# as max depth of recursion is hit
# def treeDiameter2(n, tree):
#     if not tree: return 0
#     adjacent = {}
#     for t in tree:
#         node1, node2 = t[0], t[1]
#         # to become undirected graph
#         if node1 not in adjacent: adjacent[node1] = []
#         if node2 not in adjacent: adjacent[node2] = []
#         adjacent[node1].append(node2)
#         adjacent[node2].append(node1)

#     visited = set()
#     depths = [[tree[0][0], 0]]
#     dfs2(adjacent, visited, tree[0][0], depths, 0)
#     deepest = max(depths, key=lambda item: item[1])

#     visited = set()
#     depths = [[deepest[0], 0]]
#     dfs2(adjacent, visited, deepest[0], depths, 0)
#     return max(depths, key=lambda item: item[1])[1]

# def dfs2(adjacent, visited, node, depths, lvl):
#     visited.add(node)
#     lvl += 1
#     children = adjacent.get(node)
#     if children is not None:
#         for child in children:
#             if child not in visited:
#                 depths.append([child, lvl])
#                 dfs(adjacent, visited, child, depths, lvl)

def treeDiameter(n, tree):
    if not tree: return 0
    adjacent = {}
    for t in tree:
        node1, node2 = t[0], t[1]
        # to become undirected graph
        if node1 not in adjacent: adjacent[node1] = []
        if node2 not in adjacent: adjacent[node2] = []
        adjacent[node1].append(node2)
        adjacent[node2].append(node1)

    def dfs(node):
        visited = set([node])
        maxDepth = (node, 0)
        stack = [(node, 0)]
        while stack:
            n, d = stack.pop()
            # if new depth is deeper
            if d > maxDepth[1]: maxDepth = (n, d)
            #  now go for children
            for child in adjacent[n]:
                if child in visited: continue
                stack.append((child, d+1))
                visited.add(child)
        return maxDepth
    
    deepest = dfs(tree[0][0])
    return dfs(deepest[0])[1]
    
tree = [[2, 5], [5, 7], [5, 1], [1, 9], [1, 0], [7, 6], [6, 3], [3, 8], [8, 4]]
print(str(treeDiameter(10, tree))) # 7


class Tree(object):
  def __init__(self, x):
    self.value = x
    self.left = None
    self.right = None

def mostFrequentSum(t):
    if t is None: return []
    sums = {} # map key - sum, value - frequency
    dfs(t, sums)
    sums_sorted = sorted(sums.items(), key=lambda kv:(kv[1], -kv[0]), reverse=True)
    result = []
    max = sums_sorted[0][1]
    idx = 0
    for sum in sums_sorted:
        if sum[1] == max:
            result.append(sum[0])
            idx += 1
        else: break
    return result

def dfs(node, sums):
    sum = node.value
    if node.left is not None:
        sum += dfs(node.left, sums)
    if node.right is not None:
        sum += dfs(node.right, sums)
    value = sums.get(sum, 0)
    value += 1
    sums.update({(sum, value)})
    return sum

# t1 = Tree(1); t2 = Tree(2); t3 = Tree(3)
# t1.left = t2; t1.right = t3
# t4 = Tree(-2); t5 = Tree(-3); t6 = Tree(2)
# t4.left = t5; t4.right = t6
# print(str(mostFrequentSum(t1)))
# print(str(mostFrequentSum(t4)))

import sys
def networkWires(n, wires):
    adjacent = {} # node1 -> ([node2, weight])
    for wire in wires:
        node1, node2, weight = wire[0], wire[1], wire[2]
        # directed
        list1 = adjacent.get(node1, list())
        list1.append([node2, weight])
        adjacent[node1] = list1
        # to become undirected
        list2 = adjacent.get(node2, list())
        list2.append([node1, weight])
        adjacent[node2] = list2
        
    visited = []
    result = []

    # minimum spanning forest
    for node in adjacent:
       if node in visited: continue
       mst(node, adjacent, visited, result)

    return sum(result)

def mst(node, adjacent, visited, result):

    eligibles = {}
    visited.append(node) # current root is visited
    for edge in adjacent[node]:
        adjNode, weight = edge[0], edge[1]
        eligibles.update({(adjNode, weight)}) # get children

    while eligibles:
        closest = min(eligibles.items(), key=lambda kv:(kv[1]))
        eligibles.pop(closest[0]) # the node
        visited.append(closest[0]) # the node
        result.append(closest[1]) # add the weights only, but if parents are needed too, add the whole "closest" edge

        # go through children
        for edge in adjacent[closest[0]]:
            adjNode, weight = edge[0], edge[1]
            if adjNode in visited: continue
            currentBestWeight = eligibles.get(adjNode, sys.maxsize)
            if currentBestWeight > weight:
                eligibles.update({(adjNode, weight)})
    
# wires = [[0, 1, 1], [2, 0, 2], [1, 2, 3], [3, 4, 1],
#          [4, 5, 2], [5, 6, 3], [6, 3, 2]]
# print(str(networkWires(7, wires)))