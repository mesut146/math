package math;

import math.core.Constant;
import math.core.Variable;
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
    public func get(Variable v, Constant c)
    {
        return this;
    }

	@Override
	public double get2(Variable v, double d)
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
    public func derivative(Variable v)
    {
        return a;
    }

    @Override
    public func integrate(Variable v)
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
    public func substitude0(Variable v, func p)
    {
        return new Anti(a.substitude0(v,p));
    }

    @Override
    public func copy0() {
        return new Anti(a);
    }
}
