package com.mesut.math.cons;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

//golden ratio
public class phi extends cons {

    public phi() {
        functional = true;
        //val = 1.61803398875;
        val = (Math.sqrt(5) + 1) / 2;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
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
