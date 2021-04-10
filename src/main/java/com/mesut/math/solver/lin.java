package com.mesut.math.solver;

import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.List;

//f=val
public class lin extends func {
    double val;
    boolean hasVal;
    func f;

    public lin(func f, double val) {
        this.f = f;
        this.val = val;
        this.hasVal = true;
    }

    public lin(func f) {
        this.f = f;
        this.hasVal = false;
    }

    @Override
    public String toString() {
        if (hasVal) {
            return f + "=" + val;
        }
        return f.toString();
    }

    func getF() {
        return f;
    }

    @Override
    public List<variable> vars() {
        return f.vars();
    }
}
