import com.mesut.math.core.func;
import com.mesut.math.diff.FirstOrder;
import org.junit.Test;

public class DiffEqTest {
    @Test
    public void diff() {
        func p = func.parse("1");
        func q = func.parse("2*e^x+e^(x^2)*(2*x+1)");
        FirstOrder fo = new FirstOrder(p, q);
        fo.solve();
    }

}
