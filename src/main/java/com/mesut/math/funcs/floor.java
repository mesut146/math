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
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func get0(variable[] v, cons[] c) {
        return new floor(a.get(v, c)).simplify();
    }

    @Override
    public double eval(variable[] v, double[] d) {
        return sign * Math.floor(a.eval(v, d));
    }

    @Override
    public cons evalc(variable[] v, double[] d) {
        return new cons(sign * Math.floor(a.evalc(v, d).decimal().doubleValue()));
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
    public boolean eq2(func f) {
        // TODO: Implement this method
        return a.eq(f.a);
    }

    @Override
    public func substitude0(variable v, func p) {
        // TODO: Implement this method
        return new floor(a.substitude0(v, p));
    }

    @Override
    public func simplify() {
        if (a.isCons0()) {
            return evalc();
        }
        return this;
    }


}
