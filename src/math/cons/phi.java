package math.cons;
import math.core.*;

public class phi extends cons
{
    public phi(){
        val=1.61803398875;
        a=parse("(1+sqrt(5))/2");
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        return val;
    }
    
    @Override
    public String toString2()
    {
        return String.valueOf('\u03d5');
    }

    @Override
    public func copy0()
    {
        return new phi();
    }
    
    
    
}
