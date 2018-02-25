### Lec 9.1 - Functions and State

Until now, our programs have been side-effect free. Therefore, the concept of time was not important. 

**Church-Rosser Theorem of lambda calculus**: Rewriting can be done anywhere in a term, and all rewritings which terminate lead to the same solution.



With *State*, everything would be different.



#### Stateful Objects (有状态的对象)

One normally describes the world as a set of objects, some which have state that changes over the course of time.

An object *has a state* if its behavior is influenced by its history.



In scala, we use **var** in variable definition. (之前一直使用 **val**)

```scala
class BankAccount {
  private var balance = 0
  // void
  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount 
    else throw new Error("insufficient funds")
  }
  def withdraw(amount: Int): Int = 
  if (0 < amount && amount <= balance) {
    balance = balance - amount
    balance
  } else throw new Error("insufficient funds")
}

val account = new BankAccount
account deposit 50
account withdraw 40
account withdraw 20 // error

```







