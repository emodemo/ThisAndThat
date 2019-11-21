def sumSubsets(arr, num):
	  s = [[i,[x]] for i,x in enumerate(arr) if x <= num]
	  r = []
	  length = len(arr)
	  while s:
	    i,c = s.pop()
	    nsum = sum(c)
	    if nsum == num and c not in r:
	      r.append(c)
	    else:
	      for j in range(i+1,length):
	        new_n = arr[j]
	        if sum(c) + new_n <=num:
	          s.append([j,c+[new_n]])
	  if not r:
	    return [[]]
	  return r[::-1]

# the only differences in the algorithms are:
# 1) using a set 
# 2) "j in range(i, length)" to get the current value too
def combinationSum(a, n):
    aset = sorted(x for x in set(a) if x <= n)
    alist = list(aset)
    stack = [[i,[x]] for i,x in enumerate(alist)]
    result = []
    length = len(alist)
    while(stack):
        i,c = stack.pop()
        nsum = sum(c)
        if nsum == n and c not in result:
            result.append(c)
        else:
            for j in range(i, length):
                new_n = alist[j]
                if sum(c) + new_n <= n:
                    stack.append([j,c+[new_n]])
                else: break
    r = ''
    if not result:
        return "Empty"
    for k in result[::-1]:
      tmp = ' '.join(str(l) for l in k)
      tmp = "(" + tmp + ")"
      r += tmp
    return r

import copy # for deepcopy
def wordBoggle(board, words):
    rows = len(board)
    cols = len(board[0])

    # board, word, word index, is First letter search, current row, current col
    def dfs(boardCopy, word, widx, first, row, col):
        if widx >= len(word):
            return True
        letter = word[widx]
        # find starting letter
        if first:
            for r in range(rows):
                for c in range(cols):
                    if boardCopy[r][c] == letter:
                        boardCopy[r][c] = '' # remove from board => cannot be reused for same word
                        if dfs(boardCopy,word,widx+1, False, r, c):
                            return True # word found
                        boardCopy[r][c] = letter # word not found, get back and coninue with next letter
        # find rest of letters in neighbours
        else:
            for r in range(row-1,row+2):
                for c in range(col-1,col+2):
                    if r >= 0 and r < rows and c >= 0 and c < cols and boardCopy[r][c] == letter:
                        boardCopy[r][c] = ''
                        if dfs(boardCopy, word, widx+1, False, r, c):
                            return True
                        boardCopy[r][c] = letter
        return False


    result = []
    for word in words:
        if dfs(copy.deepcopy(board), word, 0, True, 0, 0):
            result.append(word)

    return sorted(result)