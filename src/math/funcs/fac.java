package math.funcs;
import math.core.cons;
import math.core.var;
import math.core.func;
import java.math.*;

public class fac extends func
{

    @Override
    public String toLatex()
    {
        return toString();
    }


	public fac(func f){
		a=f;
	}
	public fac(int i){
		a=new cons(i);
	}
	@Override
	public func get(var[] v, cons[] c)
	{
        a=a.get(v,c);
        return this;
		//return new fac(a.get(v, c)).sign(sign);
	}

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*f((int)a.eval(v,d));
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        BigDecimal bd=new BigDecimal(1);
        for(int i=2;i<=a.evalc(v,d).decimal().intValue();i++){
            bd=bd.multiply(new BigDecimal(i));
        }
        return sc(new cons(bd));
    }

    
	
	int f(int x){
		int y=1;
		for(int i=2;i<=x;i++){
			y*=i;
		}
		return y;
	}

	@Override
	public func derivative(var v)
	{
		// TODO: Implement this method
		if (a.isConstant()){
			return cons.ZERO;
		}
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
		return a.eq(f);
	}

	@Override
	public func substitude0(var v, func p)
	{
		return new fac(a.substitude0(v,p)).sign(sign);
	}

	@Override
	public func simplify()
	{
		return this;
	}
	
	
}
