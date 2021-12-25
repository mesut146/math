package com.mesut.math.ipret;

import com.mesut.math.core.func;

import java.util.ArrayList;
import java.util.List;

public class Output {
    public func res;
    List<Point> points = new ArrayList<>();
    //or graph data


    public Output(func res) {
        this.res = res.simplify();
    }

    public void print() {
        System.out.println(res);
    }

    public String text() {
        return res.toString();
    }
}
