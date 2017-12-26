### Lec 3.1 Class Hierarchies

**Abstract Classes**: 

- abstract classes can contain members which are missing an implementation.
- we cannot *new* an instance directly from an abstract class.

This implies that the types *Empty* and *NonEmpty* **conform** to the type IntSet

*IntSet* is called the **superclass** of *Empty* and *NonEmpty*.

*Empty* and *NonEmpty* are **subclasses** of *IntSet*.

- In Scala, any user-defined class extends another class.
- If no superclass is given, the standard class Object in the Java package java.lang is assumed.
- The direct or indirect superclass of a class C are called *base classes* of C.

**implements**: same

**override**: same

```scala
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

// 以下用 scala 实现了二叉树

// 定义了结点
class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true

  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this

  // !!! 该函数非常有挑战性
  // 实际执行结果，为将原树全部拆解，然后一一incl到新树(other)上
  // 因此，如果原树很大，调用时需要反向调用
  def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem

  override def toString = "{" + left + elem + right + "}"
}

// This defines a singleton object named Empty.
// ！！！定义了叶子结点，
// 使用 object 而非 class，可以保证其不会被额外实例化，
// 从而实现所有的叶子结点均依赖于同一个实例。
object Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  def union(other: IntSet): IntSet = other
  override def toString = "."
}

object intsets {
  println("abstract class")
  val t1 = new NonEmpty(3, Empty, Empty) // NonEmpty = {.3.}
  val t2 = t1 incl 4 // IntSet = {.3{.4.}}
}

```

**Persistent Data Structures**: one of the cornerstone of scaling functional programming up to collections and so on.



**Scala also use dynamic binding!** The code gets executed on a object is determined by the **run time value** that is passed. See the example in the vedio.



It's similar to the higher-order functions. In this case:

- Can we implement objects in terms of higher-order functions, or the other way?