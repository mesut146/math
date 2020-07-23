package com.mesut.math.solver;

import com.mesut.math.Util;
import com.mesut.math.core.variable;

public class linl {
    //v=a*n+b
    variable v;
    lin l;

    public linl() {

    }

    public linl(Object vo, lin lo) {
        v = (variable) Util.cast(vo);
        l = lo;
    }

    @Override
    public String toString() {
        return v + "=" + l;
    }


}
