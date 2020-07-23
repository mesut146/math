package com.mesut.math.funcs;

import com.mesut.math.core.func;
import com.mesut.math.operator.div;

public class loga_b extends div {

    public loga_b(func a, func b) {
        super(new ln(b), new ln(a));
    }

    @Override
    public String toString2() {
        return "log_" + a + "(" + b + ")";
    }


}
