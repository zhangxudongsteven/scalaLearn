### Lec 10.3 - A Simple FRP Imple

```scala
class Signal[T](expr: => T) {
    def apply(): T = ???
}
object Signal {
    def apply[T](expr: => T) = new Signal(expr)
}

class Var[T](expr: => T) extends Signal[T](expr) {
    def update(expr: => T): Unit = ???
}
object Var {
    def apply[T](expr: => T) = new Var(expr)
}
```



Each signal maintains

- its current value,
- the current expression that defines the signal value,
- a set of *observers*: the other signals that depend on its value.

**if the signal changes, all observers need to be re-evaluated.**



每次 sig value 变化，所有 observers 的变化将被触发，当前 sig 的 set 将被清理。所有 observers 的 re-evaluation 将重新注册当前 sig 的 observer。（因为重新计算后，原观察者可能不再保留 observer 身份）



We use a stackable variables to track variable dependency.

```scala
class StackableVariable[T](init: T) {
    private var values: List[T] = List(init)
    def value: T = values.head
    def withValue[R](newValue: T)(op: => R): R = {
        values = newValue :: values
        try op finally values = values.tail
    }
}
object {
    val caller = new StackableVar(initalSig)
    caller.withValue(otherSig) { ... }
    ... caller.value ...
}
```



第一种FRP实现方案利用了全局变量，这导致该方案减分。（必须保证线程间不干扰）

(We will face trouble when try to evaluate serveral signal expressions in parallel.)

解决方案：

- synchronization：将导致线程等待、死锁等
- thread-local state：each thread accesses a separate copy of a variable. (Java.util.thread & scala.util.DynamicVariable. avoid race conditions that the global variable would entail.)（JDK use global hash table, has performance problem, and other disadvantages.）
- use implicit parameters: directly pass current value into signal expression. (use scala *Future*)





