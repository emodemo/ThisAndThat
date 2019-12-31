## balanced bst
class Node:
    left = None
    right = None
    isRed = True
    # red means node is left to parent, else right, but left to parent is not always red!!
    # referring to the node's color means referring to the link pointing to it
    def __init__(self, key, value):
        super().__init__()
        self.key = key
        self.value = value
    
    def __str__(self):
        leftKey = None
        rightKey = None
        if self.left != None : leftKey = self.left.key
        if self.right != None: rightKey = self.right.key
        return str(self.key) + " [" + str(leftKey) + ", " + str(rightKey) + "] " + str(self.isRed)

class RedBlackTree:
    root = None

    def put(self, key, value):
        self.root = self.__put(self.root, key, value)

    def find(self, key):
        # same as bst
        pass

    def delete(self, key):
        # TODO
        pass

    # from a:[x<a, b]      b:[a<x<b, x>b], where a->b is red
    # to   a:[x<a, a<x<b]  b:[a, x>b]    , where b->a is red
    # input node is a, output is the that take the place of a (e.g. b)
    def __rotateLeft(self, a):
        # rotate
        b = a.right
        a.right = b.left
        b.left = a
        # adjust colors
        b.isRed = a.isRed # b takes the place of a => takes its color
        a.isRed = True # a is left child => is red
        return b ## return the node a has switched place with

    # from   a:[x<a, a<x<b]  b:[a, x>b]    , where b->a is red
    # to     a:[x<a, b]      b:[a<x<b, x>b], where a->b is red
    # input node is b, output is the that take the place of b (e.g. a)
    def __rotateRight(self, b):
        a = b.left
        b.left = a.right
        a.right = b
        a.isRed = b.isRed
        b.isRed = True
        return a

    # from a[left, right]   b[a, null]  b->a is red, will add c at the null link of b.right
    # to   a[left, right]   b[a, c]     where b->a is red, and b->c is also red => flop colors
    # also balancing from c   to   b    after inserting a
    #                   b        a   c
    #                 a
    # keeps two links red, which is prohibited => flip colors to have link to b red and the other two black
    # all other cases should come to this case, therefore add new nodes with RED always
    def __flipColors(self, a):
        a.isRed = True
        if a.left  != None: a.left.isRed = False
        if a.right != None: a.right.isRed = False

    def __put(self, node, key, value):
        # same as bst, apart from the color part
        if node == None: return Node(key, value)
        condition = self.__compare(node.key, key)
        if condition < 0   : node.left  = self.__put(node.left, key, value)
        elif condition > 0 : node.right = self.__put(node.right, key, value)
        else: node.value = value
        # the color part
        ## right is Red and left is Black, which is not the proper form, the revers is
        if self.__isNodeRed(node.right) == True and self.__isNodeRed(node.left) == False :
            node = self.__rotateLeft(node)
        ## smaller value was added => needs rebalancing
        if self.__isNodeRed(node.left) == True and self.__isNodeRed(node.left.left) == True:
            node = self.__rotateRight(node)
        ## both are red, not a proper form
        if self.__isNodeRed(node.left) == True and self.__isNodeRed(node.right) == True:
            self.__flipColors(node)

        return node
        
    def __isNodeRed(self, node):
        if node == None: return False
        return node.isRed    

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

tree = RedBlackTree()    
tree.put(1, 1.0)
tree.put(2, 2.0)
tree.put(7, 7.0)
tree.put(6, 6.0)
tree.put(5, 5.0)
tree.put(9, 9.0)
tree.put(3, 3.0)
tree.put(4, 4.0)
tree.printTree()