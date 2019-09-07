package math.funcs;
import math.core.cons;
import math.core.var;
import math.core.func;
import java.math.*;

public class floor extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

	
	public floor(func f){
		a=f;
	}

	@Override
	public func get(var[] v, cons[] c)
	{
		return new floor(a.get(v,c)).simplify();
	}

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*Math.floor(a.eval(v,d));
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return new cons(sign*Math.floor(a.evalc(v,d).decimal().doubleValue()));
    }

	@Override
	public func derivative(var v)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public func integrate(var v)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public func copy0()
	{
		// TODO: Implement this method
		return new floor(a);
	}

	@Override
	public String toString2()
	{
		// TODO: Implement this method
		return "floor("+a+")";
	}

	@Override
	public boolean eq2(func f)
	{
		// TODO: Implement this method
		return a.eq(f.a);
	}

	@Override
	public func substitude0(var v, func p)
	{
		// TODO: Implement this method
		return new floor(a.substitude0(v,p));
	}

	@Override
	public func simplify()
	{
		if(a.isCons0()){
			return evalc();
		}
		return this;
	}
	
	
	
}
