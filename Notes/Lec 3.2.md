### Lec 3.2 How Classes Are Organized

Type 与 Class 的关系：（Type 为数据类型）

- Object 是所有 Class 的父类；
- Object 的 Type 是 Class；
- 从代码上，Type 是 Class 实现的一个接口。



Classes and objects are organized in packages.

```scala
package progfun.examples

object Hello { … }

// import single class
import progfun.examples.Hello
// import all
import progfun.examples._
import progfun.examples.{Hello1, Hello2}
```

We can refer to *Hello* by its *fully qualified name* - *progfun.examples.Hello*. (like java)

REPL 中执行使用以下命令：

> scala progfun.examples Hello



以下 entities 均已自动导入：

- All members of package *scala*
- All members of package *java.lang*
- All members of the singleton object *scala.Predef*



在 scala 和 java 中，class 只能有一个 superclass。

```scala
// trait 类似 java 中的 abstract class（抽象类）
trait Planar {
  def height: Int
  def width: Int
  def surface = feight * width
}

// 一个类可以继承多个 trait
class Square extends Shape with Planer with Movable

// Traits resemble interfaces in Java, but are more powerful because they can contains fields and concrete methods.
```

**trait 不能包含（value）parameter**



At the top of the type hierarchy we find:

- Any: Root
- AnyRef: Objects or Java Objects
- AnyVal: types in scala packages. (Double, Float, Int)



At the bottom, *Nothing* is a subtype of every other type.There is no value of type *Nothing*.

- To signal abnormal termination
- As an element type of empty collections

```scala
def error(msg: String) = throw new Error(msg) // error: (msg: String)Nothing
```



At the bottom, *Null* is a subtype of every class that inherits from *Object*; it is **incompatible** with subtypes of *AnyVal*.   (the subtype only of the reference types, not of the value types.)

```scala
val x = null // x : Null = null
val y: String = x // y : String = null
val z: Int = null // error: incompatible
```

