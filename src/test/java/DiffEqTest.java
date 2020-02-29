import com.mesut.math.core.func;
import com.mesut.math.diff.FirstOrder;
import org.junit.Test;

public class DiffEqTest {
    @Test
    public void diff() {
        FirstOrder fo = new FirstOrder();
        fo.p = func.parse("1");
        fo.q = func.parse("2*e^x+e^(x^2)*(2*x+1)");
        fo.solve();
    }

}
