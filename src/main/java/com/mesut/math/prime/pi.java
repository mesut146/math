package com.mesut.math.prime;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Set;

//number of primes less than p
public class pi extends func {

    public pi(func p) {
        //p>=1
        a = p;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        int x = (int) a.eval(v, vals);
        int i = 0;
        while (PrimeGenerator.primes[i] <= x) {
            i++;
        }
        return i;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        // TODO: Implement this method
        return new cons(eval(vars, vals));
    }

    @Override
    public func getReal() {
        throw new RuntimeException("invalid call to real");
    }

    @Override
    public func getImaginary() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toLatex() {
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func derivative(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toString2() {
        return "pi(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f.a);
    }

    @Override
    public void vars(Set<variable> vars) {
        // TODO: Implement this method
        a.vars(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }

    @Override
    public func simplify() {
        if (a.vars().isEmpty()) {
            return new cons(eval()).signBy(sign);
        }
        return this;
    }
}
