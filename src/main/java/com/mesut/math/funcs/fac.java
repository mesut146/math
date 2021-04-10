package com.mesut.math.funcs;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.integral.gamma;

import java.math.BigDecimal;
import java.util.Set;

//numeric factorial

public class fac extends func {
    public fac(func f) {
        a = f;
        alter.add(new gamma(f.add(1)));
    }

    public fac(int i) {
        this(new cons(i));
    }

    public static int compute(int x) {
        int result = 1;
        for (int i = 2; i <= x; i++) {
            result *= i;
        }
        return result;
    }

    @Override
    public func getReal() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func getImaginary() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toLatex() {
        return toString();
    }

    @Override
    public void vars(Set<variable> vars) {
        a.vars(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get0(vars, vals);
        return this;
        //return new fac(a.get(v, c)).sign(sign);
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        double aval = a.eval(v, vals);
        if (!cons.isInteger(aval)) {
            System.out.println("can't compute factorial of a real number use gamma function instead");
        }
        return sign * compute((int) aval);
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        BigDecimal bd = new BigDecimal(1);
        for (int i = 2; i <= a.evalc(vars, vals).decimal().intValue(); i++) {
            bd = bd.multiply(new BigDecimal(i));
        }
        return sc(new cons(bd));
    }

    @Override
    public func derivative(variable v) {
        if (a.isConstant()) {
            return cons.ZERO;
        }
        return signf(alter.get(0).derivative(v));
    }

    @Override
    public func integrate(variable v) {
        if (a.isConstant()) {
            return mul(v);
        }
        return signf(alter.get(0).integrate(v));
    }

    @Override
    public func copy0() {
        return new fac(a);
    }

    @Override
    public String toString2() {
        return a.toString() + "!";
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f);
    }

    @Override
    public func substitute0(variable v, func p) {
        return signOther(new fac(a.substitute0(v, p)));
    }

    @Override
    public func simplify() {
        return this;
    }


}
