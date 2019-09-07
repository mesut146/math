package math.funcs;
import math.Config;
import math.core.cons;
import math.core.var;
import math.core.func;

public class atan extends func
{

    @Override
    public String toLatex()
    {
        return "atan("+a.toLatex()+")";
    }


	public atan(func f){
		a=f;
	}
	@Override
	public func get(var[] v, cons[] c)
	{
		a.get(v,c);
		return this;
	}

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*Math.atan(a.eval(v,d));
	}
    
    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(sign*Math.atan(a.evalc(v,d).decimal().doubleValue()));
    }

	@Override
	public func derivative(var v)
	{
		//f=atany tanf=y sinf/cosf=y
		//cos^2(f)+sin^2(f)/cos^2(f)=1/cos^2(f)=y'
		return a.derivative(v).div(cons.ONE.add(a.pow(2)));
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
		return new atan(a).sign(sign);
	}

	@Override
	public String toString2()
	{
		return "atan("+a.toString()+")";
	}

	@Override
	public boolean eq2(func f)
	{
		return a.eq(f.a);
	}

	@Override
	public func substitude0(var v, func p)
	{
		a.substitude(v,p);
		return this;
	}
	
}
