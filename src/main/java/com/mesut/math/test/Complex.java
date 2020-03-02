package com.mesut.math.test;

import com.mesut.math.core.*;
import com.mesut.math.trigonometry.*;

public class Complex
{
    cons a,b;
    func f;

    public Complex(){
        f=a.add(cons.i.mul(b));
    }
    func exp(){
        //e^fx+iy
        return cons.E.pow(a).mul(new cos(b).add(cons.i.mul(new sin(b))));
    }

    @Override
    public String toString()
    {
        return a+(b.sign==1?"+":"")+"i*"+b;
    }
    
    
}
