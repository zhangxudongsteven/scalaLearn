### Lec 9.2 - Identity and Change



Q: how to decide whether two expressions are "the same"? ("same" is defined by the property of **operational equivalence**, -> *no possible test can distinguish between them*)

**referential transparency**: (参照透明度)

```scala
val x = E // E is an arbitrary expression
val y = E
// ===
val x = E
val y = x
```



Once we allow the assignment, the two formulations are different.

只要对象含有私有属性，而非纯过程，则两个对象不相同。



#### Assignment and The Substitution Model

如果加入 Assignment 动作，代换模型将不再合法，程序将不能以面向过程的思想来演绎。We will move from a pure functional programming to a combination of functional and imperative programming(命令式编程).



### Lec 9.3 - Loops

命令式编程的重要特性：循环

在 Scala 中，三种循环将被编译成 Scala 内置的 high level functions.

```scala
// 关键字
while (i > 0) {
    r = r * x
    i = i - 1
} r
do {
    command
} while (condition)

// 函数式实现
@TailRec
def WHILE(condition: => Boolean)(command: => Unit): Unit = 
if (condition) {
    command
    WHILE(condition)(command)
}

def REPEAT(command: => Unit)(condition: => Boolean) = {
    command
    if (condition) ()
    else REPEAT(command)(condition)
}

// for
for (int i = 1; i < 3; i = i + 1) {} // scala不支持
for (i <- 1 until 3) {} // 1, 2

for (i <- 1 until 3; j <- "abc") {}
// ===>
(1 until 3) foreach (i => "abc" foreach (j => ( println(i + " " + j)))
                          ----------------
```



















