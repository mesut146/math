package com.mesut.math;

import com.mesut.math.core.func;
import com.mesut.math.core.var;
import com.mesut.math.funcs.fac;

import java.util.ArrayList;
import java.util.List;

//symbolic taylor series
public class taylorsym extends taylor {
    List<term> list = new ArrayList<>();

    public taylorsym(func func, double at) {
        super(func, at);
    }

    public taylorsym() {
        super();
    }
    //var v;

    /*public static taylorsym symbol(Object fo, Object vo, func at, int n) {
        func f = Util.cast(fo);
        var v = Util.var(vo);
        taylorsym t = new taylorsym(f,at);
        //t.v = v;
        for (int i = 0; i <= n; i++) {
            func c = f.substitude(v, at).simplify().div(new fac(i).eval());
            //t.put(c, i);
            f = f.derivative(v);
        }
        return t;
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        /*for (int i = 0; i < list.size(); i++) {
            term t = list.get(i);

            if (!t.fc.is(0)) {
                sb.append(t.fc);

                if (t.pow != 0) {
                    sb.append("*x");
                    if (t.pow > 1) {
                        sb.append("^");
                        sb.append(t.pow);
                    }
                }
                if (i < list.size() - 1 && list.get(i + 1).fc.sign == 1) {
                    sb.append("+");
                }
            }

        }*/
        return sb.toString();
    }

    /*public func conv() {
        func res = cons.ZERO;
        for (term t : list) {
            res = res.add(t.fc.mul(v.pow(t.pow)));
        }
        return res;
    }*/

    public double coeff(func f) {
        return 0;
    }


}
