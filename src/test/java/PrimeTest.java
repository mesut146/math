import com.mesut.math.prime.PrimeGenerator;
import org.junit.Test;

import java.io.File;
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
        PrimeGenerator.computePrimes(1000*1000);
    }
}
