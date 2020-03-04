package com.mesut.math.integral;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.var;
import com.mesut.math.funcs.ln;

public class li extends Integral {

    public li(func f) {
        lower = cons.TWO;
        upper = f;
        setDummy(f, var.t);
        a = new ln(dv).MulInverse();
    }

    @Override
    public String toLatex() {
        return toString();
    }

    @Override
    public String toString2() {
        return "li(" + a + ")";
    }

}
