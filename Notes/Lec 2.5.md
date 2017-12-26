### Lec 2.5 Functions and Data

Rational Numbers: 
$$
x/y
$$

```scala
class Rational(x: Int, y: Int) {
  // a test performed before class initialize
  require(y != 0, "denominator must be nonzero")
  
  // 定义了一个单参数构造函数
  def this(x: Int) = this(x, 1)
  
  // 用于简化分数
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  // 采用 call-by-value 方式调用，防止重复计算
  val numer = x / gcd(x, y)
  val denom = y / gcd(x, y)
  
  def add(that: Rational): Rational = 
	new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  
  def less(that: Rational) = this.numer * that.denom < that.numer * this.denom
  
  def max(that: Rational) = if (this.less(that)) that else this
  
  def neg: Rational = new Rational(-numer, denom)
  
  def sub(that: Rational) = add(that.neg)
  
  // 用于打印 object
  override def toString = numer + "/" + denom
}

// main
object retionals {
  val x = new Retional(1, 3)
  val y = new Retional(5, 7)
  val z = new Retional(3, 2)
  
  x.numer // 1
  x.denom // 3

  x.sub(y).sub(z) // -79/42
  y.add(y) // 70/49 -> 10/7
  
  x.less(y) // true
  x.max(y) // 5/7
  
  new Rational(2) // 2/1
}
```



**DRY Principal: Don't Repeat Yourself**

**Data Abstraction**: A cornerstone of software engineering.



*require* is a predefined function, throw an *IllegalArgumentException*. It is used to enfore a precondition on the caller of a function.

*assert* will also throw an exception, throw an *AssertionError*. It's used as to check the code of the function itself.



The **default constructor** of scala will execute the value definition and val in the class. 



**Define Operators**: Scala支持自定义运算符号，具体规则可参考工具书。

各种运算符的运算顺序与 Java 一致。all letters 拥有最低优先级，其它 special characters 拥有最高优先级。

```scala
r.add(s) === r add s

class Rational(x: Int, y: Int) {
  // a test performed before class initialize
  require(y != 0, "denominator must be nonzero")
  
  // 定义了一个单参数构造函数
  def this(x: Int) = this(x, 1)
  
  // 用于简化分数
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  // 采用 call-by-value 方式调用，防止重复计算
  val numer = x / gcd(x, y)
  val denom = y / gcd(x, y)
  
  def + (that: Rational): Rational = 
	new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  
  def < (that: Rational) = this.numer * that.denom < that.numer * this.denom
  
  def max(that: Rational) = if (this < that) that else this
  
  // 定义与冒号中间需要一个空格
  def unary_- : Rational = new Rational(-numer, denom)
  
  def - (that: Rational) = this + -that
  
  // 用于打印 object
  override def toString = numer + "/" + denom
}

// main
object retionals {
  val x = new Retional(1, 3)
  val y = new Retional(5, 7)
  val z = new Retional(3, 2)
  
  x.numer // 1
  x.denom // 3

  x + y + z // -79/42
  y + y // 70/49 -> 10/7
  
  x - y // true
  x max y // 5/7
  
  new Rational(2) // 2/1
}
```

