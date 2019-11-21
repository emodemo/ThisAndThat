# https://github.com/aramzham/CodeFights-Solutions/blob/master/PythonCodeFightsSolutions/Slithering_In_Strings/Slithering_In_Strings.py
# https://github.com/emirot/codesignal/blob/master/python/competitiveEating.py

xs = [()]
res = [False] * 2
if xs: res[0] = True
if xs[0]: res = True
print(res)
#####
print(15//-4) # // Divides and rounds down to the nearest integer. while / only divides
#####
n = 50
print(n.bit_length())
#####
class Spam(int): pass
x = Spam(0)
type(x) == int # False
isinstance(x, int) # True
#####
n = 1302
x = 5
hex(int(str(n), base=x)) # Oxca
z = hex(int(str(n), base=x))[2:] # ca
print(z)
#####
s = [0, 4, 2, 3, 1, 7]
upperBound = 10
def mexFunction(s, upperBound):
    found = -1
    print(range(upperBound))
    for i in range(upperBound):
#        print(i)
        if not i in s:
#            print(i)
            found = i
            break
    else:
        found = upperBound
    return found
mexFunction(s, upperBound)
#####
def listBeautifier(a):
    res = a[:]
    while res and res[0] != res[-1]:
        _, *res, _ = res # wihtout first and last element, if _,_,*res,_ => wihtout the 1st 2 elements
#       print(res)
    return res
a = [3, 4, 2, 4, 38, 4, 5, 3, 2]
#print(listBeautifier(a))
#####
message = "ds  df    df  dfsdfd"
message.lower().capitalize()
' '.join(message.split()) # message.split, take the chunks, an " ".join .. joins the chunks with whitespace in between
message.replace("\t", " "*4) # replace \t with 4 times white space
# https://docs.python.org/3.6/library/textwrap.html // wrap and fill methods
print('hehehehe'[::-1]) # TODO: check arryas
table = str.maketrans('abcdefghijklmnopqrstuvwxyz','zabcdefghijklmnopqrstuvwxy') # maps corresponding characters
'somestring'.translate(table) # translates the string using the table
