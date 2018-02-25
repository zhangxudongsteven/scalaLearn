package quickcheck

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    num <- arbitrary[Int]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(num, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("min1") = forAll(arbitrary[Int]) { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("gen1") = forAll(genHeap) { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  // If you insert any two elements into an empty heap,
  // finding the minimum of the resulting heap should get the smallest of the two elements back.
  property("test1") = forAll(arbitrary[Int], arbitrary[Int]) { (a: Int, b: Int) =>
    val h1 = insert(a, empty)
    val h2 = insert(b, h1)
    val m = findMin(h2)
    if (a < b) m == a else m == b
  }

  // If you insert an element into an empty heap,
  // then delete the minimum, the resulting heap should be empty.
  property("test2") = forAll(arbitrary[Int]) { a: Int =>
    val h1 = insert(a, empty)
    val h2 = deleteMin(h1)
    isEmpty(h2)
  }

  // Given any heap, you should get a sorted sequence of elements
  // when continually finding and deleting minima.
  // (Hint: recursion and helper functions are your friends.)
  property("test3") = forAll(genHeap) { (h: H) =>
    val seq = getSeq(h, Nil)
    seq == seq.sorted
  }

  def getSeq(heap: H, seq: List[Int]): List[Int] = {
    if (isEmpty(heap)) seq else findMin(heap) :: getSeq(deleteMin(heap), seq)
  }

  // Finding a minimum of the melding of any two heaps
  // should return a minimum of one or the other.
  property("test4") = forAll(genHeap, genHeap) { (h1: H, h2: H) =>
    val h = meld(h1, h2)
    val m = findMin(h)
    m == findMin(h1) || m == findMin(h2)
  }

  // delete element and insert to another, result should be the same.
  property("test5")= forAll(genHeap, genHeap) { (h1: H, h2: H) =>
    val m = findMin(h1)
    val xs1 = getSeq(meld(h1, h2), Nil)
    val xs2 = getSeq(meld(deleteMin(h1), insert(m, h2)), Nil)
    xs1 == xs2
  }

}
