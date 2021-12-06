package com.mesut.math.operator;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.List;
import java.util.Set;

public class div extends func {

    public div(func f1, func f2) {
        a = f1;
        b = f2;
    }

    @Override
    public boolean isDiv() {
        return true;
    }

    @Override
    public func getReal() {
        func cd = b.getReal().pow(2).add(b.getImaginary().pow(2));
        func ac = a.getReal().mul(b.getReal());
        func bd = a.getImaginary().mul(b.getImaginary());
        return signOther(ac.add(bd).div(cd));
    }

    @Override
    public func getImaginary() {
        func cd = b.getReal().pow(2).add(b.getImaginary().pow(2));
        func ad = a.getReal().mul(b.getImaginary());
        func bc = a.getImaginary().mul(b.getReal());
        return signOther(bc.sub(ad).div(cd));
    }


    @Override
    public void vars(Set<variable> vars) {
        a.vars(vars);
        b.vars(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return signf(a.get(vars, vals).div(b.get(vars, vals)));
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * a.eval(v, vals) / b.eval(v, vals);
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(eval(vars, vals));
    }

    @Override
    public String toLatex() {
        return String.format("\\frac{%s,%s}", a, b);
    }

    @Override
    public func copy0() {
        return new div(a, b);
    }


    @Override
    public func derivative(variable v) {
        func l = a.derivative(v).mul(b);
        func r = a.mul(b.derivative(v));
        return signOther(a.derivative(v).mul(b).sub(a.mul(b.derivative(v))).div(b.pow(2)));
    }

    @Override
    public func integrate(variable v) {
        if (b.isConstant()) {
            return a.integrate(v).div(b);
        }
        return new Integral(this, v);
    }

    @Override
    public String toString2() {
        StringBuilder sb = new StringBuilder();
        if (a.isConstant() || a.isVariable()) {
            sb.append(a);
        }
        else {
            sb.append("(").append(a).append(")");
        }
        sb.append("/");
        if (b.isConstant() || b.isVariable()) {
            sb.append(b);
        }
        else {
            sb.append("(").append(b).append(")");
        }
        return sb.toString();
    }

    public func simplify() {
        a = a.simplify();
        b = b.simplify();
        if (a.is(0)) {
            if (b.is(0)) {
                return cons.NaN;
            }
            return cons.ZERO;
        }
        if (a.eq(cons.INF)) {
            return cons.INF;
        }
        if (b.eq(cons.INF)) {
            return cons.ZERO;
        }
        if (a.isDiv()) {// (a/b)/c=a/(b*c)
            return signOther(a.a.div(a.b.mul(b)));
        }
        if (b.isDiv()) {// a/(b/c)=a*c/b
            return signOther(a.mul(b.b).div(b.a));
        }
        if (a.sign == -1) {
            sign *= -1;
            a = a.copy();
            a.sign = 1;
        }
        if (b.sign == -1) {
            sign *= -1;
            b = b.copy();
            b.sign = 1;
        }
        if (b.is(1)) {
            return signf(a);
        }
        if (b.is(0)) {
            return signf(cons.INF);
        }
        if (a.eq(b)) {
            return signf(cons.ONE);
        }
        return this;
    }

    public void group() {
        func p = cons.ONE;
        func q = p;
        //(x^2+x)/x^3
        if (a.isAdd()) {

        }
        if (b.isAdd()) {

        }
        a = p;
        b = q;
    }

    private func group0(add a) {
        List<func> l = a.f;
        //x^5+x^3
        List<func> base = getFree();
        List<func> pow = getFree();
        func p = cons.ONE;
        for (int i = 0; i < l.size(); i++) {
            func f = l.get(i);
            if (f.isPow()) {
                base.add(f.a);
                pow.add(f.b);
                if (true) {

                }
            }
            else {
                base.add(f);
                pow.add(cons.ONE);
            }
        }
        return null;
    }

    @Override
    public boolean eq0(func f) {
        if (a.eq(f.a) && b.eq(f.b)) {
            return true;
        }
        return false;
    }

    @Override
    public func substitute0(variable v, func p) {
        return a.substitute(v, p).div(b.substitute(v, p));
    }


}
