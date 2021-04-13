import com.mesut.math.Interpreter;
import com.mesut.math.parser.ParseException;
import org.junit.Test;

public class InterpreterTest {

    @Test
    public void calls() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("1 + a(2)");
    }

    @Test
    public void deriv() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x^2");
        interpreter.execute("a.derivative() + 3");
    }

    @Test
    public void multiVar() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x + y ^ 2");
        interpreter.execute("a(x=1,y=3)");
    }
}
