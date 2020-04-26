# O(n^2) time | O(n) space | recursive
def fibonacci_v1(n):
    if n == 1 : return 0
    elif n == 2 : return 1
    else : return fibonacci_v1(n-1) + fibonacci_v1(n-2)

# O(n) time | O(1) space | iterative + memo
def fibonacci_v2(n):
    last_two = [0, 1]
    index = 3
    while index <= n:
        fibo = last_two[0] - last_two[1]
        last_two[0] = last_two[1]
        last_two[1] = fibo
        index += 1
    return last_two[1] if n > 1 else last_two[0] # could be also init check

# any two numbers in array that sum up to targetSum
# O(nlogn) time | 0(1) space
def two_num_sum(array, targetSum):
    sort = sorted(array) # O(nlogn) time
    left = 0 # left pointer
    right = len(array) - 1 # right pointer
    while left < right :
        tmp_sum = sort(left) + sort(right)
        if tmp_sum == targetSum : return [sort[left], sort[right]]
        elif tmp_sum < targetSum : left += 1
        else : right -= 1
    return []

# assume only 1 possible answer
# Avarage: O(log(n)) time | O(1) space
# Worst: O(n) time | O(1) space
def findClosestInBST(root, target):
    tmp_node = root
    closest = int("inf")
    while tmp_node is not None:
        if abs(target - closest) > abs (target - tmp_node.value) : closest = tmp_node.value
        if target < tmp_node.value : tmp_node = tmp_node.left
        elif target > tmp_node.value : tmp_node = tmp_node.right
        else : break
    return closest

# could be done with recursion too
def dfs(root):
    stack = []
    stack.append(root)
    result = []
    while stack :
        node = stack.pop()
        result.append(node.name)
        for child in root.children :
            stack.append(child)
    return result
# Definition for doubly-linked list node.
class ListNode(object):
    def __init__(self, x):
        self.val = x
        self.next = None
        self.prev = None

class DoubleLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None

    def setHead(self, node):
        if self.head is None :
            self.head = node
            self.tail = node
        else : 
            self.insertBefore(node, self.head)

    def setTail(self, node):
        if self.tail is None : self.setHead(node)
        else : self.insertAfter(node, self.tail)

    # 0,1,2,4,5, insert 3 before 4
    def insertBefore(self, node, before):
        if node == self.head and node == self.tail : return # sanity
        # self.remove(node) # sanity in case it exists
        node.prev = before.prev # 3.prev = 2
        node.next = before      # 3.next = 4
        if before.prev is None : self.head = node
        else : node.prev.next = node # 2.next = 3
        before.prev = node      # 4.prev = 3

    # 0,1,2,4,5, insert 3 after 2
    def insertAfter(self, node, after):
        if node == self.head and node == self.tail : return # sanity
        # self.remove(node) # sanity in case it exists
        node.prev = after       # 3.prev = 2
        node.next = after.next  # 3.next = 4
        if after.next is None : self.tail = node
        else : node.next.prev = node # 4.prev = 3
        node.prev.next = node   # 2.next = 3

    # 0,1,2,4,5 insert 3 at 4th postion (1-based)
    def insertAt(self, node, index):
        if index == 1 : 
            self.setHead(node)
            return
        tmp_node = self.head
        tmp_index = 1
        while tmp_node is not None and tmp_index != index :
            tmp_node = tmp_node.next
            tmp_index += 1
        if tmp_node is not None : self.insertBefore(node, tmp_node)
        else : self.setTail(node)

    def remove(self, node):
        if node == self.head : self.head = self.head.next
        if node == self.tail : self.tail = self.tail.prev
        else:
            if node.prev is not None : node.prev.next = node.next
            if node.next is not None : node.next.prev = node.prev
            node.next = None
            node.prev = None

    def removeNodeWithValue(self, value):
        tmp_node = self.head
        while tmp_node is not None :
            if tmp_node.value == value : 
                self.remove(tmpNode)
                tmpNode = None
            else :
                tmp_node = tmp_node.next
        
    # return whether it is found or not
    def findNode(self, value):
        tmp_node = self.head
        while tmp_node is not None and tmp_node.value != value :
            tmp_node = tmp_node.next
        return tmp_node is not None        

# The product sum of a "special" array is the sum of its elements, where "special" arrays inside it 
# should be summed themselves and then multiplied by their level of depth.
# example: the product sum of [x, y] is x + y; the product sum of [x, [y, z]] is x + 2y + 2z.
def productSum(array, level = 1):
    tmp_sum = 0
    for element in array:
        if type(element) is list :
            tmp_sum += productSum(element, level + 1)
        else : tmp_sum += element
    return tmp_sum * multiplier

def binarySearch(array, value):
    min = 0
    max = len(array) - 1
    while min <= max :
        mid = (int) (min + (max - min) / 2)
        midValue = array[mid]
        if midValue == value : return mid
        if midValue < value : min = mid + 1
        elif midValue > value : max = mid - 1

# O(n) time | O(1) space
def palindromeCheck(word):
    left = 0 # left pointer
    right = len(word) - 1
    while left < right :
        if word[left] != word[right] : return false
        left += 1
        right -= 1
    return true

# shift each letter by key positions in the alphabet
# abz 2 = cdb
def caesarCipher(word, key):
    new_word = []
    shift_key = key % 26
    for letter in word :
        new_word.append(newLetter(letter, shift_key))
    return new_word
    
def newLetter(letter, shift_key):
    new_key = ord(letter) + shift_key # the index of the letter + shiftkey
    return chr(new_key) if new_key <= 122 else chr(96 + new_key % 122)
# print(str(caesarCipher("abz", 2))) 

# TODO
# def branchSums
# def bubbleSort
# def InsertSort
# def SelectionSort