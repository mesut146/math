package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.funcs.ln;
import com.mesut.math.operator.mul;

public class acoth extends mul {
    func p;

    public acoth(func f) {
        super(cons.ONE.div(2), new ln(f.add(cons.ONE).div(f.sub(cons.ONE))));
        p = f;
    }

    @Override
    public String toString2() {
        return "acoth(" + p + ")";
    }
}
