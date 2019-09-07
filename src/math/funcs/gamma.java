package math.funcs;
import math.*;
import math.core.cons;
import math.core.var;
import math.core.func;
import math.core.*;

public class gamma extends func
{
	var v;// (v-1)!
    var dt;

	public gamma(var v)
    {
        if (v.eq2(var.t))
        {
            dt=new var("u");
            a = func.parse(String.format("u^(%s-1)*e^(-u)", v));
        }
        else
        {
            dt=var.t;
            a = func.parse(String.format("t^(%s-1)*e^(-t)", v));
        }
		this.v = v;
	}

    @Override
    public func get(var[] v, cons[] c)
    {
	    a = a.get(v, c);
        return this;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        return a.get(v, d).integrate(0, 1000, dt);
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(eval(v, d));
    }

    //d for v
	@Override
	public double eval(double d)
    {
        //System.out.println(a.get(v,d));
		return a.get(v, d).integrate(0, 1000, dt);
	}

    @Override
    public String toLatex()
    {
        return toLatex();
    }

    @Override
    public func derivative(var v)
    {
	    a = a.derivative(v);
        return this;
    }

    @Override
    public func integrate(var v)
    {

        a = a.integrate(v);
        return this;
    }

    @Override
    public func copy0()
    {
        return null;
    }

    @Override
	public String toString2()
    {
		return "gamma(" + v + ")";
	}

    @Override
    public boolean eq2(func f)
    {
        return false;
    }

    @Override
    public func substitude0(var v, func p)
    {
	    a = a.substitude(v, p);
        return this;
    }
}
