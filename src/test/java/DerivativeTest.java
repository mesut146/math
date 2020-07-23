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
        System.out.println(f.derivative().eval(1));
        System.out.println(f.numericDerivative(1));

        f = func.parse("sin(x^2+cos(x))");
        System.out.println(f.derivative().eval(1));
        System.out.println(f.numericDerivative(1));
    }

}
