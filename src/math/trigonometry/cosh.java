package math.trigonometry;
import math.core.*;
import math.funcs.*;
import math.operator.*;

public class cosh extends div
{
    static{
        register("cosh",cosh.class);
    }
    
    func p;
    public cosh(func f){
        //(e^f+e^-f)/2
        super(new exp(f).add(new exp(f.negate())),cons.TWO);
        p=f;
    }

    @Override
    public String toString2()
    {
        return "cosh("+p+")";
    }
    
    
}
