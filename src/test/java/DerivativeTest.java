import com.mesut.math.core.func;
import org.junit.Test;

public class DerivativeTest {

    @Test
    public void test1() {
        func f = func.parse("e^(x^2)");
        System.out.println(f.derivative());
    }

    @Test
    public void numeric() {
        func f = func.parse("e^(x)");
        System.out.println(f.derivative().eval(1));
        System.out.println(f.numericDerivative(1));
    }

    @Test
    public void dif1() {
        //Config.mul.simplify=false;
        func f;
        //f=func.parse("f'(x)+f(x)*p(x)-q(x)");
        f = func.parse("1/(3*x^2-3)");
        System.out.println(f);
        System.out.println(f.derivative(2));
    }
}
