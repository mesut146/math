package math.funcs;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class atan extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }


	public atan(func f){
		a=f;
	}
	@Override
	public func get(Variable v, Constant c)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public double get2(Variable v, double d)
	{
		// TODO: Implement this method
		return sign*Math.atan(a.get2(v,d));
	}

	@Override
	public func derivative(Variable v)
	{
		// TODO: Implement this method
		return null;
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
		// TODO: Implement this method
		return null;
	}

	@Override
	public String toString2()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public boolean eq2(func f)
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public func substitude0(Variable v, func p)
	{
		// TODO: Implement this method
		return null;
	}
	
}
