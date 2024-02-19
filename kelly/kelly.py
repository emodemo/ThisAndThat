# taken from quantdare.com Jose Leiva
from scipy.optimize import minimize_scalar, newton, minimize
from scipy.integrate import quad
from scipy.stats import norm
import numpy as np

# Thorp focuses on annual excess returns and suggests modeling P(r) as a normal distribution truncated at m +/- 3*st.
# TODO: with lognormal returns instead of normal, as per Thorp - The Kelly Criterion And The Stock Market
# TODO: probability of negative excess return
def eee(l,f,s,m,st):
    print(str(l))
    return np.log(1+f*l)*norm.pdf(l,m,st)

def norm_integral(f,m,st):
    val,er = quad(lambda l: np.log(1+f*l)*norm.pdf(l,m,st),m-3*st,m+3*st)
    #val,er = quad(lambda l: eee(l,f,s,m,st),m-3*st,m+3*st)
    return -val

def bbb(l,f,s,m,st):
    print(str(l))
    return (l/(1+f*l))*norm.pdf(l,m,st)

def norm_dev_integral(f,m,st):
    val,er = quad(lambda l: (l/(1+f*l))*norm.pdf(l,m,st),m-3*st,m+3*st)
    #val,er = quad(lambda l: bbb(l,f,s,m,st),m-3*st,m+3*st)
    return val
 
# Reference values from Eduard Thorp's article
m = .058
s = .216
# Option 1: minimize the expectation integral
sol = minimize_scalar(norm_integral,args=(m,s),bounds=[0.,2.],method='bounded')
print('Optimal Kelly fraction: {:.4f}'.format(sol.x))
# Option 2: take the derivative of the expectation and make it null
x0 = newton(norm_dev_integral,.1,args=(m,s))
print('Optimal Kelly fraction: {:.4f}'.format(x0))


# taken from wduwant.com/kelly
# kelly_portfolio.xlsx: example with 2 Independent Assets with known probabilities of win for time series with t = 2
# here: example with 3 Independent Assets with known probabilities of win for time series with t = 2
# expected returns are Ra=[t1:30%,t2:−15%] Rb=[t1:−5%,t2:20%] Rc=[t1:5%,t2:10%]
#!!! here we are looking for the weights
# also here: https://github.com/v-for-vasya/Kelly-Criterion.git

def objective(x, sign=-1.0):
    w1=x[0]
    w2=x[1]
    w3=x[2]
    a=np.array([0.3,-.15])
    b=np.array([-0.05,0.2])
    c=np.array([0.05,0.1])
    fx=np.log(np.prod(1+w1*a+w2*b+w3*c))
    return sign*(fx)

def weight_sum(x):
    return x[0]+x[1]+x[2]-1.0

b=(-1,1)
bounds=(b,b,b)

cons={'type': 'eq', 'fun': weight_sum}

x_default=np.zeros(3)
sol = minimize(objective, x_default,method='SLSQP', bounds=bounds, constraints=[cons])

#print(str(sol))
print('Kelly portfolio weights are as follows ' + str(sol.x*100))

## ME Example from  "mathexchange/Kelly criterion with more than two outcomes"
# f(x)=0.7log(1−x)+0.2log(1+10x)+0.1log(1+30x)
# note from the math site: 
# I get that the optimum occurs at x=0.248, with f(0.248)=0.263.
# In other words, if you bet a little under a quarter of your bankroll,
# you should expect your bankroll to grow on average by e^0.263=1.30 for every bet.

## here we are looking for the optimal bet
#TODO: how to optimize for this example
from sympy import *
# For a symbolic verification with Python and SymPy one would set the derivative y'(x) 
# of the expected value of the logarithmic bankroll y(x) to 0 and solve for x
x, p = symbols('x p')
y = 0.1 * log(1 + 30 * x) + 0.2 * log(1 + 10 * x) + 0.7 * log(1 - x)
s = solve(diff(y, x), x)
print(str(s))
# [-0.0578343329665600, 0.247834332966560]
d = s[1]
fx = 0.1 * log(1 + 30 * d) + 0.2 * log(1 + 10 * d) + 0.7 * log(1 - d)
print(str(fx)) # 0.263191478105847
