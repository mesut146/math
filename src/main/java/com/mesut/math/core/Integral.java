package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

//https://en.wikipedia.org/wiki/Numerical_integration

public class Integral extends func {

    public func f;
    public variable dv;
    public func lower, upper;

    //f(x),dv
    public Integral(Object f, Object var) {
        this.f = Util.cast(f);
        this.dv = Util.var(var);
    }

    //f(x),dv,lower,upper
    public Integral(Object f, Object var, Object lower, Object upper) {
        this(f, var);
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
                return signOther(new Integral(f.getReal(), dv, lower, upper));
            }
        }
        return signOther(new Integral(f.getReal(), dv));
    }

    @Override
    public func getImaginary() {
        if (hasLimits()) {
            if (lower.isReal() && upper.isReal()) {
                return signOther(new Integral(f.getImaginary(), dv, lower, upper));
            }
        }
        return signOther(new Integral(f.getImaginary(), dv));
    }

    @Override
    public String toLatex() {
        if (hasLimits()) {
            return String.format("\\[\\int_{%s}^{%s} %s \\,d%s\\]", lower.toLatex(), upper.toLatex(), f.toLatex(), dv.toLatex());
        }
        else {
            return String.format("\\[\\int_ %s \\,d%s\\]", f.toLatex(), dv.toLatex());
        }
    }

    @Override
    public void vars(Set<variable> vars) {
        f.vars(vars);

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

    //make variable by preferring given vars
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
        res.f = res.f.get0(vars, vals);
        return res;
    }

    @Override
    public double eval() {
        //check single variable
        makeFinite();
        if (!lower.isConstant() || !upper.isConstant()) {
            throw new RuntimeException("integral limits must be constant");
        }
        if (a.eval() == b.eval()) return 0;
        if (a.eval() > b.eval()) {
            throw new RuntimeException("lower bound must be less than higher bound");
        }
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

    //scale infinite bounds to 0,1  or -1,1 range
    public void makeFinite() {
        boolean isLow = lower.asCons().isInf();
        boolean isUp = upper.asCons().isInf();
        variable nv = variable.t;
        if (isLow) {
            if (isUp) {//both
                //x=t/(t-1)
                func tsq = nv.pow(2);
                func ext = cons.ONE.add(tsq).div(cons.ONE.sub(tsq).pow(2));
                func np = nv.div(cons.ONE.sub(tsq));
                this.f = f.substitute(dv, np).mul(ext);
                this.dv = nv;
                this.lower = cons.ONE.negate();
                this.upper = cons.ONE;
            }
            else {//low only
                func ext = nv.pow(-2);
                func np = upper.sub(cons.ONE.sub(nv).div(nv));
                this.f = f.substitute(dv, np).mul(ext);
                this.dv = nv;
                this.lower = cons.ZERO;
                this.upper = cons.ONE;
            }
        }
        else if (isUp) {//up only
            func ext = cons.ONE.div(cons.ONE.sub(nv).pow(2));
            func np = lower.add(nv.div(cons.ONE.sub(nv)));
            this.f = f.substitute(dv, np).mul(ext);
            this.dv = nv;
            this.lower = cons.ZERO;
            this.upper = cons.ONE;
        }
    }

    //scale into 0,1 range
    void scale() {
        double a = lower.eval();
        double b = upper.eval();
        variable nv = variable.t;
        if (a == 0) {
            //x=u*b   dx=b*du
            f = upper.mul(f.substitute(dv, upper.mul(nv)));
            return;
        }
        if (b == 0) {
            //x=u*a dx=a*du
            f = lower.mul(f.substitute(dv, lower.mul(nv))).negate();
        }
        dv = nv;
        lower = cons.ZERO;
        upper = cons.ONE;
    }

    public double riemannSum() {
        double sum = 0;
        double k = Config.integral.interval;
        double low = lower.eval();
        double dx = (upper.eval() - low + 1) / k;

        double curVal;
        for (int i = 1; i <= k; i++) {
            double p = low + i * dx;
            curVal = f.eval(dv, p);
            sum += curVal;
            System.out.println(sum * dx);
        }
        return sum * dx;
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

        return (b - a) * this.f.eval((a + b) / 2);
    }

    public double trapezoidalRule() {
        double a = lower.eval();
        double b = upper.eval();

        return (b - a) * (this.f.eval(a) + this.f.eval(b)) / 2;
    }

    public double composizeTrapezoidalRule(int n) {
        double a = lower.eval();
        double b = upper.eval();

        double total = (b - a) / n;
        return 0;

    }

    @Override
    public func derivative(variable v) {
        if (dv.eq(v) && !hasLimits()) {//not always
            return signf(f);
        }
        if (hasLimits()) {
            List<variable> l1 = lower.vars();
            List<variable> l2 = upper.vars();
            List<variable> l3 = f.vars();
            boolean b1 = l1.contains(v);
            boolean b2 = l2.contains(v);
            if (b1 || b2) {
                if (l3.contains(v)) {

                }
                else {//fubuni's theorem
                    func ls = f.substitute(dv, upper).mul(upper.derivative(v));
                    func rs = f.substitute(dv, lower).mul(lower.derivative(v));
                    return ls.sub(rs);
                }
            }
            /*if(b2){
                return a.substitude(dv,a2).mul(a2.derivative(v));
            }
            if(b1){
                return a.substitude(dv,a1).mul(a1.derivative(v)).negate();
            }*/
            return new Integral(f.derivative(v), this.dv, lower, upper);
        }
        return new Integral(f.derivative(v), this.dv);
    }

    @Override
    public func integrate(variable v) {
        if (!this.dv.eq(v)) {
            if (hasLimits()) {
                return new Integral(f.integrate(v), this.dv, lower, upper);
            }
            return new Integral(f.integrate(v), this.dv);
        }
        return null;
    }

    @Override
    public func copy0() {
        if (hasLimits()) {
            return new Integral(f, dv, lower, upper);
        }
        return new Integral(f, dv);
    }

    @Override
    public String toString2() {
        if (hasLimits()) {
            return String.format("Integral{%s d%s,%s,%s}", f, dv, lower, upper);
        }
        return String.format("Integral{%s d%s}", f, dv);
    }

    @Override
    public boolean eq0(func f) {
        Integral other = (Integral) f;
        boolean eqNolimit = this.f.eq(other.f) && dv.eq(other.dv);
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
        f = f.substitute(v, p);
        lower = lower.substitute(v, p);
        upper = upper.substitute(v, p);
        return this;
    }

}
