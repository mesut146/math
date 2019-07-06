package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class Li extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }


    @Override
    public func get(Variable[] v, Constant[] c)
    {
        return this;
    }

	@Override
	public double eval(Variable[] v, double[] d)
	{
		// TODO: Implement this method
		return 0;
	}

	

    @Override
    public boolean eq2(func f)
    {
        return false;
    }

    @Override
    public func derivative(Variable v)
    {
        return null;
    }

    @Override
    public func integrate(Variable v)
    {
        return new Anti(v);
    }

    @Override
    public func copy0() {
        return new Li();
    }

    @Override
    public String toString2()
    {
        return "Li("+a+")";
    }

    @Override
    public func substitude0(Variable v, func p)
    {
        return new Li().simplify();
    }

    
    
}
