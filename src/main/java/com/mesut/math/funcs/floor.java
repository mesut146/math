package com.mesut.math.funcs;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class floor extends func {
    public floor(func f) {
        a = f;
    }

    @Override
    public func getReal() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func getImaginary() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toLatex() {
        // TODO: Implement this method
        return toString();
    }

    @Override
    public void vars(Set<variable> vars) {
        a.vars(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return new floor(a.get(vars, vals)).simplify();
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * Math.floor(a.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(sign * Math.floor(a.evalc(vars, vals).decimal().doubleValue()));
    }

    @Override
    public func derivative(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        // TODO: Implement this method
        return new floor(a);
    }

    @Override
    public String toString2() {
        // TODO: Implement this method
        return "floor(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        // TODO: Implement this method
        return a.eq(f.a);
    }

    @Override
    public func substitute0(variable v, func p) {
        // TODO: Implement this method
        return new floor(a.substitute0(v, p));
    }

    @Override
    public func simplify() {
        if (a.isCons0()) {
            return evalc();
        }
        return this;
    }


}
