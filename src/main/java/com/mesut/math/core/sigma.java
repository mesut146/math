package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;

import java.util.Set;

public class sigma extends func {


    func start, end;//var or cons
    public func fx;
    var var;

    public sigma(Object func, Object var, Object start, Object end) {
        this.fx = Util.cast(func);
        this.var = Util.var(var);
        this.start = Util.cast(start);
        this.end = Util.cast(end);
    }

    @Override
    public void vars0(Set<var> vars) {
        fx.vars0(vars);
        start.vars0(vars);
        end.vars0(vars);
    }

    @Override
    public func get0(var[] v, cons[] c) {
        /*if(this.var.eq2(v)){

         }*/
        return this;
    }

    @Override
    public double eval(var[] v, double[] d) {
        func s = start.get(v, d);
        func e = end.get(v, d);
        //sigma(x^n/n^2,n=1 to k) get(x=2,k=33)

        if (!isInt(s)) {
            throw new RuntimeException("starting index must be an integer");
        }
        if (!isInt(e)) {
            throw new RuntimeException("ending index must be an integer");
        }
        int si = (int) s.eval();
        int ei = (int) e.eval();

        func fx2 = fx.get(v, d);
        double precision = Math.pow(10, -Config.digits);
        double sum = fx2.eval(var, si), last = 0;

        for (int i = si + 1; i <= ei; i++) {
            sum += fx2.eval(var, i);
            //System.out.println(fx2.eval(var,i));
            //System.out.println(sum);
            //1.6449340668
            //1.6449240668982262
            if (i == Config.maxIteration) {

                return sum;
            }
            last = sum;
        }
        return sum;
    }

    @Override
    public cons evalc(var[] v, double[] d) {
        // TODO: Implement this method
        return new cons(eval(v, d));
    }

    @Override
    public func derivative(var v) {
        return new sigma(fx.derivative(v), this.var, start, end);
    }

    @Override
    public func integrate(var v) {
        return new sigma(fx.integrate(v), var, start, end);
    }

    boolean isInt(func f) {
        if (f.isConstant()) {
            if (f.eq(cons.INF)) {
                return true;
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
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public func substitude0(var v, func p) {
        fx = fx.substitude(v, p);
        return this;
    }

    @Override
    public func copy0() {
        return new sigma(fx, var, start, end);
    }

    @Override
    public func getReal() {
        sigma s = new sigma(fx.getReal(), var, start, end);
        return sign(s);
    }

    @Override
    public func getImaginary() {
        sigma s = new sigma(fx.getImaginary(), var, start, end);
        return sign(s);
    }
}
