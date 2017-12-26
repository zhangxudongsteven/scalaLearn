### Lec 4.3 Subtyping and Generics

在 Lec 3.3（多态）中提到了，以下两种多态：

- subtyping: instances of a subclass can be passed to a base class.
- generics: instances of a function or class are created by type parameterization.

Subtyping: usually associated with object oriented programming.

Generics: originally from functional programming.



**Liscov Substitution Principle**: 子类功能不能退化（不要破环继承体系）。

[OOD（面向对象设计）的原则（solid）](http://blog.csdn.net/JavaLive09/article/details/7619284?utm_source=jiancool)



上述两种多态存在 interactions：

- **bounds**：
- **variance**：



### Bounds

```scala
def assertAllPos[S <: IntSet](r: S): S = ...
```



**<:** is an *upper bound* of the type parameter **S**: 

It means that *S* can be instantiated only to the types that conform to *IntSet*.

Generally, the notation

- **S <: T** means *S* is a subtype of *T*
- **S >: T** means *S* is a supertype of *T*, or *T* is a subtype of *S*

```scala
[S >: NonEmpty <: IntSet]
// restrict S to any type on the interval between NonEmpty and IntSet.
```



**covariant**: their subtyping relationship varies with the type parameter.

In scala, arrays are not covariant, **list is**.

A type that accepts mutations of its elements should not be covariant.

```scala
List[NonEmpty] <: List[IntSet] // scala.
NonEmpty[] <: IntSet[] // java. Arrays in Java are covariant
```



### Variance

how subtyping relates to genericity.

*C[T]* is a parameterized type and A, B are types such that **A <: B**.

(C is) **covariant**	C[A] <: C[B]		class C[+A] { … }

**contravariant**	C[A] >: C[B]		class C[-A] { … }

**nonvariant**		neither			class C[A] { … }



subtyping example of function types:

```scala
type A = IntSet => NonEmpty
type B = NonEmpty => IntSet
// A <: B
// !!! think about it
```

if A2 <: A1 and B1 <: B2 then 

​	A1 => B1 <: A2 => B2

**So functions are *contravariant* in their argument types and *covariant* in their result type.**

```scala
package scala
trait Function[-T, +U] {
  def apply(x: T): U
}
```

- **covariant** type parameter can only appear in method results. (父类接受子类的参数)
- **contravariant** type paramteres can only appear in method parameters.（子类接受父类的返回值）
- **nonvariant** type parameters can appear anywhere.



```scala
package List

trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  // U 必须为 T 的 父类
  // elem 不属于 T 的 父类时，会不断转换为自己的父类，直到包含
  def prepend[U >: T](elem: T): List[U] = new Cons(elem, this)
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmtpy = false
}

object Nil extends List[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

object test {
  val x: List[String] = Nil
}
```

