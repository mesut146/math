package com.mesut.math.operator;

import com.mesut.math.Util;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class add extends func {

    public add() {
        //type = types.add;
    }

    public add(func... args) {
        //type = types.add;
        /*for(func term:args){
            f.add(term);
        }*/
        Collections.addAll(list, args);
    }

    public add(List<func> args) {
        //type = types.add;
        list = args;
    }

    public static holder e3(func f1, func f2) {
        if (f1.eq(f2)) {
            return new holder(f1, 1, null, true);
        }

        if (!f1.isMul()) {
            f1 = new mul(f1, cons.ONE);
        }
        if (!f2.isMul()) {
            f2 = new mul(f2, cons.ONE);
        }
        holder o1 = rm((mul) f1);
        holder o2 = rm((mul) f2);

        if (Util.isEq(o1.l, o2.l)) {
            return new holder(new mul(o1.l), o1.d + o2.d - 1, null, true);
        }

        return new holder(null, 0, null, false);
    }

    public static holder rm(mul t) {
        double d = 1;
        List<func> l = getFree();
        for (func p : t.list) {
            if (p.isCons0()) {
                d *= p.eval();
            }
            else {
                l.add(p);
            }
        }
        d *= t.sign;
        return new holder(null, d, l, false);
    }

    @Override
    public boolean isAdd() {
        return true;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        func res = cons.ZERO;
        for (func term : list) {
            res = res.add(term.get(vars, vals));
        }
        return signOther(res);
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        double sum = 0;
        for (func f1 : list) {
            sum += f1.eval(v, vals);
        }
        return sign * sum;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        cons sum = cons.ZERO;
        for (func term : list) {
            sum = (cons) sum.add(term.evalc(vars, vals));
        }
        return sc(sum);
    }

    @Override
    public func derivative(variable v) {
        func res = cons.ZERO;
        //add res = new add(cons.ZERO);
        for (func term : list) {
            res = res.add(term.derivative(v));
            //res.f.add(term.derivative(v));
        }
        return signOther(res);
    }

    @Override
    public func integrate(variable v) {
        func res = cons.ZERO;
        for (func term : list) {
            res = res.add(term.integrate(v));
        }
        return signOther(res);
    }

    @Override
    public String toString2() {
        return toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            func term = list.get(i);
            if (i == 0 && sign * term.sign == -1) {
                sb.append("-");
            }
            sb.append(term.toString2());
            if (i < list.size() - 1) {
                int s = list.get(i + 1).sign * sign;
                if (s == 1) {
                    sb.append("+");
                }
                else if (s == -1) {
                    sb.append("-");
                }
            }
        }
        return sb.toString();
    }

    public func simplify() {
        List<func> list = getFree();
        //System.out.println(f);
        for (func term : this.list) {
            term = term.simplify();
            if (term.isAdd()) {
                //merge two adds
                for (func inner : term.list) {
                    list.add(term.signf(inner));
                }
            }
            //if a element is inf whole add becomes inf
            else if (term.isConstant() && term.asCons().isInf()) {
                return term;
            }
            else {
                list.add(term);
            }
        }
        set(list);
        cons0();
        if (this.list.size() == 0) {
            return cons.ZERO;
        }
        else if (this.list.size() == 1) {
            return signf(this.list.get(0));
        }
        //System.out.println("f="+f);
        mu();
        //System.out.println("fmu="+f);
        if (this.list.size() == 1) {
            return signf(this.list.get(0));
        }
        return this;
    }

    public void cons0() {
        double sum = 0;
        List<func> list = getFree();
        int idx = 0;
        for (int i = 0; i < this.list.size(); i++) {
            func term = this.list.get(i);
            if (term.isCons0()) {
                sum += term.eval();
                idx = i;
            }
            else {
                list.add(term);
            }
        }
        if (sum != 0) {
            //put sum where last cons seen
            idx = idx > list.size() ? 0 : idx;
            list.add(idx, new cons(sum));
        }
        set(list);
    }

    //2x+3x=5x
    //2i+3i=5i
    public void mu() {
        List<func> l = getFree();
        boolean b[] = new boolean[list.size()];

        for (int i = 0; i < list.size(); i++) {

            if (!b[i]) {
                func v = list.get(i);
                double k = 1;
                for (int j = i + 1; j < list.size(); j++) {
                    holder q = e3(v, list.get(j));
                    if (q.b) {
                        b[j] = true;
                        k += q.d;
                        v = q.f;
                    }
                }
                func n = v.mul(k);

                //System.out.println("v="+v+" ,k="+k+" ,n="+n+" ,"+n.getType());
                //System.out.printf("v=%s k=%s n=%s\n",v,k,v.mul(k));
                l.add(n);
            }
        }
        set(l);
    }

    @Override
    public boolean eq0(func f1) {
        return Util.isEq(list, f1.list);
    }

    @Override
    public func substitute0(variable v, func p) {
        List<func> arr = getFree();
        for (func term : list) {
            arr.add(term.substitute0(v, p));
        }
        return new add(arr).simplify();
    }

    @Override
    public func copy0() {
        return new add(list);
    }

    @Override
    public String toLatex() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toLatex());
            if (i < list.size() - 1) {
                if (list.get(i + 1).sign != -1) {
                    sb.append("+");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public func getReal() {
        add res = new add();
        res.sign = sign;
        for (func t : list) {
            res.list.add(t.getReal());
        }
        return res.simplify();
    }

    @Override
    public func getImaginary() {
        add res = new add();
        res.sign = sign;
        for (func t : list) {
            res.list.add(t.getImaginary());
        }
        return res.simplify();
    }


    @Override
    public void vars(Set<variable> vars) {
        for (func term : list) {
            term.vars(vars);
        }
    }
}
