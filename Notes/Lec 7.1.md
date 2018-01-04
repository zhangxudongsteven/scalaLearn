### Lec 7.1 Queries with For

The for notation is essentially equivalent to the common operations of query languages for databases. (SQL)

```scala
case class Book(title: String, authors: List[String]) // 一行数据

val books: List[Book] = List( ... )

// select title from books where "Bird" in authors
// ===
for (b <- books; a <- b.authors if a startsWith "Bird,") yield b.title

for (b <- books if b.title indexOf "Program" >= 0) yield b.title 

// 找到出版过2本书的作者
for {
  b1 <- books
  b2 <- books
  if b1 != b2 // 所有的结果出现 2 次
  // if b1.title < b2.title // 所有结果出现 1 次，但当有三本书有相同作者时，会重复出现三次
  // 用 Set 表示 books 变量，可避免重复
  a1 <- b1.authors
  a2 <- b2.authors
  if a1 == a2
} yield a1
```



### Lec 1.2 Translation of For

```scala
def mapFun[T, U](xs: List[T], f: T => U): List[U] = 
	for (x <- xs) yield f(x)
def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U] = 
	for (x <- xs; y <- f(x)) yield y
def filter[T](xs: List[T], p: T => Boolean): List[T] =
	for (x <- xs if p(x)) yield x
```

在实际实现中，我们用**函数**代替**循环**。

```scala
for (x <- e1; y <- e2; s) yield e3
// ===
e1.flatMap(x => for(y <- e2; s) yield e3)
```

通过上述方式，将 for 循环逐步展开成函数嵌套。



所以只要实现了相应 type 的 map，flatMap 与 filter，就可以使用 for 表达式操作形影对象。

Scala 对数据库做了相应实现，产生了 ScalaQuery 和 Slick 等包。基础思想借鉴于 C# LINQ。



#### DEMO: Random Value Generators

Q: What is a systematic way to get random values for other domains, such as `booleans, strings, pairs and tuples`?

```scala
trait Generator[+T] {
  
  self => // define an alias for 'this'
  
  def generate: T
  
  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }
  
  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  def generate = rand.nextInt()
}

val booleans = new Generator[Boolean] {
  def generate = integers.generate > 0
}

val pairs = new Generator[(Int, Int)] {
  def generate = (integers.generate, integers.generate)
}
```



####Scala Check 单元测试工具