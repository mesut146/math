import com.mesut.math.core.func;
import org.junit.Test;

public class ImaginaryTest {

    @Test
    public void euler() {
        func f = func.parse("e^(i*x)");
        System.out.println(f.getReal());
        System.out.println(f.getImaginary());
    }

    @Test
    public void log() {
        func f = func.parse("ln(a+b*i)");
        System.out.println(f.getReal());
        System.out.println(f.getImaginary());
    }

    @Test
    public void test1() {
        func f = func.parse("ln(x+i)^i");
        System.out.println(f.getReal());
        System.out.println(f.getImaginary());
    }

    @Test
    public void test2() {
        func f = func.parse("ln(1-t*e^(i*x*n))");
        System.out.println(f.getReal());
        System.out.println(f.getImaginary());
    }
}
