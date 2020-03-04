import com.mesut.math.core.func;
import com.mesut.math.taylor;
import com.mesut.math.taylorsym;
import org.junit.Test;

public class TaylorTest {

    @Test
    public void lambert() {
        func f = func.parse("x*e^x");
        func inv = f.inverse();

        System.out.println(inv.taylor(0, 7));
        System.out.println(f.inverse().taylor(0,5));
    }

    @Test
    public void taylor1() {

        func f = func.parse("exp(x)");
        taylor taylor = new taylor(f, 0);
        taylor.calc(5);
        System.out.println(taylor);
        System.out.println(taylor.derivative("x"));

    }

    @Test
    public void symbol1() {

        func f = func.parse("sin(x)");
        taylor taylor = new taylorsym(f, 0);
        taylor.calc(10);
        System.out.println(taylor);
        System.out.println(taylor.derivative("x"));

    }
}
