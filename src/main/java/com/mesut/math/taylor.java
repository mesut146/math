package com.mesut.math;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.funcs.fac;
import com.mesut.math.operator.add;
import com.mesut.math.operator.mul;

//numeric taylor series(faster than symbolic one)
public class taylor extends add {

    public func func;
    public double at;
    public func center;//var-at
    public variable var;

    public taylor(func func, double at) {
        this.func = func;
        this.var = func.vars().get(0);
        this.at = at;
    }

    public taylor() {

    }


    public void calc(int n) {
        center = var.sub(at);

        list.add(makeTerm(func.eval(at), 0, center));

        for (int i = 1; i <= n; i++) {
            func = func.derivative();
            double coeff = func.eval(at) / new fac(i).eval();
            list.add(makeTerm(coeff, i, center));
        }
        simplify();
    }

    public void put(double coeff, int power, func center) {
        list.add(makeTerm(coeff, power, center));
    }

    private func makeTerm(double coeff, int pow, func center) {
        if (coeff == 0) {
            return cons.ZERO;
        }
        if (coeff == 1) {
            return center.pow(pow);
        }
        else {
            return new cons(coeff).mul(center.pow(pow));
        }
    }


    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        center = var.sub(at).toString();

        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);
            if (term.coeff != 0) {
                sb.append(term.coeff);

                if (term.pow != 0) {
                    if (at == 0) {
                        sb.append("*x");
                    }
                    else {
                        sb.append("*(").append(center).append(")");
                    }
                    if (term.pow > 1) {
                        sb.append("^");
                        sb.append(term.pow);
                    }
                }
                if (i < list.size() - 1 && list.get(i + 1).coeff > 0) {
                    sb.append("+");
                }
            }

        }
        return sb.toString();
    }*/

    //mul class
    static class term extends mul {
        double coeff;
        func center;
        int pow;

        public term(double coeff, int pow, func center) {
            this.coeff = coeff;
            this.pow = pow;
            this.center = center;
            this.list.add(new cons(coeff));
            this.list.add(center.pow(pow));
        }

        /*@Override
        public String toString() {
            return toString2();
        }

        @Override
        public String toString2() {
            if (coeff == 0) {
                return "0";
            }
            if (pow == 0) {
                return "1";
            }
            if (coeff == 1) {
                return printPoly();
            }
            return coeff + "*" + printPoly();
        }

        String printPoly() {
            if (pow == 1) {
                return center.toString();
            }
            return center + "^" + pow;
        }*/
    }
}
