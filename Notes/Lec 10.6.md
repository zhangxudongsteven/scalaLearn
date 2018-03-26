### Lec 10.6 - Combinators on Futures

##### Future 高级函数

```scala
trait Future[T] extends Awaitable[T] {
    def filter(p: T=>Boolean): Future[T]
    def flatMap[S](f: T=>Future[S]): Future[U]
    def map[S](f: T=>S): Future[S]
    def recoverWith(f: PartialFunction[Throwable, Future[T]]): Future[T]
}

object Future {
    def apply[T](body: => T): Future[T]
}
```



原代码：

```scala
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

改进 Socket 过程为：

```scala
val socket = Socket()
val packet: Future[Array[Byte]] = socket.readFromMemory()
val confirmation: Future[Array[Byte]] = 
	packet.flatMap(p => socket.sendToEurope(p))
```



采用高级函数改进：

```scala
def sendToSafe(packet: Array[Byte]): Future[Array[Byte]] = 
	sendTo(mailServer.europe, packet) fallbackTo {
    	sendTo(mailServer.usa, packet)
	} recover {
    	case europeError => europeError.getMessage.toByteArray
	}
```



scala也提供阻塞方式：

```scala
trait Awaitable[T] extends AnyRef {
    abstract def ready(atMost: Duration): Unit
    abstract def result(atMost: Duration): T
}
```



####参照教材 P690



scala 时间表示

```scala
import scala.language.postfixOps

object Duration {
    def apply(length: Long, unit: TimeUnit): Duration
}

// example
val fiveYears = 1826 minutes
```

