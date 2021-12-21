package com.mesut.math;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.funcs.fac;

//symbolic taylor series
public class taylorsym extends taylor {

    public func at;

    public taylorsym(func func, Object var, Object at) {
        this.func = func;
        this.var = Util.var(var);
        this.at = Util.cast(at);
    }

    public taylorsym(func func, Object at) {
        this(func, func.vars().get(0), at);
    }

    public taylorsym() {
        super();
    }

    @Override
    public void calc(int n) {
        calc(n, false);
    }

    public void calc(int n, boolean fac) {
        func center = var.sub(at);

        list.add(makeTerm(func.substitute(var, at).simplify(), 0, center));

        for (int i = 1; i <= n; i++) {
            func = func.derivative();
            func coeff;
            if (!fac) {
                coeff = func.substitute(var, at).simplify().div(new fac(i).eval());
            }
            else {
                coeff = func.substitute(var, at).simplify().div(new fac(i));
            }
            list.add(makeTerm(coeff, i, center));
        }
        simplify();
    }

    private func makeTerm(func coeff, int pow, func center) {
        if (coeff.is(0)) {
            return cons.ZERO;
        }
        if (coeff.is(1)) {
            return center.pow(pow);
        }
        else {
            return new cons(coeff).mul(center.pow(pow));
        }
    }

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

    /*@Override
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

        }
        return sb.toString();
    }*/

    /*public func conv() {
        func res = cons.ZERO;
        for (term t : list) {
            res = res.add(t.fc.mul(v.pow(t.pow)));
        }
        return res;
    }*/


}
