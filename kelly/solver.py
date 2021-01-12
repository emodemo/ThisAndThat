from sympy import *
# For a symbolic verification with Python and SymPy one would set the derivative y'(x) 
# of the expected value of the logarithmic bankroll y(x) to 0 and solve for x
x, b, p = symbols('x b p')
y = p * log(1 + b * x) + (1 - p) * log(1 - x)
s = solve(diff(y, x), x)
print(str(s))
# [(b*p + p - 1)/b]
