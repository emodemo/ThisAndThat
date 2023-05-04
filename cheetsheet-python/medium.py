
# on 2D array with 0 and 1 return array of sizes of all connected 1s,
# where connection is up/down left/right, but not diagonal
# [[1,0,0,1,0]
# [1,0,1,1,0]] => [2,3]
# O(rc) time | O(rc) space
def riverSize(matrix):
    size = []
    visited = [[False for value in row] for row in matrix] # 2D with all false
    for i in range(len(matrix)) :
        for j in range(len(matrix[i])) :
            if visited[i][j] : continue
            # TODO
            #dfs or bfs, both works
    return size

# in:  [12, 3, 1, 2, -6, 5, -8, 6], 0
# out: [[-8, 2, 6], [-8, 3, 5], [-6, 1, 5]]
# # O(n^2) time | O(n) space
def threeNumSum(array, targetSum) :
    s_array = sorted(array) # [-8, -6, 1, 2, 3, 5, 6, 12]
    triplets = []
    length = len(s_array)
    for tmp_idx in range(length - 2) :# as we are looking for triplets
        left_idx = tmp_idx + 1     # 2nd num in triplet : from current + 1 
        right_idx = length - 1 # 3rd num in triplet : from end
        while left_idx < right_idx :
            n1, n2, n3 = s_array[tmp_idx], s_array[left_idx], s_array[right_idx] # n1 could be before the while loop
            tmp_sum = n1 + n2 + n3
            if tmp_sum == targetSum : 
                triplets.append([n1, n2, n3])
                left_idx += 1
                right_idx -= 1
            elif tmp_sum < targetSum : left_idx += 1
            elif tmp_sum > targetSum : right_idx -= 1
    return triplets

print(str(threeNumSum([12, 3, 1, 2, -6, 5, -8, 6], 0)))

# [-1, 5, 10, 20, 28, 3], [26, 134, 135, 15, 17] => [28, 26] 
# O(nlog(n) + mlon(m)) time | O(1) space
def smallestDifference(array1, array2):
    s_array1, s_array2 = sorted(array1), sorted(array2) # array1.sort(),
    len_1, len_2 = len(s_array1), len(s_array2)
    idx_1, idx_2 = 0, 0
    smallest, current = float("inf"), float("inf")
    result = []
    while idx_1 < len_1 and idx_2 < len_2:
        first_num = s_array1[idx_1]
        second_num = s_array2[idx_2]
        if first_num < second_num:
            current = second_num - first_num
            idx_1 += 1
        elif second_num < first_num:
            current = first_num - second_num
            idx_2 += 1
        else:
            return [first_num, second_num]

        if smallest > current:
            smallest = current
            result = [first_num, second_num]
    return result

print(str(smallestDifference([-1, 5, 10, 20, 28, 3], [26, 134, 135, 15, 17])))

# O(n) time | O(n) space
def in_order_traverse(tree, array):
    if tree is not None:
        in_order_traverse(tree.left, array)
        array.append(tree.value)
        in_order_traverse(tree.right, array)
    return array

# O(n) time | O(n) space
def pre_order_traverse(tree, array):
    if tree is not None:
        array.append(tree.value)
        pre_order_traverse(tree.left, array)
        pre_order_traverse(tree.right, array)
    return array

# O(n) time | O(n) space
def post_order_traverse(tree, array):
    if tree is not None:
        post_order_traverse(tree.left, array)
        post_order_traverse(tree.right, array)
        array.append(tree.value)
    return array

# O(n) time | O(n) space
def invert_binary_tree_iterative(tree):
    queue = [tree] # root node
    while len(queue):
        current = queue.pop(0)
        if current is None: continue
        swap_left_and_right(current)
        queue.append(current.left)
        queue.append(current.right)

# O(n) time | O(d) space , here d = depth of the tree
def invert_binary_tree_rec(tree):
    if tree is None: return
    swap_left_and_right(tree)
    invert_binary_tree_rec(tree.left)
    invert_binary_tree_rec(tree.right)

def swap_left_and_right(tree):
    tree.left, tree.right = tree.right, tree.left

# [75, 105, 120, 75, 90, 135] =>  330 (75, 120, 135)
# O(n) time | O(n) space
def maxSumNonAdjacent(array):
    if not len(array): return 0
    if len(array) == 1: return array[0]
    max_sums = [0 for i in array]
    max_sums[0] = array[0]
    max_sums[1] = max(array[0], array[1])
    for i in range(2, len(array)):    # start fromm index=2
        max_sums[i] = max(max_sums[i-1], max_sums[i-2] + array[i])
    return max_sums[len(array) - 1]

print(str(maxSumNonAdjacent([75, 105, 120, 75, 90, 135])))

# maximum sum that can be obtained by summing up all the numbers in a non-empty
# subarray of the input array. A subarray must only contain adjacent numbers
# input:  [3, 5, -9, 1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1, -5, 4]
# output: 19 ([1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1])
# O(n) time | O(1) space - where n is the length of the   input array
def kadaneAlgorithm(array):
    tmpsum = array[0]
    maxsum = array[0]
    for i in range(1, len(array)):
        tmpsum = max(array[i], tmpsum + array[i])
        maxsum = max(tmpsum, maxsum)
    return maxsum

print(str(kadaneAlgorithm([3, 5, -9, 1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1, -5, 4])))


class MinMaxStack:
    # O(1) time | O(1) space
    # for all methods

    def __init__(self):
        self.minmaxstack = []
        self.stack = []

    def peek(self):
        return self.stack[len(self.stack) -1]

    def pop(self):
        self.minmaxstack.pop()
        return self.stack.pop()

    def push(self, number):
        minmax = {"min": number, "max": number}
        if len(self.minmaxstack): # if not empty
            lastminmax = self.minmaxstack[len(self.minmaxstack) - 1]
            minmax["min"] = min(lastminmax["min"], number)
            minmax["max"] = max(lastminmax["max"], number)
        self.minmaxstack.append(minmax)
        self.stack.append(number)
    
    def getMin(self):
        return self.minmaxstack[len(self.minmaxstack) - 1]["min"]

    def getMax(self):
        return self.minmaxstack[len(self.minmaxstack) - 1]["max"]


# TODO
# def moveElementToEnd
# def singleCycleCheck
# def BFS
# def youngestCommonAncestor(root, node1, node2):
    # compare node1 and node2, get the lower in hierarchy, then go up until finding one that is higher than the other
