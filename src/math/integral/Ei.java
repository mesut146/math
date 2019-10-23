package math.integral;
import java.util.*;
import math.core.*;
import math.funcs.*;

public class Ei extends Integral
{

    public Ei(func f){
        lim=true;
        a1=f;
        a2=cons.INF;
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
