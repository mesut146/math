package com.mesut.math.trigonometry;

import com.mesut.math.core.*;
import com.mesut.math.funcs.*;
import com.mesut.math.operator.*;

public class atanh extends mul
{
    func p;
    public atanh(func f){
        super(cons.ONE.div(2),new ln(cons.ONE.add(f).div(cons.ONE.sub(f))));
        p=f;
    }

    @Override
    public String toString2()
    {
        return "atanh("+p+")";
    }
}
