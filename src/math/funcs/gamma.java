package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class gamma extends func
{
	Variable v;
	
	public gamma(Variable v){
		a=func.parse("t^x*e^(-t)".replace("x",v.toString()));
		this.v=v;
	}


    @Override
    public func get(Variable[] v, Constant[] c) {
	    a=a.get(v,c);
        return this;
    }

    @Override
    public double eval(Variable[] v, double[] d) {
        return 0;
    }

    //d for v
	@Override
	public double eval(double d) {

		return a.get(v,d).integrate(0,1000,Variable.t);
	}

    @Override
    public String toLatex() {
        return null;
    }

    @Override
    public func derivative(Variable v) {
	    a=a.derivative(v);
        return this;
    }

    @Override
    public func integrate(Variable v) {
        return null;
    }

    @Override
    public func copy0() {
        return null;
    }

    @Override
	public String toString2() {
		return "gamma("+v+")";
	}

    @Override
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public func substitude0(Variable v, func p) {
	    a=a.substitude(v,p);
        return this;
    }
}
