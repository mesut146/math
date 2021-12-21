import com.mesut.math.ipret.Interpreter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class InterpreterTest {

    @Test
    public void calls() throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("1 + a(2)");
        interpreter.execute("b = a + 1");
        interpreter.execute("b(y)");
    }

    @Test
    public void deriv() throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("derivative(a) + 3");
        interpreter.execute("der(a)+4");
        interpreter.execute("der(x^3+y^4,y)");
    }

    @Test
    public void integral() throws IOException {
        Interpreter interpreter = new Interpreter();
        //interpreter.execute("a = x^2");
        //interpreter.execute("b=int(a,x,1,3)");
        //interpreter.execute("b");

        //interpreter.execute("int(ln(1-x)/x,x,-1,1)");
        //interpreter.execute("int(e^(-x^2),x,0,inf)");
    }

    @Test
    public void multiVar() throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x + y ^ 2");
        Assert.assertEquals("10", interpreter.execute("a(x=1,y=3)").text);//10
        interpreter.execute("b = a(x=1,y=y+1)");
        Assert.assertEquals("1+(y+1)^2", interpreter.execute("b").text);
    }

    @Test
    public void factor() throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("factor(100)");
        interpreter.execute("a = factor(100)");
        interpreter.execute("b = factor(100) * factor(200)");
        interpreter.execute("b");//20000
    }

    @Test
    public void prime() throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("prime(1)");//2
        interpreter.execute("prime(2)");//3
        interpreter.execute("prime(3)");//5
        interpreter.execute("prime(10)");//29
        interpreter.execute("prime(348513)");//4999999
    }

    @Test
    public void primeSet() throws  IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("pset(10)");//p{2, 3, 5, 7}
        interpreter.execute("pset(100)");
        interpreter.execute("pset(50,100)");
        interpreter.execute("pset(10)^2");
        Assert.assertEquals("25", interpreter.execute("pi(100)").text);
        Assert.assertEquals("26", interpreter.execute("pi(100)+1").text);
    }

    @Test
    public void set() throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("set(1,10)");
        interpreter.execute("factor(set(1,10))");
        interpreter.execute("factor(set(1,10) * 2 + 1)");
    }

    @Test
    public void countPrime() throws IOException {
        Interpreter interpreter = new Interpreter();
        Assert.assertEquals("10", interpreter.execute("countPrime(set(50,100))").text);
        Assert.assertEquals("11", interpreter.execute("countPrime(set(1,50)^2+1)").text);
        interpreter.execute("factor(set(1,50)^2+1)");
    }

}
