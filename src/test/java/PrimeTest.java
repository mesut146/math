import com.mesut.math.core.set;
import com.mesut.math.prime.PrimeGenerator;
import com.mesut.math.prime.factor;
import com.mesut.math.prime.prime;
import com.mesut.math.prime.pset;
import org.junit.Test;

import java.io.FileInputStream;

public class PrimeTest {

    @Test
    public void readFromFile() throws Exception {
        String path = "/home/mesut/IdeaProjects/math/prime-test.bin";
        PrimeGenerator.readFrom(new FileInputStream(path));
    }


    @Test
    public void generate() throws Exception {
        String path = "/home/mesut/IdeaProjects/math/prime-test.bin";
        //PrimeGenerator.generate(new File(path), 1000*1000);
        PrimeGenerator.computePrimes(1000 * 1000);
    }

    @Test
    public void setTest() {
        set s = new pset(100);
        System.out.println(s);
        s = (set) s.pow(3).mul(2).add(1);
        System.out.println(s);
        System.out.println(factor.factorize(s));
        System.out.println(new prime(2).eval());
    }
}
