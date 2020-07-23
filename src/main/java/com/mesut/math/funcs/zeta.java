package com.mesut.math.funcs;

import com.mesut.math.Util;
import com.mesut.math.core.*;
import com.mesut.math.integral.gamma;

import java.util.Set;

public class zeta extends func {
    public sigma sum;
    public Integral i;
    public gamma g;
    func s;
    public zeta(Object o) {
        s = Util.cast(o);
        sum = new sigma(variable.n.pow(s.negate()), variable.n, 1, cons.INF);
        i = new Integral(variable.u.pow(s.sub(1)).div(cons.E.pow(variable.u).sub(1)), variable.u, cons.ZERO, cons.INF);
        g = new gamma(s);
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
    public void vars0(Set<variable> vars) {
        s.vars0(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        s = s.get(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        // TODO: Implement this method
        return sign * sum.eval(v, vals);
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toLatex() {
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func derivative(variable v) {
        sum = (sigma) sum.derivative(v);
        return this;
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        // TODO: Implement this method
        return new zeta(s);
    }

    @Override
    public String toString2() {
        // TODO: Implement this method
        return "zeta(" + s + ")";
    }

    @Override
    public boolean eq2(func f) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public func substitute0(variable v, func p) {
        // TODO: Implement this method
        return null;
    }


}
