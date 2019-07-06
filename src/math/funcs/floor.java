package math.funcs;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

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
	public func get(Variable[] v, Constant[] c)
	{
		return new floor(a.get(v,c)).simplify();
	}

	@Override
	public double eval(Variable[] v, double[] d)
	{
		return Math.floor(a.eval(v,d));
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
	public func substitude0(Variable v, func p)
	{
		// TODO: Implement this method
		return new floor(a.substitude0(v,p));
	}

	@Override
	public func simplify()
	{
		if(a.isConstant()){
			return new Constant(eval());
		}
		return super.simplify();
	}
	
	
	
}
