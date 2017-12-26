#### Lec 1.1

Programming Paradigms:（编程范式）

- imperative programming: 命令式编程 (runs well on a Von Neumann Computer. strong correspondence.)
- functional programming: 函数式编程
- logic programming: 逻辑式编程（曾用在AI编程中）

Orthogonal to it, we have object-oriented programming (OOP)



Imperative Programming: 

- modifying mutable vairables
- using assignments
- control structures (if-then-else, loops, break, continue, return)



Theory: (计算理论概念)

- one or more data types
- operations on these types
- laws that describe the relationships between values and operations

(**does not describe mutations**)

for programming, mutation will destroy useful laws in the theories. 

**THEREFORE**, concentrate on definning theories for operators expressed as functions. 

--->

**Functional programming: avoid mutations, and find ways to abstract and compose functions.**

Restricted (严格定义): FP means programming without mutable variables, assignments, loops and other imperative control structures.

Wider (广义定义): FP means focusing on the functions. (functions can be values that are produced, consumed and composed.)

这些原则可以在任何一种编程语言中实现，但是在 Functional Programming Language 中更加方便。



parallel programming: 并行编程
concurrent programming: 并发编程



scala mapreduce：get ride of mutable states.

programming in functional paradium is thinking about space not time.