import com.mesut.math.core.func;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    @Test
    public void unary() {
        Assert.assertEquals(-8, (int) func.parse("-2^3").eval());
    }

    @Test
    public void fac() {
        Assert.assertEquals(120, (int) func.parse("5!").eval());
        Assert.assertEquals(24, (int) func.parse("(1+3)!").eval());
    }

    @Test
    public void prec() {
        Assert.assertEquals(7, (int) func.parse("1+2*3").eval());
        Assert.assertEquals(32, (int) func.parse("2*2^4").eval());
        Assert.assertEquals(-8, (int) func.parse("-2^3").eval());
        Assert.assertEquals(64, (int) func.parse("2^3!").eval());
    }

    @Test
    public void func() {
        Assert.assertEquals(1, func.parse("sin(pi/4)^2+cos(pi/4)^2").eval(), 0.00001);
    }

    @Test
    public void eq() {
        func f = func.parse("f(x,y) = x^2+y");
        System.out.println(f);
    }

    @Test
    public void eq2() {
        func f = func.parse("f(y=1,x=sin(x))");
        System.out.println(f);
    }
}
