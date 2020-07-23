package com.mesut.math.funcs;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.operator.pow;

public class sqrt extends pow {

    public sqrt(func f) {
        super(f, cons.ONE.div(2));
    }

    public sqrt(double d) {
        this(new cons(d));
    }

    @Override
    public String toLatex() {
        return "\\sqrt{" + a.toLatex() + "}";
    }

    @Override
    public func copy0() {
        return new sqrt(a);
    }

    @Override
    public String toString2() {
        return "sqrt(" + a + ")";
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f.a);
    }

    @Override
    public func simplify() {
        //sqrt(a^2b)=a^b
        if (a.isPow() && a.b.isConstant() && a.b.eval() % 2 == 0) {
            a.b = a.b.div(2);
            a = a.simplify();
            return this.simplify();
        }
        //eval if both are integer
        if (a.isCons0() && a.asCons().isInteger()) {
            cons d = evalc();
            if (d.isInteger()) {
                return d;
            }
        }

        return this;
    }
}
