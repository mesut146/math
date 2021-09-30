import com.mesut.math.core.set;
import com.mesut.math.prime.PrimeGenerator;
import com.mesut.math.prime.factor;
import com.mesut.math.prime.pset;
import org.junit.Assert;
import org.junit.Test;

public class PrimeTest {

    @Test
    public void generate() throws Exception {
        //PrimeGenerator.generate(new File(getPath()), 1000*1000);
        PrimeGenerator.computePrimes(1000 * 1000);
    }

    @Test
    public void pset() {
        set s = new pset(50, 100);
        System.out.println(s.print());
    }

    @Test
    public void set() {
        set set1 = new pset(1000);
        set set2 = (set) set1.pow(3).mul(2).add(1);

        //System.out.println(set1);
        System.out.println(set2);
        System.out.println(factor.factorize(set1));
        System.out.println(factor.factorize(set2));
    }

    @Test
    public void factor() {
        for (int i = 1000; i < 10000; i++) {
            factor f = factor.factorize(i);
            Assert.assertEquals(i, f.eval());
        }
    }
}
