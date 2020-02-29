import com.mesut.math.core.func;
import com.mesut.math.funcs.inv;
import org.junit.Test;

public class TaylorTest {
    @Test
    public void taylor() {
        func f = func.parse("x*e^x");
        //System.out.println(taylor.numeric(f,0,10));
        /*System.out.println(f);
        System.out.println(f.derivative());*/
        System.out.println(new inv(f).taylor(0, 5));

        //System.out.println(taylorsym.symbol("ln(x)","x",1,8));
    }
}
