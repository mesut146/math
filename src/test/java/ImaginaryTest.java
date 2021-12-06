import com.mesut.math.Config;
import com.mesut.math.core.func;
import org.junit.Assert;
import org.junit.Test;

public class ImaginaryTest {

    @Test
    public void euler() {
        func f = func.parse("e^(i*x)");
        Assert.assertEquals(f.getReal(), func.parse("cos(x)"));
        Assert.assertEquals(f.getImaginary(), func.parse("sin(x)"));
    }

    @Test
    public void log() {
        func f = func.parse("ln(a+b*i)");
        Config.lnFullImaginary = true;
        Assert.assertEquals(f.getReal(), func.parse("ln(a^2+b^2)/2"));
        Assert.assertEquals(f.getImaginary(), func.parse("atan(b/a)+pi*n*2"));
    }

    @Test
    public void ii() {
        Config.lnFullImaginary = false;
        func f = func.parse("i^i");
        Assert.assertEquals(func.parse("e^(-pi/2)"), f.getReal());
        Assert.assertEquals(func.parse("0"), f.getImaginary());
    }

    @Test
    public void test1() {
        func f = func.parse("ln(x+i)^i");
        System.out.println(f.getReal());
        System.out.println(f.getImaginary());
    }

    @Test
    public void test2() {
        func f = func.parse("ln(1-t*e^(i*x))");
        System.out.println(f.getReal());
        System.out.println(f.getImaginary());
        System.out.println(f.getReal().eval("t=1,x=2"));
        System.out.println(f.get("t=1,x=2").getReal());
    }
}
