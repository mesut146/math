package com.mesut.math.trigonometry;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.funcs.ln;

import java.util.Set;

public class atan extends func {

    public atan(func f) {
        a = f;
    }

    @Override
    public func getReal() {
        func r = a.getReal();
        func im = a.getImaginary();
        //i/2*ln(1+((2i)/(r+i*(im-1))))
        func f = cons.i.div(2)
                .mul(new ln(
                        cons.ONE.add(
                                cons.TWO.mul(cons.i)).div(r.add(cons.i.mul(im.sub(1))))));
        return f.getReal();
    }

    @Override
    public func getImaginary() {
        func r = a.getReal();
        func im = a.getImaginary();
        //i/2*ln(1+((2i)/(r+i*(im-1))))
        func f = cons.i.div(2)
                .mul(new ln(
                        cons.ONE.add(
                                cons.TWO.mul(cons.i)).div(r.add(cons.i.mul(im.sub(1))))));
        return f.getImaginary();
    }

    @Override
    public String toLatex() {
        return "atan(" + a.toLatex() + ")";
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func get0(variable[] v, cons[] c) {
        a.get0(v, c);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] d) {
        return sign * Math.atan(a.eval(v, d));
    }

    @Override
    public cons evalc(variable[] v, double[] d) {
        return new cons(sign * Math.atan(a.evalc(v, d).decimal().doubleValue()));
    }

    @Override
    public func derivative(variable v) {
        //f=atany tanf=y sinf/cosf=y
        //cos^2(f)+sin^2(f)/cos^2(f)=1/cos^2(f)=y'
        return a.derivative(v).div(cons.ONE.add(a.pow(2)));
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        return new atan(a);
    }

    @Override
    public String toString2() {
        return "atan(" + a.toString() + ")";
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitude0(variable v, func p) {
        a.substitude(v, p);
        return this;
    }

    @Override
    public func simplify() {
        if (a.is(0)) {
            return cons.ZERO;
        }
        else if (a.is(1)) {
            return cons.PI.div(4);
        }
        else if (a.is(-1)) {
            return cons.PI.div(4).negate();
        }
        else if (a.eq(cons.INF)) {
            return cons.PI.div(2);
        }
        else if (a.eq(cons.INF.negate())) {
            return cons.PI.div(2);
        }

        return this;
    }


}
