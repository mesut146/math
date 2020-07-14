package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.funcs.exp;
import com.mesut.math.operator.div;

public class sinh extends div {

    func p;

    public sinh(func f) {
        //(e^f-e^-f)/2
        super(new exp(f).sub(new exp(f.negate())), cons.TWO);
        p = f;
    }

    @Override
    public String toString2() {
        return "sinh(" + p + ")";
    }


}
