package com.mesut.math.funcs;

import com.mesut.math.core.func;
import com.mesut.math.core.variable;

//permutation
public class perm extends func {

    public perm(func a, func b) {
        this.a = a;
        this.b = b;
    }

    public static int calc(int n, int r) {
        //n!/(n-r)!
        int res = 1;
        int diff = n - r;
        for (int i = n; i >= diff; i--) {
            res *= i;
        }
        return res;
    }

    @Override
    public double eval(variable[] vars, double[] vals) {
        int n = (int) a.eval(vars, vals);
        int r = (int) b.eval(vars, vals);
        return calc(n, r);
    }

    @Override
    public String toString2() {
        return "perm(" + a + "," + b + ")";
    }
}
