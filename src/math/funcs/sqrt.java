package math.funcs;
import math.core.cons;
import math.core.var;
import math.core.func;

public class sqrt extends func
{

    @Override
    public String toLatex()
    {
        return "\\sqrt{"+a.toLatex()+"}";
    }


	public sqrt(func f){
		a=f;
		b=f.pow(cons.ONE.div(2));
	}
	public sqrt(double d){
	    this(new cons(d));
    }
	@Override
	public func get(var[] v, cons[] c)
	{
		return signto(b.get(v,c));
	}

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*b.eval(v,d);
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return sc(b.evalc(v,d));
    }

	@Override
	public func derivative(var v)
	{
		return b.derivative(v);
	}

	@Override
	public func integrate(var v)
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
	public func substitude0(var v, func p)
	{
		return new sqrt(a.substitude0(v,p));
	}

    @Override
    public func simplify() {
        if(a.isPow()&&a.b.isConstant()&&a.b.eval()%2==0){
            a=a.b.div(2);
            return this;
        }
        return this;
    }
}
