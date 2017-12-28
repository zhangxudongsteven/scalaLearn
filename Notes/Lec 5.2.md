### Lec 5.2 Pairs and Tuples

**defination**: 

```scala
val pair = ("a", 1) // (String, Int) = ("a", 1)
val tuple = ("a", 1, 2, 3) // (String, Int, Int, Int) = ("a", 1, 2, 3)
```





**merge sort**:

```scala
def msort(xs: List[Int]): List[Int] = {
  val n = xs.length/2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}

def merge(xs: List[Int], ys: List[Int]): List[Int] = xs match {
  case Nil => ys
  case x :: xsl => ys match {
    case Nil => xs
    case y :: ysl =>
   		if (x < y) x :: merge(xsl, ys)
    	else y :: merge(xs, ysl)
  }
}

// using pairs
def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
  case (Nil, ys) => ys
  case (xs, Nil) => xs
  case (x :: xsl, y :: ysl) =>
  	if (x < y) x :: merge(xsl, ys)
    	else y :: merge(xs, ysl)
}
```

