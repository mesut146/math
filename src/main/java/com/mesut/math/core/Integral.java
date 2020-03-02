package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Integral extends func {
    public var dv;
    public func lower, upper;

    //f(x),dv
    public Integral(Object f, Object v) {
        this.a = Util.cast(f);
        this.dv = Util.var(v);
    }

    //f(x) dx
    public Integral(Object f) {
        this.a = Util.cast(f);
        this.dv = var.x;
    }

    //f(x),dv,lower,upper
    public Integral(Object f, Object v, Object i1, Object i2) {
        this.a = Util.cast(f);
        this.dv = Util.var(v);
        this.lower = Util.cast(i1);
        this.upper = Util.cast(i2);
        //lim=true;
    }

    public Integral() {

    }

    public boolean hasLimits() {
        return lower != null && upper != null;
    }

    @Override
    public func getReal() {
        if (hasLimits()) {
            if (lower.isReal() && upper.isReal()) {
                return sign(new Integral(a.getReal(), dv, lower, upper));
            }

        }
        return sign(new Integral(a.getReal(), dv));
    }

    @Override
    public func getImaginary() {
        if (hasLimits()) {
            if (lower.isReal() && upper.isReal()) {
                return sign(new Integral(a.getImaginary(), dv, lower, upper));
            }

        }
        return sign(new Integral(a.getImaginary(), dv));
    }

    @Override
    public String toLatex() {
        StringBuilder s = new StringBuilder("\\int");
        if (hasLimits()) {
            s.append(String.format("_{%s}^{%s}", lower, upper, a, dv));
        }
        s.append(String.format("%s d%s", lower, upper, a, dv));
        return s.toString();
    }

    @Override
    public void vars0(Set<var> vars) {
        if (a != null) {
            a.vars0(vars);
        }
        if (hasLimits()) {//if improper then remove dummy var
            vars.remove(dv);
            lower.vars0(vars);
            upper.vars0(vars);
        }
    }

    public boolean isLimCons() {
        return hasLimits() && lower.isConstant() && upper.isConstant();
    }

    //f=f(x) v=preferred var
    public void setDummy(func f, var v) {
        List<var> l = f.vars();
        if (l.contains(v)) {
            dv = makeDummy(l);
        }
        else {
            dv = v;
        }
    }

    public var makeDummy(List<var> l) {
        List<String> pref = Arrays.asList("x", "t", "u", "y", "u", "w");
        List<String> str = new ArrayList<>();
        for (var v : l) {
            str.add(v.name);
        }

        if (str.containsAll(pref)) {
            for (char i = 'a'; i <= 'z'; i++) {
                if (i != 'd' && !str.contains(String.valueOf(i))) {
                    return new var(i);
                }
            }
            return new var("x0");
        }
        else {
            for (String sv : pref) {
                if (!str.contains(sv)) {
                    return new var(sv);
                }
            }
        }
        return var.x;
    }

    public Integral make() {
        Integral i = new Integral();

        return i;
    }

    @Override
    public func get0(var[] v, cons[] c) {
        Integral i = (Integral) copy();
        if (hasLimits()) {
            i.lower = i.lower.get0(v, c);
            i.upper = i.upper.get0(v, c);
        }
        i.a = i.a.get0(v, c);
        return i;
    }

    @Override
    public double eval() {
        //check single variable
        makeFinite();
        return riemannSum();
    }

    @Override
    public double eval(var[] v, double[] d) {
        a = a.get(v, d);
        System.out.println("int=" + a + " dv=" + dv);
        return eval();
    }

    @Override
    public cons evalc(var[] v, double[] d) {
        return new cons(eval(v, d));
    }

    //transform infinite bounds to 0,1 range
    public void makeFinite() {
        boolean isLow = lower.asCons().isInf();
        boolean isUp = upper.asCons().isInf();
        if (isLow) {
            if (isUp) {//both
                var nv = var.t;
                func tsq = nv.pow(2);
                func ext = cons.ONE.add(tsq).div(cons.ONE.sub(tsq).pow(2));
                func np = nv.div(cons.ONE.sub(tsq));
                this.a = a.substitude(dv, np).mul(ext);
                this.dv = nv;
                this.lower = cons.ONE.negate();
                this.upper = cons.ONE;
            }
            else {//low only
                var nv = var.t;
                func ext = nv.pow(-2);
                func np = upper.sub(cons.ONE.sub(nv).div(nv));
                this.a = a.substitude(dv, np).mul(ext);
                this.dv = nv;
                this.lower = cons.ZERO;
                this.upper = cons.ONE;
            }
        }
        else if (isUp) {//up only
            var nv = var.t;
            func ext = cons.ONE.div(cons.ONE.sub(nv).pow(2));
            func np = lower.add(nv.div(cons.ONE.sub(nv)));
            this.a = a.substitude(dv, np).mul(ext);
            this.dv = nv;
            this.lower = cons.ZERO;
            this.upper = cons.ONE;
        }
    }

    public double riemannSum() {
        if (!lower.isConstant() || !upper.isConstant()) {
            throw new Error("integral limits must be constant");
        }
        double sum = 0;
        double k = Config.integral.interval;
        double low = lower.eval();
        double dx = (upper.eval() - low) / k;

        double convRange = Math.pow(10, -Config.integral.convDecimal);
        int curTries = 0;
        double curVal = 0;
        for (int i = 1; i < k; i++) {
            double p = low + i * dx;
            curVal = a.eval(dv, p) * dx;
            //check if we converged enough
            /*if (Math.abs(curVal) <= convRange) {
                curTries++;
                if (curTries == Config.integral.convMaxTries) {
                    //break;
                }
            }*/
            sum += curVal;
            //System.out.println(i + "-sum=" + sum);
            /*if ((k / 10) % i == 0) {
                System.out.println("sum=" + sum);
            }*/
        }
        return sum;
    }

    public double calcWithTaylor(int n) {
        double sum = 0;
        set set = new set();
        for (int i = 0; i < n; i++) {

        }
        sum = set.sum();
        return sum;
    }

    public double calcWithTaylor() {
        return calcWithTaylor(10);
    }

    public double rectangleRule() {
        double a = lower.eval();
        double b = upper.eval();

        return (b - a) * this.a.eval((a + b) / 2);
    }

    public double trapezoidalRule() {
        double a = lower.eval();
        double b = upper.eval();

        return (b - a) * (this.a.eval(a) + this.a.eval(b)) / 2;
    }

    public double composizeTrapezoidalRule(int n) {
        double a = lower.eval();
        double b = upper.eval();

        double total = (b - a) / n;
        var sigVar = var.t;
        func newFunc = new cons(a).add(sigVar.mul(total));

        sigma sigma = new sigma(this.a.substitude(dv, newFunc), sigVar, 1, n - 1);
        double rest = sigma.eval();
        return total * (this.a.eval(a) / 2 + this.a.eval(b) / 2 + rest);
    }

    @Override
    public func derivative(var v) {
        if (dv.eq(v) && !hasLimits()) {//not always
            return signf(a);
        }
        if (hasLimits()) {
            List<var> l1 = lower.vars();
            List<var> l2 = upper.vars();
            List<var> l3 = a.vars();
            boolean b1 = l1.contains(v);
            boolean b2 = l2.contains(v);
            if (b1 || b2) {
                if (l3.contains(v)) {

                }
                else {//fubuni's theorem
                    func ls = a.substitude(dv, upper).mul(upper.derivative(v));
                    func rs = a.substitude(dv, lower).mul(lower.derivative(v));
                    return ls.sub(rs);
                }
            }
            /*if(b2){
                return a.substitude(dv,a2).mul(a2.derivative(v));
            }
            if(b1){
                return a.substitude(dv,a1).mul(a1.derivative(v)).negate();
            }*/
            return new Integral(a.derivative(v), this.dv, lower, upper);
        }
        return new Integral(a.derivative(v), this.dv);
    }

    @Override
    public func integrate(var v) {
        if (!this.dv.eq(v)) {
            if (hasLimits()) {
                return new Integral(a.integrate(v), this.dv, lower, upper);
            }
            return new Integral(a.integrate(v), this.dv);
        }
        return null;
    }

    @Override
    public func copy0() {
        if (hasLimits()) {
            return new Integral(a, dv, lower, upper);
        }
        return new Integral(a, dv);
    }

    @Override
    public String toString2() {
        if (hasLimits()) {
            return String.format("Integral{%s d%s,%s,%s}", a, dv, lower, upper);
        }
        return String.format("Integral{%s d%s}", a, dv);
    }

    @Override
    public boolean eq2(func f) {
        Integral i = (Integral) f;
        if (hasLimits() == i.hasLimits()) {
            if (hasLimits()) {
                return a.eq(i.a) && dv.eq(i.dv) && lower.eq(i.lower) && upper.eq(i.upper);
            }
            else {
                return a.eq(i.a) && dv.eq(i.dv);
            }

        }
        return false;
    }

    @Override
    public func substitude0(var v, func p) {
        a = a.substitude(v, p);
        lower = lower.substitude(v, p);
        upper = upper.substitude(v, p);
        return this;
        //return new Integral(a.substitude0(v,p),this.v);
    }

    @Override
    public func simplify() {
        if (hasLimits()) {

        }

        return this;
    }


}
