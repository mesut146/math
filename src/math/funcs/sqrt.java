package math.funcs;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class sqrt extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }


	public sqrt(func f){
		a=f;
		b=f.pow(Constant.ONE.div(2));
	}
	public sqrt(double d){
	    this(new Constant(d));
    }
	@Override
	public func get(Variable v, Constant c)
	{
		return b.get(v,c).s(sign);
	}

	@Override
	public double eval(Variable v, double d)
	{
		return sign*b.eval(v,d);
	}

	@Override
	public func derivative(Variable v)
	{
		return b.derivative(v);
	}

	@Override
	public func integrate(Variable v)
	{
		return b.integrate(v);
	}

	@Override
	public func copy0()
	{
		return new sqrt(a);
	}

	@Override
	public String toString2()
	{
		return "sqrt("+a+")";
	}

	@Override
	public boolean eq2(func f)
	{
		return a.eq(f.a);
	}

	@Override
	public func substitude0(Variable v, func p)
	{
		return new sqrt(a.substitude0(v,p));
	}

    @Override
    public func simplify() {

        return this;
    }
}
