### Lec 9.4 - Extended Example: Discrete Event Simulation

离散事件模拟（Discrete Event Simulation）: **Digital circuit simulator**, the simulator is based on a general framework for discrete event simulation.

Base components (gates) are: 

- Inverter
- AND gate
- OR gate

详见视频



### Lec 9.5 - Discrete Event Simulation: API and Usage

详见视频



### Lec 9.6 - Discrete Event Simulation: Implementation and Test

```scala
def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
        println(s"$name $currentTime value = ${wire.getSignal}")
        // === name + " " + ......
    }
    wire addAction probeAction
}
```



程序参数定义：

```scala
trait Parameters {
    def InverterDelay = 2
    def b = 3
    def c = 5
}

object sim extends Circuits with Parameters
```



