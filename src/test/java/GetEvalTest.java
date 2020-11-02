import com.mesut.math.core.func;
import org.junit.Assert;
import org.junit.Test;

public class GetEvalTest {

    @Test
    public void eval1() {
        func f = func.parse("sqrt(a^2+b^2)");
        Assert.assertEquals(f.eval("a=3,b=4"), 5, 0);
        Assert.assertEquals(f.eval("a,b", 3, 4), 5, 0);
    }

    @Test
    public void substitute() {
        func f = func.parse("sqrt(a^2+b^2)");
        Assert.assertEquals(f.substitute("a=5"), func.parse("sqrt(25+b^2)"));
        Assert.assertEquals(f, func.parse("sqrt(a^2+b^2)"));
    }

}
