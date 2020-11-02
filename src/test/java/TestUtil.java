public class TestUtil {
    static double precision = 0.001;

    public static void assertEqual(double d1, double d2) {
        assertLower(Math.abs(Math.abs(d1) - Math.abs(d2)), precision);
    }

    public static void assertLower(double d1, double d2) {
        if (!(d1 <= precision)) {
            throw new AssertionError(String.format("%f <= %f not satisfied", d1, d2));
        }
    }
}
