package com.mesut.math.core;

import java.util.Set;

//derivative of a expr without actually differentiating
public class derivative extends func {

    func func;
    variable v;

    public derivative(func func, variable v) {
        this.func = func;
        this.v = v;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return new derivative(func.get(vars, vals), v);
    }

    @Override
    public func derivative(variable v) {
        return new derivative(this, v);
    }

    @Override
    public func integrate(variable v) {
        if (v.eq(v)) {
            return func;
        }
        return new Integral(this, v);
    }

    @Override
    public func copy0() {
        return new derivative(func, v);
    }

    @Override
    public String toString2() {
        return String.format("d/d%s(%s)", v, func);
    }

    @Override
    public boolean eq0(func f) {
        return func.eq(f);
    }

    @Override
    public void vars(Set<variable> vars) {
        func.vars(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        return null;
    }
}
