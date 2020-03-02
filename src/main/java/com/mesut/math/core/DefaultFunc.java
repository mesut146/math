package com.mesut.math.core;

import java.util.Set;

public class DefaultFunc extends func {
    @Override
    public func get0(var[] v, cons[] c) {
        return null;
    }

    @Override
    public double eval(var[] v, double[] d) {
        return 0;
    }

    @Override
    public cons evalc(var[] v, double[] d) {
        return null;
    }

    @Override
    public func getReal() {
        return null;
    }

    @Override
    public func getImaginary() {
        return null;
    }

    @Override
    public func derivative(var v) {
        return null;
    }

    @Override
    public func integrate(var v) {
        return null;
    }

    @Override
    public func copy0() {
        return null;
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
    public void vars0(Set<var> vars) {

    }

    @Override
    public func substitude0(var v, func p) {
        return null;
    }
}
