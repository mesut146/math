package math.funcs;
import math.Config;
import math.core.Constant;
import math.core.Variable;
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
	public func get(Variable[] v, Constant[] c)
	{
		a.get(v,c);
		return this;
	}

	@Override
	public double eval(Variable[] v, double[] d)
	{
		return sign*Math.atan(a.eval(v,d));
	}

	@Override
	public func derivative(Variable v)
	{
		//f=atany tanf=y sinf/cosf=y
		//cos^2(f)+sin^2(f)/cos^2(f)=1/cos^2(f)=y'
		return a.derivative(v).div(Constant.ONE.add(a.pow(2)));
	}

	@Override
	public func integrate(Variable v)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public func copy0()
	{
		return new atan(a).s(sign);
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
	public func substitude0(Variable v, func p)
	{
		a.substitude(v,p);
		return this;
	}
	
}
