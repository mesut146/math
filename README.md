# math
a simple math library written in java

### Features:
- supports multivariables from a-z
- parsing from postfix
- taking derivatives in multivariables
- numeric derivatives
- numeric integration
- outputs latex code
- basic level simplification
- solving sequences


## Examples:

func f=func.parse("e^(x^2)");

System.out.println(f.derivative());

Output: 2*x*e^(x^2)

func f=func.parse("e^-x*x^5");

System.out.println(f.integrate("x",0,cons.INF));

Output: 5!=120
