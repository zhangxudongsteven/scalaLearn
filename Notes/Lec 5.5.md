### Lec 5.5 Reduction of Lists

```scala
List(x1, ... ,xn) reduceLeft op = x1 op x2 op x3 ...

def sum(xs: List[Int]) = (0 :: xs) reduceLeft ((x, y) => x + y)
def product(xs: List[Int]) = (1 :: xs) reduceLeft ((x, y) => x + y)

// !!! same as
// (_ + _) === ((x, y) => x + y)
def sum(xs: List[Int]) = (0 :: xs) reduceLeft (_ + _)
def product(xs: List[Int]) = (1 :: xs) reduceLeft (_ * _)

// !!! same as
// can be apply to empty
def sum(xs: List[Int]) = (xs foldLeft 0) (_ + _)
def product(xs: List[Int]) = (xs foldLeft 1) (_ * _)
```



#### The defination of *reduceLeft* and *foldLeft*

```scala
abstract class List[T] {
  // 调用 foldLeft
  def reduceLeft(op: (T, T) => T): T = this match {
    case Nil => throw new Error("Nil.reductLeft")
    case x :: xs => (xs foldLeft x)(op)
  }
  // z 称为 accumulater，用于累计计算结果
  def foldLeft[U](z: U)(op: (U, T) => U): U = this match {
    case Nil => z
    case x :: xs => (xs foldLeft op(z, x))(op)
  }
}
```



**reduceRight** and **foldRight** is oppsite to *reduceLeft* and *foldLeft*.

right left 表示从哪边开始累计



```scala
def concat[T](xs: List[T], ys: List[T]): List[T] = 
	(xs foldRight ys) (_ :: _)
// 这里使用 foldRight 会报错
```

 详见 下一节