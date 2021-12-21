package com.mesut.math.operator;

import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//ax^n+bx^(n-1)+..c
public class poly extends add {

    public poly(String s) {
        add p = (add) func.parse(s);
        list = p.list;
    }

    @Override
    public func add(func f) {
        // TODO: Implement this method
        return super.add(f);
    }

    @Override
    public func mul(func f) {
        //x-2
        //x-1
        //System.out.println(this);
        //System.out.println(f);
        List<func> l = getFree();
        for (func c : this.list) {
            for (func d : f.list) {
                l.add(c.mul(d));
            }
        }
        this.list = l;
        return this.order().simplify();
    }

    int g(func p) {
        if (p.isConstant()) {
            return Integer.MAX_VALUE;
        }
        else if (p.isMul()) {
            func pw = p.list.get(1);

            if (pw.eq(variable.x)) {
                return 1;
            }
            return (int) pw.b.eval();
        }
        else if (p.isPow()) {
            return (int) p.b.eval();
        }
        return 0;
    }

    poly order() {
        //System.out.println("f1="+f);
        Collections.sort(list, new Comparator<func>() {
            @Override
            public int compare(func p1, func p2) {
                //c1*x^n1  c2*x^n2
                //System.out.println("p1="+p1+",p2="+p2);
                return Integer.compare(g(p1), g(p2));

            }
        });
        //System.out.println("f2="+f);
        return this;
    }

}
