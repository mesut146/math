package com.mesut.math.solver;

import com.mesut.math.Util;
import com.mesut.math.core.var;

public class linl {
    //v=a*n+b
    var v;
    lin l;

    public linl() {

    }

    public linl(Object vo, lin lo) {
        v = (var) Util.cast(vo);
        l = lo;
    }

    @Override
    public String toString() {
        return v + "=" + l;
    }


}
