package math.funcs;
import math.*;
import math.core.cons;
import math.core.var;
import math.core.func;
import math.core.*;
import java.util.*;

public class gamma extends Integral
{
    
    static{
        register("gamma",gamma.class);
    }
	func v;// (v-1)!
    
    public gamma(Object o){
        super(null,null,cons.ZERO,cons.INF);//e^-t*t^v
        v=Util.cast(o);

        List<var> l=vars();
        if(l.contains(var.t)){
            dv=new var("t1");
        }else{
            dv=var.t;
        }
        a=new exp(dv.negate()).mul(dv.pow(v));
        register("gamma",gamma.class);
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
	    a = a.get0(v, c);
        return this;
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
