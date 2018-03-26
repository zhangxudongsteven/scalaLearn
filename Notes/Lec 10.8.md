### Lec 10.8 Composing on Futures

Future 中 flatMap 的实现：

```scala
trait Future[T] { self =>
    def flatMap[S](f: T => Future[S]): Future[S] =
	    new Future[S] {
        	def onComplete(callback: Try[S] => Unit): Unit = 
	            self onComplete {
        			case Success(x) => f(x).onComplete(callback)
                    case Failure(e) => callback(Failure(e))
    	        }
    	}
}
```



