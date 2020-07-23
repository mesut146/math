package com.mesut.math.diff;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

//y'+y*p=q
public class FirstOrder {
    public func p;
    public func q;

    public FirstOrder(func p, func q) {
        this.p = p;
        this.q = q;
    }

    public void solve() {
        //y'+y*p=q
        double h = 0.00001;
        func ah = p.mul(h);
        func ah2 = p.substitude(variable.x, variable.x.add(h)).mul(h);
        func bh = q.substitude(variable.x, variable.x.add(h));
        System.out.println(bh);
        func rhs = bh.div(ah2).sub(q.div(ah));
        func lhs = cons.ONE.div(ah2).sub(cons.ONE.div(ah)).add(1);
        System.out.println(rhs);
        System.out.println(lhs);
        func yp = rhs.div(lhs);
        System.out.println(yp);
        System.out.println(yp.eval("x", 1));
    }
}
