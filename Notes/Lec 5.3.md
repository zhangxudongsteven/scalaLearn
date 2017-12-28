### Lec 5.3 Implicit Parameters

```scala
def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
  val n = xs.length/2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xsl, y :: ysl) =>
        if (lt(x, y)) x :: merge(xsl, ys)
            else y :: merge(xs, ysl)
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst)(lt), msort(snd)(lt))
  }
}

val nums = List(2, -4, 5, 7, 1)
msort(nums)((x: Int, y: Int) => x < y)
// msort(nums)((x, y) => x < y)

val fruits = List("apple", "banana", "pinapple", "orange")
msort(fruits)((x: String, y: String) => x.compareTo(y) < 0)
// msort(fruits)((x, y) => x.compareTo(y) < 0)
```



```scala
// scala 中已有通用的排序定义
scala.math.Ordering[T]

import math.Ordering

def msort[T](xs: List[T])(ord: Ordering): List[T] = {
  val n = xs.length/2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xsl, y :: ysl) =>
        if (ord.lt(x, y)) x :: merge(xsl, ys)
            else y :: merge(xs, ysl)
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst)(ord), msort(snd)(ord))
  }
}


val nums = List(2, -4, 5, 7, 1)
msort(nums)(Ordering.Int)

val fruits = List("apple", "banana", "pinapple", "orange")
msort(fruits)(Ordering.String)
```



**implicit parameters**: 

in the example above, passing around *lt* or *ord* is cumbersome. we can avoid this by making *ord* an implicit parameter.

```scala
// scala 中已有通用的排序定义
scala.math.Ordering[T]

import math.Ordering

// !!! using *implicit* arg here
def msort[T](xs: List[T])(implicit ord: Ordering): List[T] = {
  val n = xs.length/2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x :: xsl, y :: ysl) =>
        if (ord.lt(x, y)) x :: merge(xsl, ys)
            else y :: merge(xs, ysl)
    }
    val (fst, snd) = xs splitAt n
    // !!! 可省略参数，编译器会自动寻找
    merge(msort(fst), msort(snd))
  }
}


val nums = List(2, -4, 5, 7, 1)
// !!! 可省略参数
msort(nums)

val fruits = List("apple", "banana", "pinapple", "orange")
// !!! 可省略参数
msort(fruits)
```

