package com.mesut.math.funcs;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.operator.mul;
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
        func r = a.getReal().pow(2).add(a.getImaginary().pow(2));
        return sign(new ln(r.sqrt().simplify())).simplify();
    }

    @Override
    public func getImaginary() {
        func re = a.getReal().simplify();
        func im = a.getImaginary().simplify().div(re).simplify();
        return sign(new atan(im)).simplify();
    }

    @Override
    public String toLatex() {
        return "ln(" + a.toLatex() + ")";
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
    }

    @Override
    public func get0(variable[] v, cons[] c) {
        return sign(new ln(a.get(v, c)));
    }

    @Override
    public double eval(variable[] v, double[] d) {
        return sign * Math.log(a.eval(v, d));
    }

    @Override
    public cons evalc(variable[] v, double[] d) {
        return new cons(sign * Math.log(a.evalc(v, d).decimal().doubleValue()));
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

        return new mul(this, cons.ONE).byParts();
        //return new Anti(this);
    }

    @Override
    public func copy0() {
        return new ln(a);
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitude0(variable v, func p) {
        return new ln(a.substitude0(v, p));
    }

    @Override
    public func simplify() {
		/*if(a.isConstant()){
			if(a.eq(Constant.E)){
				return Constant.ONE;
			}
			return new Constant(this);
		}*/
        if (a.isPow() && a.a.eq(cons.E)) {
            return signf(a.b);
        }
        if (a.eq(cons.E)) {
            return signf(cons.ONE);
        }
        if (a.is(1)) {
            return cons.ZERO;
        }
		/*for(func r:rules.keySet()){
			if(r.getClass()==getClass()){
				if(this.eq(r)){
                    //matcher?
					func g=rules.eval(r).simplify();
					//System.out.println("g="+g);
					return r.name().equals("fx")?g.b:g;
				}
			}
		}*/
        return this;
    }


}
