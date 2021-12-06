package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

//https://en.wikipedia.org/wiki/Numerical_integration

public class Integral extends func {

    public func func;
    public variable dv;
    public func lower, upper;

    //f(x),dv
    public Integral(Object func, Object var) {
        this.func = Util.cast(func);
        this.dv = Util.var(var);
    }

    //f(x) dx
    public Integral(Object func) {
        this.func = Util.cast(func);
        this.dv = this.func.vars().get(0);
    }

    //f(x),dv,lower,upper
    public Integral(Object func, Object var, Object lower, Object upper) {
        this(func, var);
        this.lower = Util.cast(lower);
        this.upper = Util.cast(upper);
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
                return signOther(new Integral(func.getReal(), dv, lower, upper));
            }
        }
        return signOther(new Integral(func.getReal(), dv));
    }

    @Override
    public func getImaginary() {
        if (hasLimits()) {
            if (lower.isReal() && upper.isReal()) {
                return signOther(new Integral(func.getImaginary(), dv, lower, upper));
            }
        }
        return signOther(new Integral(func.getImaginary(), dv));
    }

    @Override
    public String toLatex() {
        if (hasLimits()) {
            return String.format("\\int_{%s}^{%s} %s ,d%s", lower, upper, func, dv);
        }
        else {
            return String.format("\\int_ %s ,d%s", func, dv);
        }
    }

    @Override
    public void vars(Set<variable> vars) {
        func.vars(vars);

        if (hasLimits()) {//if improper then remove dummy var
            vars.remove(dv);
            lower.vars(vars);
            upper.vars(vars);
        }
    }

    public boolean isLimCons() {
        return hasLimits() && lower.isConstant() && upper.isConstant();
    }

    //f=f(x) v=preferred var
    public void setDummy(func f, variable v) {
        List<variable> vars = f.vars();
        if (vars.contains(v)) {
            dv = makeDummy(vars);
        }
        else {
            dv = v;
        }
    }

    public variable makeDummy(List<variable> vars) {
        List<String> pref = Arrays.asList("x", "t", "u", "y", "u", "w");
        List<String> str = new ArrayList<>();
        for (variable v : vars) {
            str.add(v.name);
        }

        if (str.containsAll(pref)) {
            for (char i = 'a'; i <= 'z'; i++) {
                if (i != 'd' && !str.contains(String.valueOf(i))) {
                    return new variable(String.valueOf(i));
                }
            }
            return new variable("x0");
        }
        else {
            for (String sv : pref) {
                if (!str.contains(sv)) {
                    return new variable(sv);
                }
            }
        }
        return variable.x;
    }


    @Override
    public func get0(variable[] vars, cons[] vals) {
        Integral res = (Integral) copy();
        if (hasLimits()) {
            res.lower = res.lower.get0(vars, vals);
            res.upper = res.upper.get0(vars, vals);
        }
        res.func = res.func.get0(vars, vals);
        return res;
    }

    @Override
    public double eval() {
        //check single variable
        makeFinite();
        return riemannSum();
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        a = a.get(v, vals);
        return eval();
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(eval(vars, vals));
    }

    //scale infinite bounds to 0,1 range
    public void makeFinite() {
        boolean isLow = lower.asCons().isInf();
        boolean isUp = upper.asCons().isInf();
        if (isLow) {
            if (isUp) {//both
                //x=t/(t-1)
                variable nv = variable.t;
                func tsq = nv.pow(2);
                func ext = cons.ONE.add(tsq).div(cons.ONE.sub(tsq).pow(2));
                func np = nv.div(cons.ONE.sub(tsq));
                this.func = func.substitute(dv, np).mul(ext);
                this.dv = nv;
                this.lower = cons.ONE.negate();
                this.upper = cons.ONE;
            }
            else {//low only
                variable nv = variable.t;
                func ext = nv.pow(-2);
                func np = upper.sub(cons.ONE.sub(nv).div(nv));
                this.func = func.substitute(dv, np).mul(ext);
                this.dv = nv;
                this.lower = cons.ZERO;
                this.upper = cons.ONE;
            }
        }
        else if (isUp) {//up only
            variable nv = variable.t;
            func ext = cons.ONE.div(cons.ONE.sub(nv).pow(2));
            func np = lower.add(nv.div(cons.ONE.sub(nv)));
            this.func = func.substitute(dv, np).mul(ext);
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
        double dx = (upper.eval() - low + 1) / k;

        double convRange = Math.pow(10, -Config.integral.convDecimal);
        int curTries = 0;
        double curVal = 0;
        for (int i = 1; i <= k; i++) {
            double p = low + i * dx;
            curVal = func.eval(dv, p) * dx;
            System.out.println(sum);
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

    //todo
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

        return (b - a) * this.func.eval((a + b) / 2);
    }

    public double trapezoidalRule() {
        double a = lower.eval();
        double b = upper.eval();

        return (b - a) * (this.func.eval(a) + this.func.eval(b)) / 2;
    }

    public double composizeTrapezoidalRule(int n) {
        double a = lower.eval();
        double b = upper.eval();

        double total = (b - a) / n;
        variable sigVar = variable.t;
        func newFunc = new cons(a).add(sigVar.mul(total));

        sigma sigma = new sigma(this.func.substitute(dv, newFunc), sigVar, 1, n - 1);
        double rest = sigma.eval();
        return total * (this.func.eval(a) / 2 + this.func.eval(b) / 2 + rest);
    }

    @Override
    public func derivative(variable v) {
        if (dv.eq(v) && !hasLimits()) {//not always
            return signf(func);
        }
        if (hasLimits()) {
            List<variable> l1 = lower.vars();
            List<variable> l2 = upper.vars();
            List<variable> l3 = func.vars();
            boolean b1 = l1.contains(v);
            boolean b2 = l2.contains(v);
            if (b1 || b2) {
                if (l3.contains(v)) {

                }
                else {//fubuni's theorem
                    func ls = func.substitute(dv, upper).mul(upper.derivative(v));
                    func rs = func.substitute(dv, lower).mul(lower.derivative(v));
                    return ls.sub(rs);
                }
            }
            /*if(b2){
                return a.substitude(dv,a2).mul(a2.derivative(v));
            }
            if(b1){
                return a.substitude(dv,a1).mul(a1.derivative(v)).negate();
            }*/
            return new Integral(func.derivative(v), this.dv, lower, upper);
        }
        return new Integral(func.derivative(v), this.dv);
    }

    @Override
    public func integrate(variable v) {
        if (!this.dv.eq(v)) {
            if (hasLimits()) {
                return new Integral(func.integrate(v), this.dv, lower, upper);
            }
            return new Integral(func.integrate(v), this.dv);
        }
        return null;
    }

    @Override
    public func copy0() {
        if (hasLimits()) {
            return new Integral(func, dv, lower, upper);
        }
        return new Integral(func, dv);
    }

    @Override
    public String toString2() {
        if (hasLimits()) {
            return String.format("Integral{%s d%s,%s,%s}", func, dv, lower, upper);
        }
        return String.format("Integral{%s d%s}", func, dv);
    }

    @Override
    public boolean eq0(func f) {
        Integral other = (Integral) f;
        boolean eqNolimit = func.eq(other.func) && dv.eq(other.dv);
        if (hasLimits() == other.hasLimits()) {
            if (hasLimits()) {//both have limits
                return eqNolimit && lower.eq(other.lower) && upper.eq(other.upper);
            }
            else {//neither have limits
                return eqNolimit;
            }
        }
        return false;
    }

    @Override
    public func substitute0(variable v, func p) {
        func = func.substitute(v, p);
        lower = lower.substitute(v, p);
        upper = upper.substitute(v, p);
        return this;
    }

}
