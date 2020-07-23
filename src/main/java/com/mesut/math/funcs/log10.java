package com.mesut.math.funcs;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;

public class log10 extends loga_b {

    public log10(func p) {
        super(new cons(10), p);
    }

    @Override
    public String toString2() {
        return "log10(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f.a);
    }

}
