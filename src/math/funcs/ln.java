package math.funcs;

import math.*;
import math.core.cons;
import math.core.var;
import math.core.func;
import math.operator.*;
import java.math.*;
import java.util.*;

public class ln extends func
{
    static{
        register("ln",ln.class);
    }
    
    @Override
    public func getReal()
    {
        func r=a.getReal().pow(2).add(a.getImaginary().pow(2));
        //System.out.println(r.sqrt().simplify());
        return new ln(r.sqrt().simplify()).simplify();
    }

    @Override
    public func getImaginary()
    {
        func ar=a.getReal().simplify();
        
        func ba=a.getImaginary().simplify().div(ar).simplify();
        return new atan(ba).simplify();
    }


    public ln(func f) {
        type = types.ln;
        a = f;
        fx = true;
		/*if(f.isPow()){
			//alter.add(f.b.mul(new ln(f.a)).simplify());
		}*/
    }

    @Override
    public String toLatex() {
        return "ln(" + a.toLatex() + ")";
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
    }

    @Override
    public func get0(var[] v, cons[] c) {
        return signto(new ln(a.get0(v, c)));
    }

    @Override
    public double eval(var[] v, double[] d) {
        return sign * Math.log(a.eval(v, d));
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(sign*Math.log(a.evalc(v,d).decimal().doubleValue()));
    }

    @Override
    public String toString2() {
        return "ln(" + a + ")";
    }

    @Override
    public func derivative(var v) {
        return signto(a.derivative(v).div(a));
    }

    @Override
    public func integrate(var v) {
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
    public func substitude0(var v, func p) {
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
        if (a.eq(parse("e^f(x)"))) {
            return a.b;
        }
        if (a.eq(cons.E)) {
            return cons.ONE;
        }
        if(a.is(1)){
            return  cons.ZERO;
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
