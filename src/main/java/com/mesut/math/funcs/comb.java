package com.mesut.math.funcs;

import com.mesut.math.core.func;
import com.mesut.math.core.variable;

//combination
public class comb extends func {

    public comb(func a, func b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double eval(variable[] vars, double[] vals) {
        int n = (int) a.eval(vars, vals);
        int r = (int) b.eval(vars, vals);
        //n!/r!.(n-r)!
        return (double) (perm.calc(n, r) / fac.compute(r));
    }

    @Override
    public String toString2() {
        return "comb(" + a + "," + b + ")";
    }
}
