package com.mesut.math.operator;

import com.mesut.math.core.func;

import java.util.List;

class holder {
    func f;
    double d;
    List<func> l;
    boolean b;
    int sign;

    public holder(func i, double o, List<func> li, boolean bo) {
        f = i;
        d = o;
        l = li;
        b = bo;
    }
}
