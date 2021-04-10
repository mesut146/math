import com.mesut.math.Linear;
import org.junit.Test;

public class Col {

    static Linear next(Linear linear) {
        if (linear.isOdd()) {
            return linear.mul(3).add(1);
        }
        if (linear.isEven()) {
            return linear.div(2);
        }
        return linear;
    }

    @Test
    public void name() {
        Linear linear = new Linear(2, 1);
        for (int i = 0; i < 5; i++) {
            linear = next(linear);
            System.out.println(linear);
        }

    }
}
