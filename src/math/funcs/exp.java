package math.funcs;
import math.*;
import math.core.cons;
import math.core.var;
import math.core.func;
import math.operator.pow;

public class exp extends pow
{
    public exp(func f){
        super(cons.E,f);
        a=f;
    }

    @Override
    public String getType() {
        return "exp";
    }

    @Override
    public func get(var[] v, cons[] c)
    {
        a=a.get(v,c);
        return this;
        //return new exp(a.get(v, c)).sign(sign);
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*super.eval(v,d);
	}
    @Override
    public cons evalc(var[] v, double[] d)
    {
        return (cons)signto(super.evalc(v,d));
    }

    @Override
    public func derivative(var v)
    {
        return mul(a.derivative(v));
    }

    @Override
    public func integrate(var v)
    {
        if (a.eq(v)){
            return this;
        }
        return new Anti(this);
    }

    @Override
    public String toString2()
    {
        return "e^"+a;
    }

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }
    
    
}
