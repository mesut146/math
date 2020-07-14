package com.mesut.math.core;

import java.math.BigDecimal;
import java.util.Set;

//todo rename
public class var extends func {

    public static final var
            x = new var("x"),
            y = new var("y"),
            z = new var("z"),
            t = new var("t"),
            u = new var("u"),
            n = new var("n");
    String name;

    public var(String name) {
        //this.type = types.variable;
        this.name = name;
    }

    public var(char c) {
        this(String.valueOf(c));
    }

    public static var from(String s) {
        return new var(s);
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
    public void vars0(Set<var> vars) {
        vars.add(this);
    }

    @Override
    public String toLatex() {
        return toString();
    }

    @Override
    public func get0(var[] v, cons[] c) {
        for (int i = 0; i < v.length; i++) {
            if (eq2(v[i])) {
                //TODO: mutable or not?
                //return new Constant(c[i]).s(sign);
                return c[i].sign(sign);
            }
        }

        return this;
    }

    @Override
    public double eval(var[] v, double[] d) {
        for (int i = 0; i < v.length; i++) {
            if (eq2(v[i])) {
                return d[i] * sign;
            }
        }
        return 0;
    }

    @Override
    public cons evalc(var[] v, double[] d) {
        for (int i = 0; i < v.length; i++) {
            if (eq2(v[i])) {
                return new cons(BigDecimal.valueOf(sign * d[i]));
            }
        }
        return cons.ZERO;
    }

    @Override
    public func derivative(var v) {
        if (eq2(v)) {
            return cons.ONE;
        }
        return cons.ZERO;
    }

    @Override
    public func integrate(var v) {
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
        return name.equals(((var) f).name);
    }

    @Override
    public func substitude0(var v, func p) {
        return eq2(v) ? p : this;
    }

    @Override
    public func copy0() {
        return new var(name);
    }
}
