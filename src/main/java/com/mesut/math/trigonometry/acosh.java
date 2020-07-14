package com.mesut.math.trigonometry;

import com.mesut.math.core.func;
import com.mesut.math.funcs.ln;

public class acosh extends ln {

    func p;

    public acosh(func f) {
        super(f.add(f.pow(2).sub(1)).sqrt());
        p = f;
    }

    @Override
    public String toString2() {
        return "acosh(" + p + ")";
    }
}
