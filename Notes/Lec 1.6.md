# Lec 1.6 & 1.7

#### Blocks and Lexcial Scope

- Avoid **NameSpace Pollution**: putting the auxiliary functions inside public function.

- 改进的牛顿法如下：

  object session {

  ​	def abs(x: Double) = if (x < 0) -x else x

  ​	def sqrt(x: Double) = {

  ​		def sqrtIter(guess: Double, x: Double): Double = if (isGoodEnough(guess, x)) guess else sqrtIter(improve(guess, x), x)

  ​		def isGoodEnough(guess: Double, x: Double) = abs(guess * guess - x) < 0.001

  ​		def improve(guess: Double, x: Double) = (guess + x / guess) / 2

  ​		sqrtIter(1.0, x)

  ​	}

  }

我们用 Block 即 {} 优化代码结构，Block 特性如下：

- It contains a sequence of definations or expressions.

- The last element of a block is an expression that defines its value.

- This return expression can be preceded by auxiliary definations.

- Blocks are themselves expressions

- **（*）**进一步优化代码，sqrt函数中，x 可共享外部变量：

  object session {

​	def abs(x: Double) = if (x < 0) -x else x

​	def sqrt(x: Double) = {

​		def sqrtIter(guess: Double): Double = if (isGoodEnough(guess)) guess else sqrtIter(improve(guess))

​		def isGoodEnough(guess: Double) = abs(guess * guess - x) < 0.001

​		def improve(guess: Double) = (guess + x / guess) / 2

​		sqrtIter(1.0)

​	}

}

**某行太长时，可用（）括起来，也可用 + 号连接两行**

#### Tail Recursion

Euclid's Algorithm 求最大公约数：(Tail Recursion)

def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

计算 Factorial 数：(Not Tail Recursion)

def factorial(n: Int): Int = if (n == 0) 1 else n * factorical(n - 1)

**Tail Recursion**: If a function calls itself as its last action, the function's stack frame can be reuesd. => Tail recursive functions are iterative processes. (其执行效率（空间/时间）等价于循环)



*Premature optimization is the source of evil. —— Donald Knuth*



**Tail Calls Annotation** :

In Scala, only directly recursive calls to the current function are optimized. One can require that a function is tail-recursive using a @tailrec annotation. If the annotation is given, and the implementation of funtion were not recursive, an error would be issued.

@tailrec

def factorial(n: Int): Int = {

​	def loop(acc: Int, n: Int): Int = 

​		if (n == 0) 0 acc 

​		else loop(acc * n, n -1)

​	loop(1, n)

}

 