package com.mesut.math.trigonometry;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

public class cos extends func {

    public cos(func f) {
        this(f, 1);
    }

    public cos(func f, int s) {
        //type=types.cos;
        f.sign = 1;
        a = f;
        sign = s;
        fx = true;
        //alter.add(parse("1-sin("+f+")^2"));
    }

    @Override
    public String toLatex() {
        // TODO: Implement this method
        return "cos(" + a.toLatex() + ")";
    }

    @Override
    public func getReal() {
        //cos(b)/2*(e^a+e^-a)
        func r = a.getReal();
        func im = a.getImaginary();
        func ls = new cos(r).simplify();
        //func rs=new exp(im).simplify().add(new exp(im.negate())).div(2);

        return ls.mul(new cosh(im));
    }

    @Override
    public func getImaginary() {
        //sin(b)/2*(e^a+e^-a)
        func r = a.getReal();
        func im = a.getImaginary();
        return new sin(r).simplify().mul(new sinh(im).negate());
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func simplify() {
        if (a.isConstant()) {
            if (!a.asCons().functional) {
			    /*if (Config.trigonomety.stay){

                }*/
                return new cons(eval());
            }

        }
        return this;
    }

    @Override
    public func copy0() {
        return new cos(a);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return new cos(a.get0(vars, vals), sign).simplify();
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * Math.cos(a.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(sign * Math.cos(a.evalc(vars, vals).decimal().doubleValue()));
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public String toString2() {
        return "cos(" + a + ")";
    }

    @Override
    public func derivative(variable v) {
        return new sin(a, -sign).mul(a.derivative(v));
    }

    @Override
    public func integrate(variable v) {
        if (a.eq(v)) {
            return new sin(a);
        }
        return new Integral(this, v);
    }

    @Override
    public func substitute0(variable v, func p) {
        return new cos(a.substitute0(v, p)).simplify();
    }


}
