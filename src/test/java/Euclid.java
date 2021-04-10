import com.mesut.math.Linear;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Euclid {

    @Test
    public void asd() {
        Linear lin = new Linear(2, 1);
        List<Linear> arr = split(lin, 3);
        System.out.println(arr);
    }

    //split lin into p-1 parts that non of them is divisible by p
    List<Linear> split(Linear lin, int p) {
        List<Linear> list = new ArrayList<>();
        //a*n+b=p*k+{1,2,3,..,p-1}
        return list;
    }
}
