### Lec 10.2 - Functional Reactive Programming 

Reactive programming is about reacting to sequences of events that happen in time.

Functional view: Aggregate an event sequence into a *signal*.

- A signal is a value that changes over time.
- It is represented as a fucntion from time to the value domain.
- Instead of propagating (传播) updates to mutable state, we define new signals in terms of existing ones.



Event-based view:

Whenever the mouse moves, an event *MouseMoved(toPos: Position)* is fired.

FRP view:

A signal *mousePosition: Signal[Position]* which at any point in time represents the current mouse position.



采用绑定式写法，重写例子：

 ```scala
class BankAccount extends Publisher {
   
    // Signal
    var balance = Var(0)
    def currentBalance: Int = balance()
    
    def deposit(amount: Int): Unit = 
	    if (amount > 0) {
            // 采用 balance() 重写
            val b = balance()
        	balance() = b + amount
    	}
    def withdraw(amount: Int): Unit = 
	    if (0 < amount && amount <= balance()) {
            val b = balance()
    		balance() = b - amount
    	} else throw new Error("insufficient funds")   
    
}

object accounts {
    def consolidated(accts: List[BankAccount]): Signal[Int] = 
    	Signal(accts.map(_.balance()).sum)
    
    val a = new BankAccount
    val b = new BankAccount
    val c = consolidated(List(a, b))
    
    c() // 0
    a deposit 20
    c.totalBalance // 20
    b deposit 30
    c.totalBalance // 50
    
    val exchange = Signal(246.00)
    val inDollar = Signal(c() * xchange())
    inDollar() // 12300.0
    b withdraw 10
    inDollar() // 9840.0
    
}
 ```



Advantage:

- shorter
- cleaner



```scala
val num = Var(1)
val twice = Signal(num()*2)
num() = 2 // twice = 4
num = Var(2) // twice = 2 因为保持对原信号Var(1)的依赖
```

