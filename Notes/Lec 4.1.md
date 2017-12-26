### Lec 4.1 Objects Everywhere

Scala fundamental types: **primitive types, functions and classes**.

Scala is a **pure object-oriented** language.

```scala
package idealized.scala

object true extends Boolean {
  def ifThenElse[T](t: => T, e: => T) = t
}

object false extends Boolean {
  def ifThenElse[T](t: => T, e: => T) = e
}

abstract class Boolean {
  
  // if (cond) te else ee
  // === cond.ifThenElse(te, ee)
  def ifThenElse[T](t: => T, e: => T): T
  
  def && (x: => Boolean): Boolean = ifThenElse(x, false)
  def || (x: => Boolean): Boolean = ifThenElse(true, x)
  def unary_! : Boolean = ifThenElse(false, true)
  def == (x: Boolean): Boolean = ifThenElse(x, x.unary_!)
  def != (x: Boolean): Boolean = ifThenElse(x.unary_!, x)

  def < (x: Boolean): Boolean = 
  ...
}
```

练习：定义自然数类 Nat

```scala
pachage week4

// Peano numbers: 皮亚诺公理
// 皮亚诺公理，也称皮亚诺公式，是数学家皮亚诺（皮阿罗）提出的关于自然数的六条公理系统。根据这六条公理可以建立起一阶算术系统，也称皮亚诺算术系统。
// 1. 0是自然数
// 2. 每一个自然数a，都有后继数a‘
// 3. 0不是任何自然数的后继数
// 4. 同一个自然数的后继数都相等
// 5. 拥有相等后继数的自然数相等
// 6. （归纳公理，保证数学归纳法的正确性）S包含于N。
// https://baike.baidu.com/item/%E7%9A%AE%E4%BA%9A%E8%AF%BA%E5%85%AC%E7%90%86/6218666

abstract class Nat {
  def isZero: Boolean
  // 前置数
  def predecessor: Nat
  // 后继数
  def successor = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {
  def isZero = true
  def predecessor = throw new Error("negative number")
  def successor = new Succ(this)
  def + (that: Nat) = that
  def - (that: Nat) = if (that.isZero) this else throw new Error("negative number")
  
}

// n 的后继数，前置数为n，后继数为 下一个后继数
class Succ(n: Nat) extends Nat {
  def isZero = false
  def predecessor = n
  // 迭代定义的算数体系
  // ！！！非常难想
  def successor = new Succ(this)
  def + (that: Nat) = new Succ(n + that)
  def - (that: Nat) = if (that.isZero) this else n - that.predecessor
}
```



### Lec 4.2 Functions as Objects

Function type **A => B** is just an abbreviation for the class *scala.Function1[A, B]*

```scala
package scala
trait Function1[A, B] {
  def apply(x: A): B
}

(x: Int) => x * x
===
{
  class AnonFun extends Function1[Int, Int] {
    def apply(x: Int) = x * x
  }  
  new AnonFun
}
===
new Function1[Int, Int] {
  def apply(x: Int) = x * x
}
// 这种匿名函数定义方式，在 java 中以同样的方式实现
```

在使用函数时，会先实例化该函数，再执行。

**eta-expansion**: Only if *f* is used in a place where a Function type is expected, it is converted automatically to the function value. (lambda 概念)



#### 在 Lec 3.3 的基础上构建 List 类

```scala
trait List[T] {
  // tell us which kind of list we have
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

// head 和 tail 在类参数中，自动生成类表达式
class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty: Boolean = true
  def head = throw NoSuchElementException("Nil.head")
  // 也可以写作，Nothing is a sub type of any other type, include T
  def tail: Nothing = throw NoSuchElementException("Nil.tail")
}

object List {
  // 函数可表达成 List(1,2)
  def apply[T](x1: T, x2: T): List[T] = 
  	new Cons(x1, new Cons(x2, new Nil))
  
  def apply[T]() = new Nil
}
```

