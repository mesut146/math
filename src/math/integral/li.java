package math.integral;
import java.util.*;
import math.core.*;
import math.funcs.*;

public class li extends Integral
{
    
    public li(func f){
        a1=cons.TWO;
        a2=f;
        setDummy(f,var.t);
        a=new ln(dv).inv();
    }

    @Override
    public String toLatex()
    {
        return toString();
    }

    @Override
    public String toString2()
    {
        return "li("+a+")";
    }
    
}
