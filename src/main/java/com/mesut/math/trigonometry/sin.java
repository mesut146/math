package com.mesut.math.trigonometry;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class sin extends func {

    public sin(func f) {
        this(f, 1);
    }

    public sin(func f, int s) {
        //type=types.sin;
        a = f;
        sign = s * f.sign;
        f.sign = 1;
        fx = true;
        //alter.add(parse("1-cos("+f+")^2"));
    }

    @Override
    public String toLatex() {
        return "sin(" + a.toLatex() + ")";
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return new sin(a.get0(vars, vals), sign).simplify();
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * Math.sin(a.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(sign * Math.sin(a.evalc(vars, vals).decimal().doubleValue()));
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public String toString2() {
        return "sin(" + a + ")";
    }

    @Override
    public func derivative(variable v) {
        return new cos(a, sign).mul(a.derivative(v));
    }

    @Override
    public func integrate(variable v) {
        if (a.eq(v)) {
            return new cos(a, sign).negate();
        }
        return new Integral(this, v);
    }

    @Override
    public func substitute0(variable v, func p) {
        return new sin(a.substitute0(v, p), sign);
    }

    @Override
    public func copy0() {
        return new sin(a);
    }

    @Override
    public func simplify() {
        //System.out.println("sin simp "+a);
        if (a.isConstant()) {
            cons p = cons.PI;
            if (a.is(0)) {
                return cons.ZERO;
            }
        }
        return this;//pi/2=1  pi/3=sqrt(3)/2
    }

    @Override
    public func getReal() {
        //sin(a)/2*(e^b-e^-b);
        func r = a.getReal();
        func im = a.getImaginary();
        return new sin(r).simplify().mul(new cosh(im));
    }

    @Override
    public func getImaginary() {
        //cos(a)/2*(e^b-e^-b);
        func r = a.getReal();
        func im = a.getImaginary();
        return new cos(r).simplify().mul(new sinh(im));
    }
     

	/*static HashMap<func,func> h=new HashMap<func,func>();
    static {
        h.put(parse("pi"),parse("0"));
        h.put(parse("pi/2"),parse("1"));
        h.put(parse("pi/3"),parse("sqrt(3)/2"));
        h.put(parse("pi/4"),parse("sqrt(2)/2"));
    }*/

    a equ(func a) {
        cons k;
        a r = new a();
        if (a.isMul() && a.f.size() == 2) {
            if (a.f.get(0).eq(cons.PI) && a.f.get(1).isConstant()) {

            }
            else if (a.f.get(1).eq(cons.PI) && f.get(0).isConstant()) {

            }

        }
        else {
            k = cons.ONE;
        }
        return r;
    }

    static class a {
        func f, g;
        boolean b = false;
    }
}
