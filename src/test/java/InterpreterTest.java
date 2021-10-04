import com.mesut.math.Interpreter;
import com.mesut.math.parser.ParseException;
import org.junit.Test;

public class InterpreterTest {

    @Test
    public void calls() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("1 + a(2)");
        interpreter.execute("b = a + 1");
        interpreter.execute("b(y)");
    }

    @Test
    public void deriv() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("derivative(a) + 3");
        interpreter.execute("der(a)+4");
        interpreter.execute("der(x^3+y^4,y)");
    }

    @Test
    public void integral() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("b=int(a,x,1,3)");
        interpreter.execute("b");
    }

    @Test
    public void multiVar() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x + y ^ 2");
        interpreter.execute("a(x=1,y=3)");
        interpreter.execute("b = a(x=1,y=y+1)");
        interpreter.execute("b");
    }

    @Test
    public void factor() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("factor(100)");
        interpreter.execute("a = factor(100)");
        interpreter.execute("b = factor(100) * factor(200)");
        interpreter.execute("b");
    }

    @Test
    public void prime() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("prime(1)");
        interpreter.execute("prime(2)");
        interpreter.execute("prime(3)");
        interpreter.execute("prime(10)");
        interpreter.execute("prime(348513)");
    }

    @Test
    public void primeSet() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("pset(10)");
        interpreter.execute("pset(100)");
        interpreter.execute("pset(10)^2");
        interpreter.execute("pi(100)");
        interpreter.execute("pi(100)+1");
    }

    @Test
    public void set() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("set(1,10)");
        interpreter.execute("factor(set(1,10))");
        interpreter.execute("factor(set(1,10) * 2 + 1)");
    }

    @Test
    public void countPrime() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("countPrime(set(50,100))");
        interpreter.execute("pset(50,100)");
        interpreter.execute("countPrime(set(1,50)^2+1)");
        interpreter.execute("factor(set(1,50)^2+1)");
    }
}
