package math;

import math.core.cons;
import math.core.func;
import math.funcs.*;
import math.cons.*;

public class Complex
{
    cons a,b;
    func f;

    public Complex(){
        f=a.add(i.i.mul(b));
    }
    func exp(){
        //e^fx+iy
        return cons.E.pow(a).mul(new cos(b).add(i.i.mul(new sin(b))));
    }

    @Override
    public String toString()
    {
        return a+(b.sign==1?"+":"")+"i*"+b;
    }
    
    
}
