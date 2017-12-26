### Lec 4.5 Decomposition

```scala
def isInstanceOf[T]: Boolean	// Java: x instanceof T
def asInstanceOf[T]: T			// Java: (T) x
```

#### Solution 1: Object-Oriented Decomposition

```scala
// 原程序中，每个 class 都要实现全部类的接口
// 方案一：将计算抽象成统一的一个接口
trait Expr {
  def eval: Int
  def show: String
}

class Number(n: Int) extends Expr {
  def eval: Int = n
}

class Sum(e1: Expr, e2: Expr) extends Expr {
  def eval: Int = e1.eval + e2.eval
}

class Prod(e1: Expr, e2: Expr) extends Expr {
  def eval: Int = e1.eval * e2.eval
}
```

*Problem：It cannot be encapsulated in the method of a single object. (通用的方法不能封装在单个对象的方法中)*

#### Solution 2: Pattern Matching

**Case Classes**

```scala
trait Expr {
  def eval: Int = this match {
	case Number(n) => n
	case Sum(e1, e2) => eval(e1) + eval(e2)
  }
  def show(e: Expr): String = e match {
    case Number(x) => x.toString
    case Sum(l, r) => show(l) + " + " + show(r)
  }
}
case class Number(n: Int) extends Expr // we can use `Number(1)` instead of `new Number(1)`
case class Sum(e1: Expr, e2: Expr) extends Expr
```

*Pattern Matching* is a generlization of *switch* from C/Java to class hierarchies, which use the keyword *match*.

```scala
def eval(e: Expr): Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}

// 以下是解析过程

eval(Sum(Number(1), Number(2)))

->

Sum(Number(1), Number(2)) match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}

->

eval(Number(1)) + eval(Number(2))
```



**选择方案的一个关键点在于：are we creating new sub-classes of expression or are we more often creating new methods.**

- sub-class: Object-Oriented Decomposition
- new methods: Pattern Matching