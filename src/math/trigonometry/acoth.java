package math.trigonometry;
import math.core.*;
import math.funcs.*;
import math.operator.*;

public class acoth extends mul
{
    func p;
    public acoth(func f){
        super(cons.ONE.div(2),new ln(f.add(cons.ONE).div(f.sub(cons.ONE))));
        p=f;
    }

    @Override
    public String toString2()
    {
        return "acoth("+p+")";
    }
}
