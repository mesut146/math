import com.mesut.math.Config;
import com.mesut.math.core.func;
import org.junit.Test;

public class DerivativeTest {

    @Test
    public void normal() {
        func f = func.parse("e^(x^2)");
        func y = func.parse("2*x*e^(x^2)");
        assert f.derivative().eq(y);
    }

    @Test
    public void numeric() {
        Config.numericDerivativePrecision = 5;

        func f = func.parse("e^(x)");
        TestUtil.assertEqual(f.derivative().eval(1), f.eval(1));
        TestUtil.assertEqual(f.numericDerivative(1), f.eval(1));

        func g = func.parse("sin(x^2+cos(x))");
        func gder = func.parse("cos(x^2+cos(x))*(2*x-sin(x))");
        TestUtil.assertEqual(g.derivative().eval(1), gder.eval(1));
        TestUtil.assertEqual(g.numericDerivative(1), gder.eval(1));
    }

}
