import com.mesut.math.Interpreter;
import com.mesut.math.parser.MathParser;
import com.mesut.math.parser.ParseException;
import org.junit.Test;

import java.io.StringReader;

public class InterpreterTest {
    @Test
    public void parse() throws ParseException {
        String line = "x = sin(y)";

        MathParser parser = new MathParser(new StringReader(line));
        System.out.println(parser.equation());
    }

    @Test
    public void interpret() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = sin(x)");
        interpreter.execute("a = a + cos(y)");
        interpreter.execute("a");
        interpreter.execute("b = a.derivative() + cos(x^2)");
        interpreter.execute("b(3)");
    }
}
