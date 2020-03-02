import com.mesut.math.core.func;
import com.mesut.math.core.var;
import com.mesut.math.funcs.inv;
import com.mesut.math.taylor;
import org.junit.Test;

public class TaylorTest {

    @Test
    public void inverse() {
        func f = func.parse("x*e^x");
        //System.out.println(taylor.numeric(f,0,10));
        /*System.out.println(f);
        System.out.println(f.derivative());*/
        System.out.println(new inv(f).taylor(0, 5));

        //System.out.println(taylorsym.symbol("ln(x)","x",1,8));
    }

    @Test
    public void taylor1() {
        func f = func.parse("e^x");
        taylor taylor = new taylor(f, 0);
        taylor.calc(10);
        System.out.println(taylor);
        System.out.println(taylor.derivative(var.x));

        //System.out.println(taylorsym.symbol("ln(x)","x",1,8));
    }
}
