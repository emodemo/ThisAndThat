# PYTHON NUMERICAL METHODS
## CHAHPTER 14: LINEAR ALGEBRA
# https://pythonnumericalmethods.berkeley.edu/notebooks/Index.html

import numpy as np
from numpy import arccos, dot, cross, eye
from numpy.linalg import norm, det, inv, solve, cond, matrix_rank

#from scipy.linalg import norm, det

### vector addition
a = np.array([[1, -5, 3, 2, 4]])
b = np.array([[1, -5, 3, 2, 4]])
a + b # [[  2 -10   6   4   8]]
3 + a # [[ 4 -2  6  5  7]]
a * b # [[ 1 25  9  4 16]]
3 * a # [[  3 -15   9   6  12]]

### The dot product is a measure of how similarly directed the two vectors are.
### if(angle==0) parallel, if(angle==pi/2) orthogonal (perpendicular) 
v = np.array([[10, 9, 3]])
w = np.array([[2, 5, 12]])
dot(v, w.T) # the 2nd must be transposed, (10*2) + (9*5) + (3*12) = 20 + 45 + 36 = 101
theta = arccos(dot(v, w.T)/(norm(v)*norm(w))) # [[0.97992471]]

### The geometric interpretation of the cross product is a vector perpendicular to both v and w
### with length equal to the area enclosed by the parallelogram created by the two vectors.
n = cross(v, w) # no transpose here, [[  93 -114   32]]

P = np.array([[1, 7],
              [2, 3],
              [5, 0]])
Q = np.array([[2, 6, 3, 1],
              [1, 2, 3, 4]])
### slip Q into P
### (2*1)+(1*1) (6*1)+(2*1) (3*1)+(3*1) (1*1)+(4*1)
### (2*2)+(1*3) (6*2)+(2*3) (3*2)+(3*3) (1*2)+(4*3)
### (2*5)+(1*0) (6*5)+(2*0) (3*5)+(3*0) (1*5)+(4*0)
np.dot(P, Q)

### determinant
M = np.array([[0,2,1,3], 
             [3,2,8,1], 
             [1,0,0,3],
             [0,3,2,1]])
det(M)

### identity matrix of size 4
I = eye(4) # M dot I = M
### inverse N of matrix M is such that M dot N = N dot M = I
### however some matrices does not have inverses (singular matrices, det(M)==0)
N = inv(M)
dot(M, inv(M)) ## ??? do not get I
## but here I do get I
A1 = np.array([[4,3],[3,2]])
dot(A1, inv(A1))

### rank = number of linearly independent columns or rows

### Solve linear equations
### inf or no solutions throws error "Singular matrix"
### x+4y-4z=0
### -11y+7z=3
###     6z=12
f = np.array([[1,4,-4],[0,-11,7],[0,0,6]])
y = np.array([0,3,12]) # this one has 1 solution
x = solve(f, y) ## x=4, y=1, z=2,
x = dot(inv(f), y) ## x=4, y=1, z=2,
#print(matrix_rank(f))
#print(matrix_rank(f,y))
### since euqations are less than varaibles, so infinitely many solutions
### -x-y-2z+3w=14
### 2x-2y-z+2w=6
###      -3z+2w=0
f = np.array([[-1,-1,2,3],[2,-2,-1,2],[0,0,-3,2]])
y = np.array([14,6,0])
#print(matrix_rank(f))
#print(matrix_rank(f,y))
# x = dot(inv(f), y)

### exercice
s = np.array([[10, 10]])
d = np.array([[4, 4, 5, 4, 4]])

def my_flow_calculator(s, d):
    # f1=10         s[0]
    # f2=f1+f3-4    d[3]
    # f3+f6=f4-4    d[0]
    # f4+f5=10      s[1]
    # f7=f5-4       d[1]
    ff = np.array(
        [[1,0,0,0,0,0,0],
        [1,-1,1,0,0,0,0],
        [0,0,-1,-1,0,1,0],
        [0,0,0,1,1,0,0],
        [0,0,0,0,1,0,-1],
        [0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0]])
    yy = np.array([s[0,0],d[0,2],d[0,0],s[0,1],d[0,0],d[0,3],d[0,4]])
    xx = solve(ff, yy)
    print(xx)
    return 0

# infinitely many solutions
# my_flow_calculator(s, d)

# importing library sympy
from sympy import symbols, Eq, solve
  
x, y, z = symbols('x,y,z')
eq1 = Eq(((1/4)*x+y-z), 0)
eq2 = Eq((x+4*y+2*z), 12)
eq3 = Eq((2*x-3*y-z), 3)
#print(solve((eq1, eq2, eq3), (x, y, z)))

## EXAMPLE with infinitely many solutions
x, y, z, w = symbols('x,y,z,w')
eq1 = Eq((x-y+2*z+3*w), 14)
eq2 = Eq((2*x-2*y-z+2*w), 6)
eq3 = Eq(((-3)*z+2*w), 0)
# print(solve((eq1, eq2, eq3), (x, y, z, w)))

# https://pythonnumericalmethods.berkeley.edu/notebooks/chapter14.07-Summary-and-Problems.html#problems
f1,f2,f3,f4,f5,f6,f7 = symbols('f1,f2,f3,f4,f5,f6,f7')
eq1 = Eq((f1),10)
eq2 = Eq((f1+f3-f2),4)
eq3 = Eq((f4-f3-f6),4) # 3
eq4 = Eq((f4+f5),10)
eq5 = Eq((f5-f7),4)
eq6 = Eq((f2),4) # 5
eq7 = Eq((f6+f7),4)
# if an Eq((f7),1.5) or another number in the range ??
print(solve((eq1, eq2, eq3, eq4, eq5, eq6, eq7, eq8), (f1,f2,f3,f4,f5,f6,f7)))
# {f1: 10, f2: 5, f3: -1, f4: 6 - f7, f5: f7 + 4, f6: 4 - f7}
