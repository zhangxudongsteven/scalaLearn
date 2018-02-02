### Lec 8.1 Structural Induction on Trees

同样采用**数学归纳法**来推导：

- show that p(1) holds for all leaves 1 of a tree
- for each type of internal node t with subtrees s1, … ,sn show that P(s1)^…^P(sn) implies P(t)



对于二叉树来说，需要满足一下三点条件：

- Empty contains x = false
- (s incl x) contains x = true
- (s incl x) contains y = s contains y if x != y



其它例子，详见视频



### Lec 8.2 Streams

**Collections and Combinatorial Search**:

for instance, to find the second prime number between 1000 and 10000:

```scala
((1000 to 10000) filter isPrime)(1)
```

(1000 to 10000) will constructs all prime numbers between 1000 and 10000 in a list. **Inefficient**

#### Stream

Streams are similar to lists, but their tail is evaluated only on demand.

```scala
// 实际定义
val xs = Stream.cons(1, Stream.cons(2, Stream.empty))

// 工厂类定义
Stream(1, 2, 3)

// 转换
(1 to 1000).toStream
((1000 to 10000).toStream filter isPrime)(1)
```

```scala
// 下面的函数将不会生成整个数组，而是只有头对象。在需要的时候，才会生成。
def streamRange(lo: Int, hi: Int): Stream[Int] = 
	if (lo >= hi) Stream.empty
	else Stream.cons(lo, streamRange(lo + 1, hi))

x :: xs // 返回list，而非stream
x #:: xs == Stream.cons(x, xs) // 返回stream
// #:: 操作符：can be used in expressions as well as patterns.
```



#### Implementation of Streams

```scala
trait Stream[+A] extends Seq[A] {
  def isEmpty: Boolean
  def head: A
  def tail: Stream[A]
  ...
}

object Stream {
  // tl is call by name
  def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
    def isEmpty = false
    def head = hd
    def tail = tl
  }
  val empty = new Stream[Nothing] {
    def isEmpty = true
    def head = error
    def tail = error
  }
}
```

 

#### Lec 8.3 Lazy Evaluation

**do things as late as possible and never do them twice.**

Scala uses strict evaluation by default, but allows lazy evaluation of value definitions with the *lazy val* form:

```scala
lazy val x = expr // 使用时计算，并保存下来
def x = expr // call-by-name，每次使用时计算

def expr = {
  val x = { print("x"); 1 }
  lazy val y = { print("y"); 2 }
  def z = { print("z"); 3 }
  z + Y + x + z + y + x // print xzyx
}

// stream
def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
    def isEmpty = false
    def head = hd
    // def tail = tl
  	lazy val tail = tl
  }

def apply(n: Int): T = if (n == 0) head else tail.apply(n - 1)


streamRange(1000, 10000).filter(isPrime).apply(1)
// == 
streamRange(1009, 10000).filter(isPrime).apply(0)
```



#### Lec 8.4 Computing with Infinite Sequences

The stream of all natural numbers:

```scala
def from(n: Int): Stream[Int] = n #:: from(n+1)

// all natural numbers
val nats = from(0) // Stream[Int] = Stream(0, ?)
val m4s = nats map (_ * 4) // Stream[Int]
(m4s take 10).toList // 0, 4, 8 ...
```



**The Sieve of Eratosthenes**: 筛数法求质数

```scala
def sieve(s: Stream[Int]): Stream[Int] = 
	s.head #:: sieve(s.tail filter (_ % s.head != 0))

val primes = sieve(from(2))

primes.take(100).toList
```



```scala
// 思考时带入guesses计算过程
def sqrtStream(x: Double): Stream[Double] = {
  def improve(guess: Double) = (guess + x / guess) / 2
  lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
  guesses
}
sqrtStream(4).take(10).toList
```





