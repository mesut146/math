package com.mesut.math;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

//a*n+b
public class Linear {
    public func a, b;
    public variable v;
    public static variable defaultVar = variable.n;

    public Linear(int a, int b) {
        this.a = cons.of(a);
        this.b = cons.of(b);
        this.v = defaultVar;
    }

    public Linear(func a, func b) {
        this.a = a;
        this.b = b;
        this.v = defaultVar;
    }

    public Linear(func a, func b, variable v) {
        this.a = a;
        this.b = b;
        this.v = v;
    }

    public boolean isEven() {
        if (a.isConstant() && b.isConstant()) {
            return a.asCons().isEven() && b.asCons().isEven();
        }
        return false;
    }

    public boolean isOdd() {
        if (a.isConstant() && b.isConstant()) {
            return a.asCons().isEven() && b.asCons().isOdd();
        }
        return false;
    }

    public Linear mul(int k) {
        return new Linear(a.mul(k), b.mul(k));
    }

    public Linear div(int k) {
        return new Linear(a.div(k), b.div(k));
    }

    public Linear add(int k) {
        return new Linear(a, b.add(k));
    }

    @Override
    public String toString() {
        if (v == null) {
            v = defaultVar;
        }
        StringBuilder sb = new StringBuilder();
        if (a.is(-1)) {
            sb.append("-");
        }
        else if (!a.is(1)) {
            sb.append(a);
        }
        sb.append(v);
        if (b.sign == 1) {
            sb.append("+");
        }
        sb.append(b);
        return sb.toString();
    }


}
