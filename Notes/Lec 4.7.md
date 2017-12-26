### Lec 4.7 Lists

```scala
val nums = List(1,2,3,4) 
// val nums = 1 :: (2 :: (3 :: (4 :: Nil)))
// === nums = 1 :: 2 :: 3 :: 4 :: Nil
// the parentheses can be omitted.
// === Nil.::(4).::(3).::(2).::(1) // 点号调用方法
val diag3 = List(List(1,0,0), list(), List(2))
```

the difference between *lists* and *arrays*:

- Lists are **immutable** - the elements of a list cannot be changed.
- Lists are recursive, while *arrays* are flag.



All lists are constructed from:

- the empty list *Nil* 

- the construction operation :: (double double points, pronounced *cons*)

  (x :: xs gives a new list with the first element *x*, followed by the elements of *xs*)



```scala
def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case y :: ys => insert(y, isort(ys))
}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => if (x < xs.head) x :: xs else x :: insert(x, xs.tail)
  // case y :: ys => if (x < y) x :: xs else x :: insert(x, ys)
  // ！！！这里用 y :: ys 将 xs 这个 List 给结构了
}
```

**注意上述方法中的解构**