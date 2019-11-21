# see https://www.geeksforgeeks.org/find-if-string-is-k-palindrome-or-not/
def kpalindrome(s, k):
    
    ## imagine there are two strings: s and the reverse of s
    ## compare both endings: if same, remove them recursivelly
    ## if not recursivelly the get the minimum of A(s-last vs reverse of s) OR B(s vs revers of last - 1)
    # here with 2d array instead of recurrsion
    reverse = s[::-1]
    l = len(s)
    # store subproblems results here instead of recursion
    dp = [[0] * (l + 1) for _ in range(l + 1)]
    
    for i in range(l+1):
        for j in range(l+1):
            if not i:
                dp[i][j] = j # if s is empty => remove all chars from reverse of s (e.g. min is j)
            elif not j:
                dp[i][j] = i # if reverse of s is empty => remove all from s (e.g. min is i)
            elif s[i-1] == reverse[j-1]:
                dp[i][j] = dp[i-1][j-1] # last chars of both strings are equal => ignore them
            else:
                # counter ++ for removed symbol
                dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1]) # remove from s, remove from reverse of s
    
    # get the result from the last recursion dp[l][l]
    # and check if smaller or equal to k*2, as counters are updated for 2 times (once per string)
    return dp[-1][-1] <= k*2