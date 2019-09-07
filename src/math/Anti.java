package math;

import math.core.cons;
import math.core.var;
import math.core.func;

public class Anti extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

    public Anti(func p){
        a=p;
    }

    @Override
    public func get(var[] v, cons[] c)
    {
        return this;
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return null;
    }


	@Override
	public double eval(var[] v, double[] d)
	{
		// TODO: Implement this method
		return 0;
	}

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f);
    }

    @Override
    public func derivative(var v)
    {
        return a;
    }

    @Override
    public func integrate(var v)
    {
        return new Anti(this);
    }
    
    @Override
    public String toString2()
    {
        if(a.isDiv()){
            if(a.a.is(1)&&a.b.type==types.ln&&a.b.a.isVariable()){
                //1/lnx
                return "li(fx)";
            }
            if(a.a.isPow()&&a.a.b.isVariable()&&a.b.isVariable()){
                //e^fx/fx
                return "Ei(fx)";
            }
        }
        return "integrate("+a+")";
    }

    @Override
    public func substitude0(var v, func p)
    {
        return new Anti(a.substitude0(v,p));
    }

    @Override
    public func copy0() {
        return new Anti(a);
    }
}
