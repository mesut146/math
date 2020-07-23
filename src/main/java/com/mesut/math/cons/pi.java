package com.mesut.math.cons;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

public class pi extends cons {

    public pi() {
        functional = true;
        val = Math.PI;
        ff = this;
    }

    @Override
    public String toLatex() {
        return "\\pi";
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return val;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return this;
    }

    @Override
    public String toString2() {
        //return "pi";
        return String.valueOf('\u03C0');
    }

    @Override
    public boolean eq0(func f) {
        return f.getClass() == pi.class;
    }

    @Override
    public func copy0() {
        return new pi();
    }


}
