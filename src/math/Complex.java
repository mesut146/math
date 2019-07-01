package math;

import math.funcs.*;
import math.cons.*;

public class Complex
{
    Constant a,b;
    func f;

    public Complex(){
        f=a.add(i.i.mul(b));
    }
    func exp(){
        //e^x+iy
        return Constant.E.pow(a).mul(new cos(b).add(i.i.mul(new sin(b))));
    }

    @Override
    public String toString()
    {
        return a+(b.sign==1?"+":"")+"i*"+b;
    }
    
    
}
