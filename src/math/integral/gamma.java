package math.integral;
import java.util.*;
import math.*;
import math.core.*;
import math.funcs.*;

public class gamma extends Integral
{
	func v;// (v-1)!
    
    public gamma(Object o){
        this(Util.cast(o));
    }

    public gamma(func f){
        a1=cons.ZERO;
        a2=cons.INF;
        //e^-t*t^v
        v=f;

        List<var> l=vars();
        if(l.contains(var.t)){
            dv=new var("t1");
        }else{
            dv=var.t;
        }
        a=new exp(dv.negate()).mul(dv.pow(v));
    }

    @Override
    public double eval(var[] v, double[] d) {
        /*if(d.length==1&&cons.isInteger(d[0])){
            return fac.compute((int) d[0]);
        }*/
        return super.eval(v,d);
    }

    @Override
    public func get0(var[] v, cons[] c)
    {
	    a = a.get0(v, c);
        return this;
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
	public String toString2()
    {
		return "gamma(" + v + ")";
	}

    @Override
    public boolean eq2(func f)
    {
        gamma g=(gamma) f;
        return v.eq(g.v);
    }

    @Override
    public func substitude0(var v, func p)
    {
	    a = a.substitude(v, p);
        return this;
    }
}
