### Lec 6.1 Other Collections 

**共性：Immutable**



*Vector*：use narrow tree. (32 elements per level)

1 level: 2^5 = 32

2 level: 2^10

...

6 level: 2^30 = 1G

access complexcity = log32(n)



**if the access patterns have recursive structures, lists are better.**

**if the access patterns are bulk operation (map, fold, filter), vectors are better.**

```scala
val nums = Vector(1, 2, 3)
// 所有 List 函数均可作用于 Vector，除了 ::
// Vector 用 :+ 与 +: 代替 List 的 ::
```



List Vector Range (and Array & String from Java namespace) **<:** Seq Set Map **<:** Iterable

```scala
val xs = Array(1, 2, 3, 4)
xs map (x => x * 2)
val s = "Hello World"
s filter (c => c.isUpper)
```



Range:

```scala
val r: Range = 1 until 5 // 1,2,3,4
val s: Range = 1 to 5 // 1,2,3,4,5
1 to 10 by 3 // 1,4,7,10
6 to 1 by -2 // 6,4,2
```



functions for sequence:

```scala
xs exists f
xs forall f
xs zip f // create pairs
xs unzip // create two lists from pairs
xs flatMap f

sum, product, max, min

def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
	(xs zip ys).map(xy => xy._1 * xy._2).sum
// === !!!
def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
	(xs zip ys).map{ case (x, y) => x * y }.sum
// { case (x, y) => x * y } === x => x match { case (x, y) => x * y }

def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d != 0)


// use for loop
def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
	(for ((x, y) <- xs zip ys) yield x * y).sum
```



### Lec 6.2 Combinatorial Search and For-Expressions

我们可以用上述高级函数取代循环等操作。

```scala
// flatten 展开各个集合
((1 until n) map (i => (1 until i) map (j => (i, j)))).flatten 
// ===
(1 until n) flagMap (i => (1 until i) map (j => (i, j)))

// 习题最终结果
(1 until n) flagMap (i => (1 until i) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))

// use for loop
for {
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)
```



For

```scala
case class Person(name: String, age: Int)

persons filter (p => p.age > 20) map (p => p.name)
// ===
for ( p <- persons if p.age > 20 ) yield p.name
// for is more readable
```



### Lec 6.3 Combinatorial Search Example

**Sets**: 

```scala
val s = (1 to 6).toSet
val fruit = Set("apple", "banana", "pear")

s map (_ + 2)
fruit filter (_.startWith == 'pe')
```

Differences between sets and sequences:

- Sets are unordered
- Sets do not have duplicated elements
- fundamental operation on sets is *contains*



Example: N-Queens

```scala
object nqueens {
  def queens(n: Int): Set[List[Int]] = {
    
    def placeQueens(k: Int): Set[List[Int]] = 
    	if (k == 0) Set(List()) 
    	else 
    		for {
      			queens <- placeQueens(k - 1)
              	col <- 0 until n
              	if isSafe(col, queens)
    		} yield col :: queens
    
    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      val queensWithRow = (row - 1 to 0 by -1) zip queens
      queensForRow forall {
        case (r, c) => col != c && math.abs(col - c) != row - r
      }
    }
    
    def show(queens: List[Int]) = {
      val lines = 
      	for (col <- queens.reverse)
      	yield Vector.fill(queens.length("* ")).updated(col, "X ").mkString
      "\n" + (lines mkString "\n")
    }
    
    (placeQueens(4) map show) mkString "\n"
    (placeQueens(8) take 3 map show) mkString "\n"
  }
}
```

