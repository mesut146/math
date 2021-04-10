package com.mesut.math.funcs;

import com.mesut.math.Config;
import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.trigonometry.atan;

import java.util.Set;

public class ln extends func {

    public ln(func f) {
        a = f;
        fx = true;
		/*if(f.isPow()){
			//alter.add(f.b.mul(new ln(f.a)).simplify());
		}*/
    }

    @Override
    public func getReal() {
        func re = a.getReal();
        func im = a.getImaginary();
        func r = re.pow(2).add(im.pow(2));
        return signOther(new ln(r).div(2)).simplify();
    }

    @Override
    public func getImaginary() {
        func re = a.getReal();
        func im = a.getImaginary();
        if (Config.lnFullImaginary) {
            return signOther(new atan(im.div(re)).add(func.parse("2*pi*n"))).simplify();
        }
        else {
            return signOther(new atan(im.div(re))).simplify();
        }
    }

    @Override
    public String toLatex() {
        return "ln(" + a.toLatex() + ")";
    }

    @Override
    public void vars(Set<variable> vars) {
        a.vars(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return signOther(new ln(a.get(vars, vals)));
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        return sign * Math.log(a.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return new cons(sign * Math.log(a.evalc(vars, vals).decimal().doubleValue()));
    }

    @Override
    public String toString2() {
        return "ln(" + a + ")";
    }

    @Override
    public func derivative(variable v) {
        return signf(a.derivative(v).div(a));
    }

    @Override
    public func integrate(variable v) {
        if (a.eq(v)) {
            return mul(v).sub(v);
        }
        return new Integral(this, v);
    }

    @Override
    public func copy0() {
        return new ln(a);
    }

    @Override
    public boolean eq0(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitute0(variable v, func p) {
        return new ln(a.substitute0(v, p));
    }

    @Override
    public func simplify() {
        a = a.simplify();
        if (a.isPow() && a.a.eq(cons.E)) {
            return signf(a.b);
        }
        if (a.eq(cons.E)) {
            return signf(cons.ONE);
        }
        if (a.is(1)) {
            return cons.ZERO;
        }
        return this;
    }


}
