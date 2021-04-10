package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class asin extends func {

    public asin(func p) {
        a = p;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * Math.asin(a.eval(v, vals));
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
        func f1 = a.derivative(v);
        func f2 = cons.ONE.sub(a.pow(2)).sqrt();
        return f1.div(f2);
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        return new asin(a);
    }

    @Override
    public String toString2() {
        return "asin(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f.a);
    }

    @Override
    public void vars(Set<variable> vars) {
        a.vars(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }

}
