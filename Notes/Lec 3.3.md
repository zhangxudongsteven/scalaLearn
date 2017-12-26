### Lec 3.3 Polymorphism（多态性）

**Type Parameterization（T）**: classes and methods can now have types as parameters.

**关键字 trait**: 类似 java 中的接口，但 trait 支持<u>部分实现</u>。

Example：

**Immutable Linked List**： constructed from two building blocks

- Nil: the empty list
- Cons: a cell containing an element and the remainder of the list.

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

def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
// in most cases, type parameters can be left out.
// 编译器会自动推测[Int][Boolean]
singleton[Int](1)
singleton[Boolean](true)

```



**Type Erasure（类型擦除）**：all type parameters and type arguments are removed before evaluating the program. Java use it, C++ and C# don't.

So, *type parameters do not affect evaluation in Scala*. 在使用 T 参数时，编译器略去类型，转译为 Object。并在需要的时候再进行类型转换。



**Polymorphism**： means that a function type comes "in may forms".

- the function can be applied to arguments of many types.
- the type can have instances of many types.

such as: (**Lec 4.3 会详细介绍以下两种多态**)

- subtyping: instances of a subclass can be passed to a base class.
- generics: instances of a function or class are created by type parameterization.



```scala
object test {
  def nth(n: Int, xs: List[T]): T = 
  	// n < 0 的判断条件可以不加，在 xs 长度相对有限的情况下，平均性能可能反而会更好
  	if (xs.isEmpty || n < 0) throw new IndexOutOfBoundsException
	if (n == 0) xs.head
	else nth(n - 1, xs.tail)  
  	
  val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))
  
  nth(2, list)
  nth(-1, list) 
  nth(4, list)
}

```





