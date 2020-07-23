package com.mesut.math.core;

import java.util.Set;

public class DefaultFunc extends func {
    @Override
    public func get0(variable[] v, cons[] c) {
        return this;
    }

    @Override
    public double eval(variable[] v, double[] d) {
        return 0;
    }

    @Override
    public cons evalc(variable[] v, double[] d) {
        return cons.ZERO;
    }

    @Override
    public func getReal() {
        return this;
    }

    @Override
    public func getImaginary() {
        return this;
    }

    @Override
    public func derivative(variable v) {
        return this;
    }

    @Override
    public func integrate(variable v) {
        return this;
    }

    @Override
    public func copy0() {
        return this;
    }

    @Override
    public String toString2() {
        return null;
    }

    @Override
    public String toLatex() {
        return null;
    }

    @Override
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public void vars0(Set<variable> vars) {

    }

    @Override
    public func substitude0(variable v, func p) {
        return this;
    }
}
