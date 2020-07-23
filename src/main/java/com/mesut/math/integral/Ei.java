package com.mesut.math.integral;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.funcs.exp;

public class Ei extends Integral {

    public Ei(func f) {
        lower = f;
        upper = cons.INF;
        setDummy(f, variable.t);
        a = new exp(dv.negate().div(dv));
        sign = -1;
    }

    @Override
    public String toString2() {
        return "Ei(" + a + ")";
    }


}
