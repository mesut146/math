package math.funcs;
import math.*;

public class fac extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }


	public fac(func f){
		a=f;
	}
	public fac(int i){
		a=new Constant(i);
	}
	@Override
	public func get(Variable v, Constant c)
	{
		return new fac(a.get(v, c)).s(sign);
	}

	@Override
	public double get2(Variable v, double d)
	{
		return sign*f((int)a.get2(v,d));
	}
	
	int f(int x){
		int y=1;
		for(int i=2;i<=x;i++){
			y*=i;
		}
		return y;
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
		return new fac(a);
	}

	@Override
	public String toString2()
	{
		return a.toString()+"!";
	}

	@Override
	public boolean eq2(func f)
	{
		// TODO: Implement this method
		return a.eq(f);
	}

	@Override
	public func substitude0(Variable v, func p)
	{
		// TODO: Implement this method
		return new fac(a.substitude0(v,p)).s(sign);
	}

	@Override
	public func simplify()
	{
		return this;
	}
	
	
}
