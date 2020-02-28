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
- solving sequences
- get real and imaginary part of a function
- taylor series(numeric and symbolic)

## Examples:

```java
func f=func.parse("e^(x^2)");

System.out.println(f.derivative());

Output: (e^(x^2))*x*2
```

```java
func f=func.parse("e^-x*x^5");

System.out.println(f.integrate("x",0,cons.INF));

Output: 120
```
