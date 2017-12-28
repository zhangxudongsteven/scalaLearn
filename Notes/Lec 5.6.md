### Lec 5.6 Reasoning About Concat

**Here, We prove equational properties of programs using stuctural induction.**



this will verify that concatenation is associative:

```scala
(xs ++ ys) ++ zs = xs ++ (ys ++ zs)
xs ++ Nil = xs
Nil ++ xs = xs
```

*Q*: How can we prove properties like these?

*A*: By **structural induction** on lists.



natural induction: 自然归纳法



**Referential Transparency**: [参照透明性](https://en.wikipedia.org/wiki/Referential_transparency)

我们将用归纳法证明参照透明性

```scala
// Base case
(Nil ++ ys) ++ zs == ys ++ zs
Nil ++ (ys ++ zs) == ys ++ zs

// Induction Step: (LHS)
((x :: xs) ++ ys) ++ zs
= (x :: (xs ++ ys)) ++ zs // by 2nd clause of ++
= x :: ((xs ++ ys) ++ zs) // by 2nd clause of ++
= x :: (xs ++ (ys ++ zs)) // by induction hypothesis

// Induction Step: (RHS: right hand side)
(x :: xs) ++ (ys ++ zs)
= x :: (xs ++ (ys ++ zs)) 
```



#### Larger Proof

prove: 

```scala
xs.reverse.reverse = xs

Nil.reverse = Nil // 1st clause
(x :: xs).reverse = xs.reverse ++ List(x) // 2nd clause
```



base case:

```scala
Nil.reverse.reverse
= Nil.reverse
= Nil
```



indection step:

```scala
(x :: xs).reverse.reverse
= (xs.reverse ++ List(x)).reverse // left-hand
= x :: xs.reverse.reverse


=== 

(ys ++ List(x)).reverse = x :: ys.reverse

// Base: 
(Nil ++ List(x)).reverse 
= List(x).reverse 
= (x :: Nil).reverse
= Nil.reverse ++ List(x)
= Nil ++ (x :: Nil)
= x :: Nil
= x :: Nil.reverse // finish, proved

// Inductive Step
((y :: ys) ++ List(x)).reverse
= ...
= x :: (y :: ys).reverse


// ！！！看视频理解
```

