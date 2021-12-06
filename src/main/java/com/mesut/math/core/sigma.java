package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;

import java.util.Set;

//summation symbol (sigma)
public class sigma extends func {

    public func fx;
    func start, end;//var or cons
    variable var;

    public sigma(Object func, Object var, Object start, Object end) {
        this.fx = Util.cast(func);
        this.var = Util.var(var);
        this.start = Util.cast(start);
        this.end = Util.cast(end);
    }

    @Override
    public void vars(Set<variable> vars) {
        fx.vars(vars);
        start.vars(vars);
        end.vars(vars);
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        func s = start.get(v, vals);
        func e = end.get(v, vals);

        if (!isInt(s)) {
            throw new RuntimeException("starting index must be an integer");
        }
        if (e.isConstant() && e.asCons().inf) {
            return evalInfinite(fx.get(v, vals), s);
        }
        if (!isInt(e)) {
            throw new RuntimeException("ending index must be an integer");
        }
        int si = (int) s.eval();
        int ei = (int) e.eval();

        func f = fx.get(v, vals);
        double sum = 0;

        for (int i = si; i <= ei; i++) {
            sum += f.eval(var, i);
        }
        return sum;
    }

    private double evalInfinite(func f, func s) {
        int si = (int) s.eval();
        double res = 0;
        for (int i = si; ; i++) {
            res = res + f.eval(i);
            if (i == Config.maxIteration) {
                break;
            }
        }
        return res;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(eval(vars, vals));
    }

    @Override
    public func derivative(variable v) {
        return new sigma(fx.derivative(v), this.var, start, end);
    }

    @Override
    public func integrate(variable v) {
        return new sigma(fx.integrate(v), var, start, end);
    }

    boolean isInt(func f) {
        if (f.isConstant()) {
            if (f.eq(cons.INF)) {
                return false;
            }
            double d = f.eval();
            return d == (long) d;
        }
        return false;
    }

    @Override
    public String toString2() {
        return String.format("sum(%s,%s=%s to %s)", fx, var, start, end);
    }

    @Override
    public String toLatex() {
        return String.format("sum_{%s=%s}^{%s} %s", var.toLatex(), start.toLatex(), end.toLatex(), fx.toLatex());
    }

    @Override
    public boolean eq0(func f) {
        return false;
    }

    @Override
    public func substitute0(variable v, func p) {
        fx = fx.substitute(v, p);
        return this;
    }

    @Override
    public func copy0() {
        return new sigma(fx, var, start, end);
    }

    @Override
    public func getReal() {
        sigma s = new sigma(fx.getReal(), var, start, end);
        return signOther(s);
    }

    @Override
    public func getImaginary() {
        sigma s = new sigma(fx.getImaginary(), var, start, end);
        return signOther(s);
    }
}
