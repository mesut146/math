package math.funcs;
import math.core.*;
import math.operator.*;

public class exp extends pow
{
    
    public exp(func f){
        super(cons.E,f);
        
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
        if (b.eq(v)){
            return this;
        }
        return new Integral(this,v);
    }

    @Override
    public boolean eq2(func f)
    {
        return b.eq(f.b);
    }
    
    
}
