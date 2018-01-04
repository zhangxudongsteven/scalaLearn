### Lec 7.1 Recap

**Example**: representing JSON (JavaScript Object Notation)

```scala
abstract class JSON
case class JSeq(elems: List[JSON]) extends JSON
case class JObj(bindings: Map[String, JSON]) extends JSON
case class JNum(num: Double) extends JSON
case class JStr(str: String) extends JSON
case class JBool(b: Boolean) extends JSON
case class JNull extends JSON

// example
val data = JObj(Map(
  "firstName" -> JStr("John"),
  "phoneNumbers" -> JSeq(List(
    JObj(Map(
      "type" -> JStr("home")
    ))
  ))
))

def show(json: JSON): String = json match {
  case JSeq(elems) =>
  	"[" + (elems map show mkString ",") + "]"
  case JObj(bindings) =>
  	val assocs = bindings map {
      // Q: what is the type of this function
      // A: JBingding (type JBingding = (String, JSON)) => String
      // === scala.Function1[JBinding, String] (which is a trait as next block)
      case (key, value) => "\"" + key + "\":" + show(value)
  	}
  	"{" + (assocs mkString ",") + "}"
  case JNum(num) => num.toString
  ...
}
```



Map 的定义：

```scala
trait Map[Key, Value] extends (Key => Value)
trait Seq[Elem] extends (Int => Elem) // elems(i) ----> this is function

val f: String => String { case "ping" => "pong"}
f("ping") // "pong"
f("abc") // match error

val p: PartialFunction[String, String] { case "ping" => "pong"}
p.isDefinedAt("ping") // true
p.isDefinedAt("pong") // false

trait PartialFunction[-A, +R] extends Function1[-A, +R] {
  def apply(x: A): R
  def isDefinedAt(x: A): Boolean
}
```



#### For

```scala
for (x <- e1) yield e2
// ===
e1.map(x => e2)

for (x <- e1 if f; s) yield e2
// ===
for (x <- e1.withFilter(x => f); s) yield e2

for (x <- e1; y <- e2; s) yield e3
// ===
e1.flatMap(x => for (y <- e2; s) yield e3)


val data: List[JSON] = ...
for {
  JObj(bindings) <- data // use match pattern
  JSeq(phones) <- bindings("phoneNumbers") // use match pattern
  JObj(phone) <- phones // use match pattern
  JStr(digits) <- phone("number") // use match pattern
  if digit startsWith "212"
} yield (binding("firstName"), binding("lastName"))
```



