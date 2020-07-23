package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class sec extends func {

    public sec(func f) {
        this.a = f;
        alter.add(new cos(f).MulInverse());
        fx = true;
    }

    @Override
    public func getReal() {
        return alter.get(0).getReal();
    }

    @Override
    public func getImaginary() {
        return alter.get(0).getImaginary();
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
    public func get0(variable[] vars, cons[] vals) {
        return signf(alter.get(0).get0(vars, vals));
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * 1.0 / Math.cos(a.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(eval(vars, vals));
    }

    @Override
    public func derivative(variable v) {
        return alter.get(0).derivative(v);
    }

    @Override
    public func integrate(variable v) {
        return null;
    }

    @Override
    public String toString2() {
        return "sec(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }

    @Override
    public func copy0() {
        return new sec(a);
    }
}
