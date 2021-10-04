package com.mesut.math;

import com.mesut.math.core.set;
import com.mesut.math.funcs.*;
import com.mesut.math.integral.*;
import com.mesut.math.prime.countPrimes;
import com.mesut.math.prime.pi;
import com.mesut.math.prime.prime;
import com.mesut.math.prime.pset;
import com.mesut.math.trigonometry.*;

import static com.mesut.math.core.func.register;

public class Config {
    public static int printDecimals = -1;//#digits will be printed , -1 means disabled
    public static boolean useBigDecimal = false;//cons will use math.BigDecimal instead of double
    public static int precision = 25;//bigdecimal digits
    public static int numericDerivativePrecision = 5;
    public static int maxIteration = 100000;//sigma
    public static int digits = 5;//sigma
    public static boolean lnFullImaginary = true;

    public static void init() {
        register("exp", exp.class);
        register("ln", ln.class);
        register("log10", log10.class);
        register("log", loga_b.class);//
        register("fac", fac.class);
        register("floor", floor.class);
        register("sqrt", sqrt.class);
        register("inv", inv.class);
        register("step", step.class);

        register("gamma", gamma.class);
        register("zeta", zeta.class);
        register("li", li.class);
        register("Ei", Ei.class);
        register("ei", Ei.class);
        register("erf", erf.class);
        register("laplace", laplace.class);

        register("sin", sin.class);
        register("cos", cos.class);
        register("tan", tan.class);
        register("cot", cot.class);
        register("sec", sec.class);
        register("csc", csc.class);
        register("asin", asin.class);
        register("acos", acos.class);
        register("atan", atan.class);
        register("acot", acot.class);

        register("sinh", sinh.class);
        register("cosh", cosh.class);

        register("pi", pi.class);
        register("primePi", pi.class);
        register("PrimePi", pi.class);
        register("prime", prime.class);
        register("p", prime.class);
        register("pset", pset.class);
        register("countPrime", countPrimes.class);
        register("countPrimes", countPrimes.class);
        register("set", set.class);

        register("ramp", ramp.class);
        register("step", step.class);

    }

    public static class add {
        public static boolean simplify = true;
    }

    public static class mul {
        public static boolean simplify = true;
        public static boolean distributeCons = false;
    }

    public static class div {
        public static boolean simplify = true;
        public static boolean distributeCons = true;
    }

    public static class pow {
        public static boolean
                simplify = true,
                simpCons = true;//2^10 becomes 1024

    }

    public static class integral {
        public static int interval = 100000;//divide to this much sub intervals
        public static int convDecimal = 16;//number of decimal for convergence
        public static int convMaxTries = 10;//if sum doesn't grow this iteration we stop
        public static boolean converge = false;
    }

    public static class trigonomety {
        public static boolean stay = true;
    }

}
