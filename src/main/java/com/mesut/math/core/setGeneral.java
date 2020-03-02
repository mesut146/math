package com.mesut.math.core;

import com.mesut.math.Util;

//set with general term
public class setGeneral extends set {

    public var v;//var of general term

    public setGeneral(Object cn, Object v, String name) {
        this.a = Util.cast(cn);
        this.v = Util.var(v);
        this.name = name;
    }

    //fil list from general term
    public void generate() {
        for (int i = start; i <= end; i++) {
            list.add(a.get(v, i));
        }
    }

    @Override
    public String toString2() {
        return name + "{" + "}";
    }
}
