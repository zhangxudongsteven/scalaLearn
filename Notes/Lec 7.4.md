### Lec 7.4 Monads

A monad *M* is a parametric type M[T] with two operations: *flatMap* and *unit*.

```scala
trait M[T] {
  def flatMap[U](f: T => M[U]): M[U]
}

def unit[T](x: T): M[T]

// map
m map f == m flatMap (x => Unit(f(x)))
		== m flatMap (f andThen unit)
```

literature, *flatMap* is called *bind*.



**3 laws** to qualify as a monad:

- Associativity:

  ```scala
  m flatMap f flatMap g == m flatMap(x => f(x) flatMap g)
  ```

- Left unit:

  ```scala
  unit(x) flatMap f == f(x)
  ```

- Right unit:

  ```scala
  m flatMap unit == m
  ```

   

满足 Monads 形式的情况下：

1. Associativity says essentially that one can "inline" nested for expressions:

   ```scala
   for (y <- for (x <- m; y <- f(x)) yield y
   	 z <- g(y)) yield z
   // === 
   for (x <- m;
        y <- f(x)
        z <- g(y)) yield z
   // 内层循环可以嵌套
   ```

2. Right unit says

   ```scala
   for (x <- m) yield x 
   // ===
   m
   ```

   ​

#### Another type: Try

```scala
abstract class Try[+T]
case class Success[T](x: T)			extends Try[T]
case class Failure(ex: Exception)	extends Try[Nothing]

def flatMap[U](f: T => Try[U]): ...
def map[U](f: T => U): ...
```

