### Lec 8.5 - Case Study: the Water Pouring Problem

倒水问题，抽象如下：

Glass: Int
State: Vector[Int] (one entry per glass)
Moves:

- Empty(glass)
- Fill(glass)
- Pour(from, to)

Solutions: BFS



**Name everything you can.**

**Put operations into natural scopes.**

**Keep degrees of freedom for future refinements.**



```scala
class Pouring(capacity: Vector[Int]) {
  
  // States
  type State = Vector[Int]
  val initialState = capacity map (x => 0)
  
  // Moves
  trait Move {
    def change(state: State): State
  }
  
  case class Empty(glass: Int) extends Move {
    // updated 更新某个值
    def change(state: State) = state updated (glass, 0)
  }
  case class Fill(glass: Int) extends Move {
    def change(state: State) = state updated (glass, capacity(glass))
  }
  case class Pour(from: Int, to: Int) extends Move {
    def change(state: State) = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated (from, state(from) - amount) updated (to, state(to) + amount)
    }
  }
  
  val glasses = 0 until capacity.length
  
  val moves = 
  	(for (g <- glasses) yield Empty(g)) ++
  	(for (g <- glasses) yield Fill(g)) ++
  	(for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))
  
  // Paths
  
  class Path(history: List[Move]) {
    
    // 方案一
    // def endState: State = trackState(history)
    // private def trackState(xs: List[Move]): State = xs match {
    //   case Nil => initialState
    //   case move :: xs1 => move change trackState(xs1)
    // }
    
    // 方案二（更优）
    def endState: State = (history foldRight initialState) (_ change _)
    def extend(move: Move) = new Path(move :: history)
    override def toString = (history.reverse mkString " ")  + "--> " + endState
  }
  val initialPath = new Path(Nil)
  
  // 优化的Path
  class Path(history: List[Move], val endState: State) {
    def extend(move: Move) = new Path(move :: history, move change endState)
    override def toString = (history.reverse mkString " ")  + "--> " + endState
  }
  val initialPath = new Path(Nil, initialState)
  
  
  // 关键函数
  def from(paths: Set[Path], explored: Set[State]): Stream[Set[Path]] =
  	if (paths.isEmpty) Stream.empty
 	else {
       val more = for {
         path <- paths
         next <- moves map path.extend
         if !(explored contains next.endState)
       } yield next
       paths #:: from(more, explored ++ (more map (_.endState)))
  	}
  
  val pathSets = from(Set(initialPath), Set(initialState))
  
  def solutions(target: Int): Stream[Path] = 
  	for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
  	} yield path
}

object test {
  val problem = new Pouring(Vector(4, 7, 9))
  problem.moves // all possible moves
  
  problem.pathSets // Stream(*, ?)
  problem.pathSets.take(3) // 获取三步之内所有可能
  
  problem.solutions(6) // 给出计算结果
}
```

