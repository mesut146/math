package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.funcs.exp;
import com.mesut.math.operator.div;

public class cosh extends div {

    func p;

    public cosh(func f) {
        //(e^f+e^-f)/2
        super(new exp(f).add(new exp(f.negate())), cons.TWO);
        p = f;
    }

    @Override
    public String toString2() {
        return "cosh(" + p + ")";
    }


}
