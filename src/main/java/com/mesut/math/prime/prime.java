package com.mesut.math.prime;

import com.mesut.math.Util;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

//n th prime p(n)
public class prime extends func {

    public prime(Object n) {
        a = Util.cast(n);
    }

    @Override
    public func getReal() {
        return null;
    }

    @Override
    public func getImaginary() {
        return null;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get0(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return pset.get((int) a.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(eval(vars, vals));
    }

    @Override
    public String toLatex() {
        return toString();
    }

    @Override
    public func integrate(variable v) {
        return null;
    }

    @Override
    public func copy0() {
        return new prime(a);
    }

    @Override
    public String toString2() {
        return "prime(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        return false;
    }

    @Override
    public void vars(Set<variable> vars) {
        a.vars(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute0(v, p);
        return this;
    }

}
