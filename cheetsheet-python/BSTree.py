# bst
class Node:
    left = None
    right = None
    def __init__(self, key, value):
        super().__init__()
        self.key = key
        self.value = value
    
    def __str__(self):
        leftKey = None
        rightKey = None
        if self.left != None : leftKey = self.left.key
        if self.right != None: rightKey = self.right.key
        return str(self.key) + " [" + str(leftKey) + ", " + str(rightKey) + "]"

class BSTree:
    root = None

    def put(self, key, value):
        self.root = self.__put(self.root, key, value)

    def __put(self, node, key, value):
        if node == None: return Node(key, value)
        condition = self.__compare(node.key, key)
        if condition < 0   : node.left  = self.__put(node.left, key, value)
        elif condition > 0 : node.right = self.__put(node.right, key, value)
        else: node.value = value
        return node

    def find(self, key):
        node = self.__find(self.root, key)
        if node == None: return -1 # not good!
        return node.value

    def __find(self, node, key):
        if node == None: return None
        condition = self.__compare(node.key, key)
        if condition == 0: return node
        if condition < 0 : return self.__find(node.left, key)
        return self.__find(node.right, key)

    def delete(self, key):
        self.root = self.__delete(self.root, key)

    # return the node taking the place of the input node
    # if inputNode != node to delete => go deeper
    # else return node will be the replacement one
    # However, this delete impl may add some not-required changes:
    # see codesignal -> interview -> tree - delete from BST: 
    # 3 -r-> 6 -r-> 8 -l-> 7, deleting 6, will select 7 as successor, while 8 is required
    def __delete(self, node, key):
        # 1 - find nodeToDelete
        if node == None: return node
        condition = self.__compare(node.key, key)
        if   condition < 0 : node.left = self.__delete(node.left, key) ## recursive update of parents
        elif condition > 0 : node.right = self.__delete(node.right, key) ## recursive update of parents
        else:
            # if one of children is null, just return the other as the one taking its place
            if node.right == None: return node.left
            if node.left  == None: return node.right
            toDelete = node ## node to delete is found
        ## 2 - successor is first bigger child without left child
            successor = self.__min(toDelete.right)
        ## 3 - delete successor from tree, and set successor.right = toDelete.right
            successor.right = self.__deleteMin(toDelete.right)
            successor.left  = toDelete.left
            node = successor
        return node ## recursive update of parents

    # retruns the node taking the place of min
    def __deleteMin(self, node):
        if node.left == None : return node.right
        node.left = self.__deleteMin(node.left)
        return node

    def min(self):
        node = self.__min(self.root)
        return node.key

    def __min(self, node):
        if node.left == None : return node
        return self.__min(node.left)
           
    def __compare(self, x, y):
        if abs(x - y) < 0.0001: return 0
        elif x > y: return -1
        else : return 1

    def printTree(self):
        queue = [self.root]
        while queue:     
            entry= queue.pop()
            print(str(entry))
            if entry.left  != None: queue.append(entry.left)
            if entry.right != None: queue.append(entry.right)