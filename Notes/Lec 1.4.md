####Lec 1.4 & 1.5

def abs(x: Int) = if (x >= 0) x else -x   这里的 if-else 是用于 expression 而非 expression

- **def** form is 'by name'
- **val** form is 'by value'



*def loop: Boolean = loop*

*def x = loop （OK）*

*val x = loop（infinite loop）*

 

手动实现 && 与 || （注意，y 应采用 call-by-name 方式）

def and(x: Boolean, y: **=>** Boolean) = if (x) y else false

def or(x: Boolean, y: => Boolean) = if (x) true else y



##### Example Square Roots with Newtons Method

object session {

​	def abs(x: Double) = if (x < 0) -x else x

​	def sqrtIter(guess: Double, x: Double): Double = if (isGoodEnough(guess, x)) guess else sqrtIter(improve(guess, x), x)

​	def isGoodEnough(guess: Double, x: Double) = abs(guess * guess - x) < 0.001

​	def improve(guess: Double, x: Double) = (guess + x / guess) / 2

​	sqrtIter(1.0, x)

}

- The *isGoodEnough* test is not very precise for small numbers and can lead to non-termination for very large numbers. (Easy to explain)
- def isGoodEnough(guess: Double, x: Double) = abs(guess * guess - x)  **/x** < 0.001

