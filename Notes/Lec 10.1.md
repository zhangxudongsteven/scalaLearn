###Lec 10.1 - Imperative Event Handling: The Observer Pattern

imperative event handling - 命令行事件处理



- publish / subscribe
- model / view / controller



```scala
trait Publisher {
    
    // 所有订阅者
    private var subscribers: Set[Subscriber] = Set()
    
    def subscribe(subscriber: Subscriber): Unit = 
    	subscribers += subscriber
    
    def unsubscribe(subscriber: Subscriber): Unit = 
    	subscribers -= subscriber
    
    // 所有订阅者，均执行 handler 处理
    def publish(): Unit = 
    	subscribers.foreach(_.handler(this))
}

trait Subscriber {
    def handler(pub: Publisher)
}
```



下面的例子展示最原始的观察者模式：

```scala
class BankAccount extends Publisher {
    
    private var balance = 0
    def currentBalance: Int = balance
    
    def deposit(amount: Int): Unit = 
	    if (amount > 0) {
        	balance = balance + amount
            publish()
    	}
    def withdraw(amount: Int): Unit = 
	    if (0 < amount && amount <= balance) {
    		balance = balance - amount
            publish()
    	} else throw new Error("insufficient funds")   
}

class Consolidator(observed: List[BankAccount]) extends Subscriber {
    
    // 初始化
    observed.foreach(_.subscribe(this))
    private var total: Int = _
    compute()
    
    private def compute() = 
    	total = observed.map(_.currentBalance).sum
    
    def handler(pub: Publisher) = compute()
    
    def totalBalance = total
    
}

object observers {
    val a = new BankAccount
    val b = new BankAccount
    val c = new Consolidator(List(a, b))
    
    c.totalBalance // 0
    a deposit 20
    c.totalBalance // 20
    b deposit 30
    c.totalBalance // 50
}
```





Advantages:

- Decouples（解耦） views from state
- Allows to have a varying number of views of a given state
- Simple to set up

Dis:

- Forces imperative style, since handlers are Unit-typed
- Many moving parts that need to be co-ordinated（统筹）
- Concurrency（并发） makes things more complicated
- Views are bound to one state; view update happens immediately



Adobe 2008:

- 1/3 的代码使用了 event handling 模式
- 1/2 的 bugs 出现再 event handling 代码中



解决方案：

- Functional Reactive Programming (1 week)
- Abstracting over events and eventstreams with Futures and Observables (2 weeks)
- Handling concurrency with Actors (3 week)