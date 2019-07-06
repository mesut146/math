package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;
import math.op.pow;

public class exp extends pow
{
    public exp(func f){
        super(Constant.E,f);
        a=f;
    }

    @Override
    public String getType() {
        return "exp";
    }

    @Override
    public func get(Variable v, Constant c)
    {
        return new exp(a.get(v, c)).s(sign);
    }

	@Override
	public double eval(Variable v, double d)
	{
		return sign*super.eval(v,d);
	}


    @Override
    public func derivative(Variable v)
    {
        return mul(a.derivative(v));
    }

    @Override
    public func integrate(Variable v)
    {
        if (a.eq(v)){
            return this;
        }
        return new Anti(this);
    }

    @Override
    public String toString2()
    {
        if (sign==-1){
            return "-(e^"+a+")";
        }
        return "e^"+a;
    }

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }
    
    
}
