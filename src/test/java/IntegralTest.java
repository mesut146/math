import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import org.junit.Test;

public class IntegralTest {

    @Test
    public void gamma() {
        func f = func.parse("e^-x*x^5");
        Integral integral = new Integral(f, "x", 0, cons.INF);
        System.out.println(integral);
        System.out.println(integral.eval());//120
    }

    @Test
    public void atan() {
        func f = func.parse("1/(1+x^2)");
        Integral integral = new Integral(f, "x", cons.MINF, cons.INF);

        System.out.println(integral.eval());//pi
    }

    @Test
    public void basel() {
        func f = func.parse("-ln(1-x)/x");
        Integral integral = new Integral(f, "x", 0, 1);
        System.out.println(integral.eval());//pi^2/6
    }
}
