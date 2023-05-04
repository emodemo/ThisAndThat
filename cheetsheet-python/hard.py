# find the min and max out of order, then find where to put them
# O(n) time | O(1) space
def subarraySort(array):
    minOutOfOrder = float('inf')
    maxOutOfOrder = float('-inf')
    for i in range(len(array)):
        num = array[i]
        if isOutOfOrder(i, num, array):
            minOutOfOrder = min(num, minOutOfOrder)
            maxOutOfOrder = max(num, maxOutOfOrder)
    if minOutOfOrder == float('inf'):
        return [-1, -1]
    subArrayLeftIdx = 0
    subArrayRightIdx = len(array) - 1
    while minOutOfOrder >= array[subArrayLeftIdx]:
        subArrayLeftIdx += 1
    while maxOutOfOrder <= array[subArrayRightIdx]:
        subArrayRightIdx -= 1
    return [subArrayLeftIdx, subArrayRightIdx]

def isOutOfOrder(i, num, array):
    if i == 0:
        return num > array[i + 1]
    if i == len(array) - 1:
        return num < array[i - 1]
    return num > array[i + 1] or num < array[i - 1]

print(str(subarraySort([1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19]))) # [3,9]

# rule 1 : each must have at least 1 reward
# rule 2 : each must have more rewards than adjacent with less points
# rule 3 : each must have less rewards than adjacent with more points
# O(n) time | O(n) space
def minRewards(score):
    rewards = [1 for _ in score]
    for i in range(1, len(score)):
        if score[i] > score[i - 1]:
            rewards[i] = rewards[i - 1] + 1
    for i in reversed((range(len(score) - 1))):
        if score[i] > score[i + 1]:
            rewards[i] = max(rewards[i], rewards[i + 1] + 1)
    return sum(rewards)

#print(str(minRewards([8, 4, 2, 1, 3, 6, 7, 9, 5]))) # 25

def maxSumIncreasingSubsequence(array):
    sequences = [None for x in array]
    sums = [num for num in array]
    maxSumIdx = 0
    for i in range(len(array)):
        currentNum = array[i]
        for j in range(0, i):
            otherNum = array[j]
            if otherNum < currentNum and sums[j] + currentNum >= sums[i]:
                sums[i] = sums[j] + currentNum
                sequences[i] = j
        if sums[i] >= sums[maxSumIdx]:
            maxSumIdx = i
    return [sums[maxSumIdx], buildSequence(array, sequences, maxSumIdx)]


def buildSequence(array, sequences, currentIdx):
    sequence = []
    while currentIdx is not None:
        sequence.append(array[currentIdx])
        currentIdx = sequences[currentIdx]
    return list(reversed(sequence))

maxSumIncreasingSubsequence([10, 70, 20, 30, 50, 11, 30]) #110

class LLNode:
    def __init__(self, name):
        self.next = None
        self.name = name

# O(n) time | O(1) space
def reverseLinkedList(head):
    p1, p2 = None, head
    while p2 is not None:
        p3 = p2.next
        p2.next = p1
        p1 = p2
        p2 = p3
    return p1

l1,l2,l3,l4 = LLNode("l1"), LLNode("l2"), LLNode("l3"), LLNode("l4")
l1.next = l2; l2.next = l3; l3.next = l4
reverseLinkedList(l1)

# O(logn) time |  O(1) space
def shiftedBinarySearch(array, target):
    return shiftedBinarySearchHelper(array, target, 0, len(array) - 1)

def shiftedBinarySearchHelper(array, target, left, right):
    while left <= right:
        middle = (left + right) // 2
        middleValue = array[middle]
        leftNum = array[left]
        rightNum = array[right]
        if middleValue == target:
            return middle
        elif leftNum <= middleValue:
            if target < middleValue and target >= leftNum:
                right = middle - 1
            else:
                left = middle + 1
        else:
            if target > middleValue and target <= rightNum:
                left = middle + 1
            else:
                right = middle - 1
    return -1

# Solution #2   -   Iterative SOlution
# O(logn) time  |  O(1) space
def searchForRange(array,  target):
    finalRange = [-1, -1]
    alteredBinarySearch(array, target, 0, len(array) - 1, finalRange, True)
    alteredBinarySearch(array, target, 0, len(array) - 1, finalRange, False)
    return finalRange


def alteredBinarySearch(array, target, left, right, finalRange, goLeft):
    while left <= right:
        mid = (left + right) // 2
        if array[mid] < target:
            left = mid + 1
        elif array[mid] > target:
            right = mid - 1
        else:
            if goLeft:
                if mid == 0 or array[mid - 1] != target:
                    finalRange[0] = mid
                    break
                else:
                    right = mid - 1
            else:
                if mid == len(array) - 1 or array[mid + 1] != target:
                    finalRange[1] = mid
                    break
                else:
                    left = mid + 1


print(str(searchForRange([0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73], 45)))
