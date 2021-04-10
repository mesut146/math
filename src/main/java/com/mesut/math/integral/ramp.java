package com.mesut.math.integral;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class ramp extends func {

    public ramp(func f) {
        a = f;
        alter.add(f.mul(new step(f)));
        alter.add(new Integral(new step(variable.t), variable.t, cons.MINF, f));
        //laplace(ramp(t))=1/s^2
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return new ramp(a.get(vars, vals));
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        double x;
        if ((x = a.eval(v, vals)) >= 0) {
            return x;
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
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func derivative(variable v) {
        return new step(a).mul(a.derivative(v));
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        // TODO: Implement this method
        return new ramp(a);
    }

    @Override
    public String toString2() {
        // TODO: Implement this method
        return "ramp(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        // TODO: Implement this method
        return a.eq(f.a);
    }

    @Override
    public void vars(Set<variable> vars) {
        // TODO: Implement this method
        a.vars(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        // TODO: Implement this method
        a = a.substitute(v, p);
        return this;
    }

}
