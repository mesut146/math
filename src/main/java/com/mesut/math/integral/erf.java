package com.mesut.math.integral;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.var;
import com.mesut.math.funcs.exp;

public class erf extends Integral {
    public erf(func f) {
        lower = cons.ZERO;
        upper = f;
        setDummy(f, var.t);
        func coef = cons.TWO.div(cons.PI.sqrt());
        a = new exp(dv.pow(2).negate()).mul(coef);
    }

    @Override
    public String toString2() {
        return "erf(" + a + ")";
    }


}
