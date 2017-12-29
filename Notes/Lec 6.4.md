### Lec 6.4 Maps

Map

```scala
val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")

capitalOfCountry("abc") // ==> Exception
capitalOfCountry get "abc" // ==> None
capitalOfCountry get "US" // ==> Some("Washington")

trait Option[+A]
case class Some[+A](value: A) extends Option[A]
object None extends Option[Nothing]

def showCapital(country: String) = capitalOfCountry.get(country) match {
  case Some(captital) => captial
  case None => "missing data"
}
```



Map extends function *Key => Value*



Map, flagMap, filter, groupBy, orderBy

```scala
fruit sortWith (_.length < _.length)
fruit.sorted

fruit groupBy (_.head) 
// Map(p -> List(),
//     a -> List()
//     ...])
```



Exercise: Use Map to represents polynomials:

```scala
object polynomials {
  
  // 去掉类定义中的 val，则不生成相应类属性
  class Poly(terms0: Map[Int, Double]) {
    // 设定默认值
    val terms = terms withDefaultValue 0.0
    // ++ 后者将覆盖前者的相同 key 字段
    def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))
    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp, coeff) = term
      // 由于有默认值，不会报错
      exp -> (coeff + terms(exp))
    }
    	
    // 连用高阶函数
    override def toString = 
    	(for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
    
    // repeat parameters
    // val p3 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
    def this(bindings: (Int, Double)*) = this(bindings.toMap)
    
    // 另一种 + 的实现方式
    def + (other: Poly) = 
    	new Poly((other.terms foldleft terms)(addTerm))
    def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
      val (exp, coeff) = term
      // 由于有默认值，不会报错
      terms + (exp -> (coeff + terms(exp)))
    }
  }
  
  val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2))
  val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
  p1 + p2
  
  val p3 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
  
  val cap1 = capitalOfCountry withDefaultValue "<unknown>"
  cap1("abc") // "<unknown>"
}
```



###Lec 6.5 