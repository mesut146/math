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
        interpreter.execute("a = x^2");
        interpreter.execute("1 + a(2)");
    }

    @Test
    public void multiVar() throws ParseException {
        Interpreter interpreter = new Interpreter();
        interpreter.execute("a = x + y ^ 2");
        interpreter.execute("a(x=1,y=3)");
    }
}
