package math.funcs;
import math.core.*;
import math.operator.*;

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
        return new Integral(this);
    }

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }
    
    
}
