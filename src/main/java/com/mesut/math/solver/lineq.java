package com.mesut.math.solver;

import com.mesut.math.Util;
import com.mesut.math.core.func;
import com.mesut.math.core.var;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lineq {
    func c;
    int a, b;
    var x = var.x, y = var.y;
    linl xs, ys;


    public lineq(int a, int b, Object c) {
        this.a = a;
        this.b = b;
        this.c = Util.cast(c);
    }

    //ax+by=k
    public lineq solve() {
        linl l = new linl();
        int aa = Math.abs(a);
        int bb = Math.abs(b);
        if (aa == 1) {
            if (a > 0) {
                xs = new linl();
                xs.v = x;
                xs.l = new lin();
            }
        }
        if (aa > bb) {

            int d = aa / bb;
            int m = aa % bb;
            lineq ln = new lineq(m, d, c);
            ln.y = next(y);
            System.out.println(ln);
            ln.solve();

        }
        return this;
    }

    var next(var v) {
        String s = v.toString();
        Pattern p = Pattern.compile("(\\w+)(\\d*)");
        Matcher m = p.matcher(s);
        System.out.println(m.groupCount());
        if (m.groupCount() == 2) {

        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (a != 1) {
            s.append(a);
        }
        s.append(x);
        if (b > 0) {
            s.append("+");
        }
        s.append(b);
        s.append(y);
        s.append("=");
        s.append(c);
        return s.toString();
    }


}
