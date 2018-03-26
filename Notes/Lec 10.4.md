### Lec 10.4 - Latency as an Effect 

|               | One       | Many          |
| ------------- | --------- | ------------- |
| Synchronouse  | T/Try[T]  | Iterable[T]   |
| Asynchronouse | Future[T] | Observable[T] |



```scala
trait Adventure {
    def collectCoins(): List[Coin]
    def buyTreasure(coins: List[Coin]): Treasure
}

val adventure = Adventure()
val coins = adventure.collectCoins()
val treasure = adventure.buyTreasure(coins)
```

全等于

```scala
trait Socket {
    def readFromMemory(): Array[Byte]
    def sentToEurope(packet: Array[Byte]): Array[Byte]
}

val socket = Socket()
val packet = socket.readFromMemory() // 耗时太长
val confirmation = socket.sentToEurope(packet) // 需要做异步处理
```



**Future[T] 异步调用**

```scala
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

trait Future[T] {
    def onComplete(callback: Try[T] => Unit)
    	(implicit executor: ExecutionContext): Unit
}

// 一种可能的用法

ts match {
    case Success(t) => onNext(t)
    case Failure(e) => onError(e)
}

trait Future[T] {
    def onComplete(success: T => Unit, failed: Throwable => Unit): Unit
    def onComplete(callback: Observer[T]): Unit
}

trait Observer[T] {
    def onNext(value: T): Unit
    def onError(error: Throwable): Unit
}

// demo

trait Socket {
    def readFromMemory(): Future[Array[Byte]]
    def sentToEurope(packet: Array[Byte]): Future[Array[Byte]]
}

val socket = Socket()
val packet: Future[Array[Byte]] = socket.readFromMemory()

// 代码过于冗杂 “回调地狱”
packet.onComplete {
    case Success(p) => {
        val confirmation: Future[Array[Byte]] = socket.sendToEurope(p)
    }
    case Failure(t) => ...
}
```

