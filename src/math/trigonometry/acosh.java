package math.trigonometry;
import math.core.*;
import math.funcs.*;

public class acosh extends ln
{
    
    func p;
    public acosh(func f){
        super(f.add(f.pow(2).sub(1)).sqrt());
        p=f;
    }

    @Override
    public String toString2()
    {
        return "acosh("+p+")";
    }
}
