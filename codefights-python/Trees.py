class Tree(object):
    def __init__(self, x):
        self.value = x
        self.left = None
        self.right = None

def deleteFromBST(t, queries):
    if queries == None: return t
    if len(queries) == 0: return t
    for q in queries:
        t = delete(t, q)
    return t

def delete(node, value):
    if node == None: return node
    condition = compare(node.value, value)
    if condition < 0: node.left = delete(node.left, value) # go left
    elif condition > 0: node.right = delete(node.right, value) # go right
    else:
        # normed in BST remove, but description requires a small change, remove the line below
        #if node.right == None: return node.left
        if node.left  == None: return node.right
        if node.left != None:
            # node to delete was found, now search for successor
            toDelete = node
            # find the rightmost node on the left subtree
            successor = findMax(toDelete.left)
            successor.left = deleteMax(toDelete.left)
            successor.right = toDelete.right
            node = successor
#        else:           
    return node    

def compare(a, b):
    if a > b: return -1
    if a < b: return 1
    return 0

def findMax(node):
    if node.right == None: return node
    return findMax(node.right)

def deleteMax(node):
    if node.right == None: return node.left
    node.right = deleteMax(node.right)
    return node

# timeout, it would need a trie 
import sys
def findSubstrings(words, parts):
    new_parts = sorted(parts,key=len,reverse=True)
    idx = 0
    for w in words:
        p_len = 0
        p_start = sys.maxsize
        for p in new_parts:
            tmplen = len(p)
            if tmplen < p_len: break          
            tmpstart = w.find(p)
            if tmpstart > -1 and tmpstart < p_start:
                p_len = tmplen
                p_start = tmpstart
        if p_len > 0:
            words[idx] = w[:p_start] + '[' + w[p_start:p_start+p_len] + ']' + w[p_start+p_len:]
        else:
            words[idx] = w
        idx += 1
    return words                
