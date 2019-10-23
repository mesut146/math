package math.trigonometry;

import math.core.*;
import math.operator.*;
import math.funcs.*;

public class sinh extends div
{
    static{
        register("sinh",sinh.class);
    }
    
    func p;
    public sinh(func f){
        //(e^f-e^-f)/2
        super(new exp(f).sub(new exp(f.negate())),cons.TWO);
        p=f;
    }

    @Override
    public String toString2()
    {
        return "sinh("+p+")";
    }
    
    
}
