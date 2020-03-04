# math
a simple math library written in java

### Features:
- supports infinite multivariables
- parsing from postfix
- taking derivatives in multivariables
- numeric derivatives
- numeric integration
- outputs latex code
- basic level simplification
- get real and imaginary part of a function
- taylor series(numeric and symbolic)

## Examples:

### Derivative
```java
func f=func.parse("e^(x^2)");
System.out.println(f.derivative());
Output: (e^(x^2))*x*2
```

### Integral
```java
func f=func.parse("e^-x*x^5");//gamma
System.out.println(f.integrate("x",0,cons.INF));
Output: 120
```

### Taylor series
```java
//e^x
func f=func.parse("e^x");
System.out.println(taylor(0,5));
Output: 1+x+(x^2)*0.5+(x^3)*0.16666666666666666+(x^4)*0.041666666666666664+(x^5)*0.008333333333333333

//Lambert-W
func f=func.parse("x*e^x");
System.out.println(f.inverse().taylor(0,5));
Output: x-x^2+(x^3)*1.5-(x^4)*2.6666666666666665+(x^5)*5.208333333333333

```
### Prime numbers
```java
System.out.println(new pset(100));
Ouptut: p{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97}

System.out.println(factor.factorize(1000));
Output: 2^3*5^3
```