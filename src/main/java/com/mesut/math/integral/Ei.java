package com.mesut.math.integral;
import com.mesut.math.core.*;
import com.mesut.math.funcs.*;

public class Ei extends Integral
{

    public Ei(func f){
        lower =f;
        upper =cons.INF;
        setDummy(f,var.t);
        a=new exp(dv.negate().div(dv));
        sign=-1;
    }
    
    @Override
    public String toString2()
    {
        return "Ei("+a+")";
    }
    
    
}