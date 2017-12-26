#### Lec 1.2 & 1.3

def square(x: Double) = x * x

def sumOfSquares(x: Double, y: Double) = square(x) + square(y)

def power(x: Double, y: Int): Double = ...

sumOfSquares(3, 2+2)



The schema of expression evaluation is called *the substitution model* - **reduce** an expression to a value.

This substitution model is formalized in the lambda-calculus(微积分) -> FP foundation



-Does every expression reduce to a value (in a finite number of steps)?

-No. 死递归. it will **reduce** to itself.



两大计算策略（ evaluation strategy）：

- Call-by-value：it evaluates every function argument only once.(调用时取出表达式的计算值)
- Call-by-name：a function argument is not evaluated if the corresponding parameter is unused in the evaluation of the function body.(调用时代入表达式) 



CBV termination -> CBN termination too，反之**不成立**

不成立的例子：

def first(x: Int, y: Int) = x  &  first(1, loop)

CBN直接给出1，不计算loop值，而CBV优先计算loop值，陷入死循环。



Scala 默认采用 CBV 策略

手动开启 CBN：

def constOne(x: Int, y: **=>** Int) = 1