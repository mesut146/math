import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import org.junit.Test;

public class GetEvalTest {

    @Test
    public void eval1() {
        func f = func.parse("sqrt(a^2+b^2)");
        assert f.eval("a=3,b=4") == 5;
        assert f.eval("a,b", 3, 4) == 5;
    }


}
