package com.mesut.math.integral;

import com.mesut.math.Util;
import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.funcs.exp;

import java.util.List;

public class gamma extends Integral {
    func v;// (v-1)!

    public gamma(Object o) {
        this(Util.cast(o));
    }

    public gamma(func f) {
        lower = cons.ZERO;
        upper = cons.INF;
        //e^-t*t^v
        v = f;

        List<variable> l = vars();
        if (l.contains(variable.t)) {
            dv = new variable("t1");
        }
        else {
            dv = variable.t;
        }
        a = new exp(dv.negate()).mul(dv.pow(v));
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        /*if(d.length==1&&cons.isInteger(d[0])){
            return fac.compute((int) d[0]);
        }*/
        return super.eval(v, vals);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get0(vars, vals);
        return this;
    }

    @Override
    public func derivative(variable v) {
        a = a.derivative(v);
        return this;
    }

    @Override
    public func integrate(variable v) {
        a = a.integrate(v);
        return this;
    }

    @Override
    public String toString2() {
        return "gamma(" + v + ")";
    }

    @Override
    public boolean eq0(func f) {
        gamma g = (gamma) f;
        return v.eq(g.v);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }
}
