package com.mesut.math.integral;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class step extends func {

    public step(func p) {
        a = p;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        double av = a.eval(v, vals);
        if (av >= 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(eval(vars, vals));
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
        return toString();
    }

    @Override
    public func derivative(variable v) {
        return null;
    }

    @Override
    public func integrate(variable v) {
        return null;
    }

    @Override
    public func copy0() {
        return new step(a);
    }

    @Override
    public String toString2() {
        return "u(" + a + ")";
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }

    @Override
    public func simplify() {
        if (a.isConstant()) {
            return evalc();
        }
        if (vars().size() == 0) {
            return evalc();
        }
        return this;
    }


}
