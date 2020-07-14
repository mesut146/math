package com.mesut.math.trigonometry;

import com.mesut.math.core.func;
import com.mesut.math.funcs.ln;

public class asinh extends ln {
    func p;

    public asinh(func f) {
        super(f.add(f.pow(2).add(1)).sqrt());
        p = f;
    }

    @Override
    public String toString2() {
        return "asinh(" + p + ")";
    }


}
