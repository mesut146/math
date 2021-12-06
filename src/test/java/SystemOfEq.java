import com.mesut.math.core.func;
import com.mesut.math.solver.LinearSystem;
import org.junit.Test;

public class SystemOfEq {

    @Test
    public void test() {
        LinearSystem system = new LinearSystem();
        system.add("a+b+c", 3);
        system.add("4*a+2*b+c", 5);
        system.add("8*a+4*b+c", 7);

        system.solve();
        System.out.println(system.printSolution());
    }

    @Test
    public void test2() {
        //predict primes
        LinearSystem system = new LinearSystem();
        system.add("a+b+c+d", 3);
        system.add("8*a+4*b+2*c+d", 5);
        system.add("27*a+9*b+3*c+d", 7);
        system.add("64*a+16*b+4*c+d", 11);
        system.add("64*a+16*b+4*c+d+f", 13);

        system.solve();
        System.out.println("sol=" + system.printSolution());
        func f = system.makeLin();
        System.out.println("lin=" + f);
        for (int i = 1; i < 10; i++) {
            System.out.println(Math.round(f.eval(i)));
        }

    }
}
