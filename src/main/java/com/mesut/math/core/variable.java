package com.mesut.math.core;

import java.math.BigDecimal;
import java.util.Set;

public class variable extends func {

    public static final variable
            x = new variable("x"),
            y = new variable("y"),
            z = new variable("z"),
            t = new variable("t"),
            u = new variable("u"),
            n = new variable("n");
    String name;

    public variable(String name) {
        //this.type = types.variable;
        this.name = name;
    }

    public variable(char c) {
        this(String.valueOf(c));
    }

    public static variable from(String s) {
        return new variable(s);
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public func getReal() {
        return this;
    }

    @Override
    public func getImaginary() {
        return cons.ZERO;
    }

    @Override
    public void vars0(Set<variable> vars) {
        vars.add(this);
    }

    @Override
    public String toLatex() {
        return toString();
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        for (int i = 0; i < vars.length; i++) {
            if (eq2(vars[i])) {
                //TODO: mutable or not?
                //return new Constant(c[i]).s(sign);
                return vals[i].sign(sign);
            }
        }

        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        for (int i = 0; i < v.length; i++) {
            if (eq2(v[i])) {
                return vals[i] * sign;
            }
        }
        return 0;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        for (int i = 0; i < vars.length; i++) {
            if (eq2(vars[i])) {
                return new cons(BigDecimal.valueOf(sign * vals[i]));
            }
        }
        return cons.ZERO;
    }

    @Override
    public func derivative(variable v) {
        if (eq2(v)) {
            return cons.ONE;
        }
        return cons.ZERO;
    }

    @Override
    public func integrate(variable v) {
        if (eq2(v)) {
            return this.pow(2).div(2);
        }
        return mul(v);
    }

    @Override
    public String toString2() {
        return name;
    }

    @Override
    public func simplify() {
        return this;
    }

    @Override
    public boolean eq2(func f) {
        return name.equals(((variable) f).name);
    }

    @Override
    public func substitute0(variable v, func p) {
        return eq2(v) ? p : this;
    }

    @Override
    public func copy0() {
        return new variable(name);
    }
}
