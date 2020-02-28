package com.mesut.math.integral;

import com.mesut.math.core.*;
import com.mesut.math.funcs.*;

public class erf extends Integral
{
    public erf(func f){
        lower =cons.ZERO;
        upper =f;
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