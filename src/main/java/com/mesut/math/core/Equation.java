package com.mesut.math.core;

import java.util.Set;

public class Equation extends func {
    func left;//var or funcCall
    func right;

    public Equation(func left, func right) {
        this.left = left;
        this.right = right;
    }

    public func getLeft() {
        return left;
    }

    public void setLeft(func left) {
        this.left = left;
    }

    public variable leftAsVar() {
        return (variable) left;
    }

    public func getRight() {
        return right;
    }

    public void setRight(func right) {
        this.right = right;
    }

    @Override
    public String toString2() {
        return left + " = " + right;
    }

    @Override
    public void vars(Set<variable> vars) {
        left.vars(vars);
        right.vars(vars);
    }

    @Override
    public func substitute0(variable v, func p) {
        left = left.substitute(v, p);
        right = right.substitute(v, p);
        return this;
    }

    @Override
    public boolean isEq() {
        return true;
    }
}
