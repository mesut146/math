package math.funcs;

import math.core.*;
import math.operator.*;

public class loga_b extends div
{
    public loga_b(func p1,func p2){
        super(new ln(p2),new ln(p1));
    }

    @Override
    public String toString2()
    {
        return "log_"+a+"("+b+")";
    }
    
    
}
