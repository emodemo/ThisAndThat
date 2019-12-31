# binary search
def binarySearch(array, value):
    sort = sorted(array)
    min = 0
    max = len(sort) - 1
    while(min <= max):
        mid = (int) (min + (max - min) / 2)
        midValue = sort[mid]
        # equals
        if abs(midValue - value) < 0.0001 : return True
        if   midValue < value: min = mid + 1
        elif midValue > value: max = mid - 1
    return False

# merge sort
# uses two pointers: start1 and mid, compare them and shift+1 the smaller
def mergeSort(array):
    aux = array
    __sort(array, aux, 0, len(array)-1)
    return array

def __merge(array, aux, lo, mid, hi):
    for k in range(lo,hi+1): # copy a[lo..hi] to aux[li..hi]
        aux[k] = array[k]
    i = lo; j = mid+1        # get 2 start pointers to compare
    for k in range(lo,hi+1): # merge back to a[lo..hi]
        if   i > mid:         array[k] = aux[j]; j+=1 # left half is done, use the right only
        elif j > hi:          array[k] = aux[i]; i+=1 # right half is done, use the left only
        elif aux[i] > aux[j]: array[k] = aux[j]; j+=1 # right one is smaller, use it
        else                : array[k] = aux[i]; i+=1 # left one is smaller, use it

def __sort(array, aux, lo, hi):
    if hi <= lo: return
    mid = (int) (lo + ((hi-lo)/2))
    __sort(array, aux, lo, mid)
    __sort(array, aux, mid+1, hi)
    __merge(array, aux, lo, mid, hi)

# graph algorithms
class Vertex:
    children = []
    def __init__(self, key):
        super().__init__()
        self.key = key
    # def __str__(self):
    #     s = str(key) + ": ["
    #     for c in children: s += str(c.key) + " "
    #     s += "]"
    # def __repr__(self):
    #     return self.__str__

class Edge:
    def __init__(self, v1, v2, weight):
        super().__init__()
        self.v1 = v1
        self.v2 = v2
        self.weight = weight

# topological sort = dfs + has cycle
def topologicalSort(vertices):
    visited = []
    result = []
    hasCycle = False
    for v in vertices:
        if v.key not in visited:
            hasCycle = __dfs(v, visited, result, [])
            if hasCycle : return [-1]
    return result

# onStack detects for cycles, by caching nodes in the current path
def __dfs(vertex, visited, result, onStack):
    visited.append(vertex.key)
    onStack.append(vertex.key)
    for child in vertex.children:
        if child.key in onStack: return True
        if child.key not in visited:
            cycle = __dfs(child, visited, result, onStack)
            if cycle: return True
    onStack.remove(vertex.key)
    result.insert(0, vertex.key) # reverse postorder
    return False

# v0 = Vertex(0); v1 = Vertex(1); v2 = Vertex(2); v3 = Vertex(3); v4 = Vertex(4) ; v5 = Vertex(5)
# v6 = Vertex(6); v7 = Vertex(7); v8 = Vertex(8); v9 = Vertex(9); v10 = Vertex(10); v11 = Vertex(11); v12 = Vertex(12)
# v0.children = [v1, v5, v6]; v2.children = [v0, v3]; v3.children = [v5]; v5.children = [v4]; v6.children = [v4, v9]
# v7.children = [v6]; v8.children = [v7]; v9.children = [v10, v11, v12]; v11.children = [v12]
# a = topologicalSort(list([v0,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12]))
# print(str(a))
# v4.children = [v3] # introduce cycle
# a = topologicalSort(list([v0,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12]))
# print(str(a))

#import queue
#q = queue.Queue()

v0 = Vertex(0); v1 = Vertex(1); v2 = Vertex(2); v3 = Vertex(3)
v4 = Vertex(4); v5 = Vertex(5); v6 = Vertex(6); v7 = Vertex(7)
e1 = Edge(v4, v5, 0.35); e2 = Edge(v4, v7, 0.37); e3 = Edge(v5, v7, 0.28); e4 = Edge(v0, v7, 0.16)
e5 = Edge(v1, v5, 0.32); e6 = Edge(v1, v4, 0.38); e7 = Edge(v2, v3, 0.17); e8 = Edge(v1, v7, 0.19)
e9 = Edge(v0, v2, 0.26); e10 = Edge(v1, v2, 0.36); e11 = Edge(v1, v3, 0.29); e12 = Edge(v2, v7, 0.34)
e13 = Edge(v6, v2, 0.40); e14 = Edge(v3, v6, 0.52); e15 = Edge(v6, v0, 0.58); e16 = Edge(v6, v4, 0.93)
#0-7 0.16 1-7 0.19 0-2 0.26 2-3 0.17 5-7 0.28 4-5 0.35 6-2 0.40 1.81
vertices = [v0,v1,v2,v3,v4,v5,v6,v7]
edges = [e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15,e16]


# pp 967 sedgewick
# prefix sum (see arrayManipulation)
# dynamic rogramming, quick sort
# B+Tree,, segment tree
# two pointer : start/start fast/slow, start/end (Sotring.sortedSquaredArray)
# lca, inorder, reverse in-order, preorder
# isBST
# challanges - christmas toys
# graph coloring
# metric tree, rtree, quadtree