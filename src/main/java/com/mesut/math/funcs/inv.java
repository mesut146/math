package com.mesut.math.funcs;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.taylor;
import com.mesut.math.taylorsym;

import java.util.Set;

//inverse of a function
public class inv extends func {
    taylorsym sym;
    taylor taylor;

    //inverse of f
    public inv(func f) {
        a = f;
        sym = new taylorsym();
        taylor = new taylor();
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get0(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        // TODO: Implement this method
        return 0;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        // TODO: Implement this method
        return new cons(eval(vars, vals));
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
    public func derivative(variable v) {
        return cons.ONE.div(a.derivative(v));
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        // TODO: Implement this method
        return new inv(a.copy());
    }

    @Override
    public String toString2() {
        // TODO: Implement this method
        return "inv(" + a + ")";
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }

    @Override
    public taylor taylor(double at, int n) {
        func df = a.derivative();
        func d = variable.x;
        //centered f(at)
        taylor.var = a.vars().get(0);
        func center = taylor.var.sub(a.eval(at));

        taylor.put(at, 0, center);
        for (int i = 1; i <= n; i++) {
            d = d.derivative().div(df);
            //System.out.println(d);
            taylor.put(d.eval(at) / new fac(i).eval(), i, center);
        }
        taylor.simplify();
        return taylor;
    }


}
