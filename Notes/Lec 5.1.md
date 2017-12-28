### Lec 5.1 More Functions on Lists

```scala
xs.length
xs.last
xs.init // drop last one
xs take n // xs[0:n-1]
xs drop n // xs[n:]
// fst = xs take n
// snd = xs drop n
val (fst, snd) = xs splitAt n 
xs(n) // === xs apply n

xs ++ ys // concat
xs.reverse
xs updated (n, x)

xs indexOf x // -1 for not appear
xs contains x // === xs indexOf x >= 0

def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(ys)
}

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

// === xs ::: ys === ys.:::(xs)
def concat[T](xs: List[T], ys: List[T]) = xs match {
  case List() => ys
  // this case is not nessecary
  // case List(x) => x :: ys
  case z :: zs => z :: concat(zs, ys)
}

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case y :: ys => reverse(ys) ++ List(y)
}

def removeAt[T](xs: List[T], n: Int) = (xs take n) ::: (xs drop n + 1)
```



