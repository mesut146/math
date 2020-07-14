package com.mesut.math.cons;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.var;

//golden ratio
public class phi extends cons {
    public phi() {
        functional = true;
        val = 1.61803398875;
        //TODO a=parse("(1+sqrt(5))/2");
    }

    @Override
    public double eval(var[] v, double[] d) {
        return val;
    }

    @Override
    public String toString2() {
        return String.valueOf('\u03d5');
    }

    @Override
    public func copy0() {
        return new phi();
    }


}
