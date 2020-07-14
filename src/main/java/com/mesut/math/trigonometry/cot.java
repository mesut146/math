package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.var;

import java.util.Set;

public class cot extends func {

    public cot(func f) {
        this.a = f;
        alter.add(new cos(f).div(new sin(f)));
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
    public void vars0(Set<var> vars) {
        a.vars0(vars);
    }

    @Override
    public func get0(var[] v, cons[] c) {
        return signf(alter.get(0).get0(v, c));
    }

    @Override
    public double eval(var[] v, double[] d) {
        return sign * 1.0 / Math.tan(a.eval(v, d));
    }

    @Override
    public cons evalc(var[] v, double[] d) {
        return new cons(eval(v, d));
    }

    @Override
    public func derivative(var v) {
        return signf(alter.get(0)).derivative(v);
    }

    @Override
    public func integrate(var v) {
        return null;
    }

    @Override
    public String toString2() {
        return "cot(" + a + ")";
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitude0(var v, func p) {
        a = a.substitude(v, p);
        return this;
    }

    @Override
    public func copy0() {
        return new cot(a);
    }
}
