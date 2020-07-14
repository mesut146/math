import com.mesut.math.core.set;
import com.mesut.math.prime.PrimeGenerator;
import com.mesut.math.prime.factor;
import com.mesut.math.prime.prime;
import com.mesut.math.prime.pset;
import org.junit.Test;

import java.io.FileInputStream;

public class PrimeTest {

    String getPath() {
        return "/home/mesut/IdeaProjects/math/prime-test.bin";
    }

    @Test
    public void readFromFile() throws Exception {
        PrimeGenerator.readFrom(new FileInputStream(getPath()));
    }

    @Test
    public void generate() throws Exception {
        //PrimeGenerator.generate(new File(getPath()), 1000*1000);
        PrimeGenerator.computePrimes(1000 * 1000);
    }

    @Test
    public void setTest() {
        set set1 = new pset(100);
        set set2 = (set) set1.pow(3).mul(2).add(1);

        System.out.println(set1);
        System.out.println(set2);
        System.out.println(factor.factorize(set1));
        System.out.println(factor.factorize(set2));
    }

    @Test
    public void factor() {
        System.out.println(factor.factorize(1000).derivative());
    }


    @Test
    public void pn() {
        System.out.println(new prime(5).eval());
    }
}
