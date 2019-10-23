package math.integral;

import math.core.*;
import math.funcs.*;

public class erf extends Integral
{
    public erf(func f){
        lim=true;
        a1=cons.ZERO;
        a2=f;
        setDummy(f,var.t);
        func coef=cons.TWO.div(cons.PI.sqrt());
        a=new exp(dv.pow(2).negate()).mul(coef);
    }

    @Override
    public String toString2()
    {
        return "erf("+a+")";
    }
    
    
}
