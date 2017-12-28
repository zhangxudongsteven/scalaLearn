### Lec 5.4 Higher-Order List Functions

```scala
abstract class List[T] {
  def map[U](f: T => U): List[U] = this match {
    case Nil		=> this
    case x :: xs	=> f(x) :: xs.map(f)
  }
}

xs map (x => x * factor)
```



```scala
abstract class List[T] {
  def filter[T](f: T => Boolean): List[T] = this match {
    case Nil		=> this
    case x :: xs	=> if (f(x)) x :: xs.filter(f) else xs.filter(f)
  }
}

xs filter (x => x > 0)
```



```scala
xs filterNot p
xs partition p // Page 327 与 span 对比
xs takeWhile p
xs dropWhile p
xs span p // Page 328 !!!
```



exercise with *pack()* and *encode()*