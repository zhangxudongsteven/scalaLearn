### Lec 2.1 - Higher-Order Functions & Lec 2.2 - Currying — Lec 2.4 Summar

- Scala treat functions as first-class values, which can be passed as a parameter and returned as a result.
- Functions that take other functions as parameters or that return functions as results are called *higher* order functions.



def sum(f: Int => Int, a: Int, b: Int): Int = if (a > b) 0 else f(a) + sum(f, a + 1, b)

*f* can be many functions:

```scala

def id(x: Int): Int = x

def cube(x: Int): Int = x * x * x

def factorial(n: Int): Int = if (n == 0) 1 else n * factorical(n - 1)

...
```



*Function Type*: 

​	**f: A => B**  A and B are input and output



*Anonymous Functions*: (Syntactic Sugar)

```scala
(x: Int) => x * x * x

(x: Int, y: Int) => x + y
```

The type of the parameter can be omitted if it can be inferred by the compiler from the context.



用尾递归重写前面的累加函数

```scala
@tailrec
def sum(f: Int => Int, a: Int, b: Int) = {
	def loop(a: Int, acc: Int): Int = 
		if (a > b) acc 
		else loop(a + 1, f(a) + acc)
	loop(a, 0)
}
```



Currying**: a special way to write higher-order functions.

def f(args1)…(argSn-1)(argSn) = E

===

def f = (args1 => (args2 => …(argSn => E)))

**带 n 个参数的函数，可以被 map 成 n 层嵌套的 n 个匿名函数**



如下推导：

def sum(f: Int => Int): (Int, Int) => Int = {

​	def sumF(a: Int, b: Int): Int = 

​		if (a > b) 0 else f(a) + sumF(a + 1, b)

​	sumF

}

上面的例子中，sum函数接受一个函数作为参数并返回一个 *(Int, Int) => Int* 形式的函数。

def sumInts = sum(x => x)

def sumCubes = sum(x => x * x * x)    ===   sum(cube)(1, 10)  ===   (sum(cube))(1, 10)

def sumFactorials = sum(fact)



**在 scala 中，上述形式可被简化为如下的连写：**

def sum(f: Int => Int)(a: Int, b: Int): Int = if (a > b) 0 else f(a) + sum(f)(a + 1, b)

**The Type of this function is as followed:**

(Int => Int) => (Int, Int) => Int

*形式为 (Input) => output*

(Int => Int) => (Int, Int) => Int     ===      (Int => Int) => ((Int, Int) => Int)



**a => b 读作 a maps to b**

**a + b * c = d 读作 a, b, c reduced to d**



写出一个连乘，并写出一个累乘与累加通用的函数

object exercise {

​	// 累乘函数

​	def product(f: Int => Int)(a: Int, b: Int): Int = 

​		if (a > b) 1 else f(a) * product(f)(a + 1, b)

​	product(x => x * x)(3, 4)	[144]

​	

​	def fact(n: Int) = product(x => x)(1, n)

​	fact(5)	[120]

​	

​	// 通用函数，what we are after is a version of **map reduce**. 

​	def mapReduce(map: Int => Int, reduce: (Int, Int) => Int, init: Int)(a: Int, b: Int): Int = 

​		if (a > b) init

​		else reduce(map(a), mapReduce(map, reduce, zero)(a+1, b))



​	// 所以上述累乘与累加函数可以写作：

​	def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a, b)

​	def sum(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x + y, 0)(a, b)

}



### Example: Find Fixed Points

defination: a number *x* is called *fix point* of a function *f* if *f(x)=x*

The iteration converges by averaging successive values.

牛顿法求 sqrt 根，本质上和上述方法一致，通过不断迭代找到该点。

def sqrt(x: Double) = fixedPoint(y => x / y)(1.0)

def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)

#### Exercise: Write a square root function using both *fixedPoint* and *averageDamp*.

```scala
import math.abs

object exercise {

	val tolerance = 0.0001

	def isCloseEnough(x: Double, y: Double) = 
		abs((x - y) / x) < tolerance

	def fixedPoint(f: Double => Double)(firstGuess: Double) = {
		def iterate(guess: Double): Double = {
			val next = f(guess)
			if (isCloseEnough(guess, next)) next else iterate(next)
		}
		iterate(firstGuess)
	}
  
	fixedPoint(x => 1 + x/2)(1)    //  === 2
  
	def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2
	def sqrt(x: Double) = fixedPoint(averageDamp(y => x / y))(1)
	sqrt(2)
}
```



### Lec 2.4 - Scala Syntax Summary

Extended Backus-Naur form(EBNF): a context-free syntax (| [] {})

本章节最好重看视频，用 EBNF 总结了目前所有的 Scala 语法。