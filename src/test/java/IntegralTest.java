import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import org.junit.Test;

public class IntegralTest {

    @Test
    public void numeric1() {
        func f = func.parse("e^-x*x^5");
        Integral integral = new Integral(f, "x", 0, cons.INF);
        System.out.println(integral);
        System.out.println(integral.eval());
    }
}
