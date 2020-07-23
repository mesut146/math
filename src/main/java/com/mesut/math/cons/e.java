package com.mesut.math.cons;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

public class e extends cons {
    public e() {
        functional = true;
        val = Math.E;
        ff = this;
    }

    @Override
    public String toLatex() {
        return "e";
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * val;
    }

    @Override
    public cons evalc() {
        return this;
    }

    @Override
    public func copy0() {
        return new e();
    }

    @Override
    public String toString2() {
        return "e";
    }

    @Override
    public boolean eq0(func f) {
        return f.getClass() == getClass();
    }

}
