package com.mesut.math.operator;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.var;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class add extends func {

    public add() {
        type = types.add;
    }

    public add(func... args) {
        type = types.add;
        //since Arrays.aslist returns abstract list
        f = new ArrayList<>(Arrays.asList(args));
    }

    public add(List<func> args) {
        type = types.add;
        f = args;
    }

    @Override
    public func get0(var[] v, cons[] c) {
        func res = cons.ZERO;
        for (func term : f) {
            res = res.add(term.get(v, c));
        }
        return sign(res);
    }

    @Override
    public double eval(var[] v, double[] d) {
        double sum = 0;
        for (func f1 : f) {
            sum += f1.eval(v, d);
        }
        return sign * sum;
    }

    @Override
    public cons evalc(var[] v, double[] d) {
        cons sum = cons.ZERO;
        for (func term : f) {
            sum = (cons) sum.add(term.evalc(v, d));
        }
        return sc(sum);
    }

    @Override
    public func derivative(var v) {
        func res = cons.ZERO;
        for (func term : f) {
            res = res.add(term.derivative(v));
        }
        return sign(res);
    }

    @Override
    public func integrate(var v) {
        func res = cons.ZERO;
        for (func term : f) {
            res = res.add(term.integrate(v));
        }
        return sign(res);
    }

    @Override
    public String toString2() {
        return toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);
            if (i == 0 && sign * term.sign == -1) {
                sb.append("-");
            }
            sb.append(term.toString2());
            if (i < f.size() - 1) {
                int s = f.get(i + 1).sign * sign;
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
        for (func term : f) {
            if (term.isAdd()) {
                //merge two adds
                for (func inner : term.f) {
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
        if (f.size() == 0) {
            return cons.ZERO;
        }
        else if (f.size() == 1) {
            return signf(f.get(0));
        }
        //System.out.println("f="+f);
        mu();
        //System.out.println("fmu="+f);
        if (f.size() == 1) {
            return signf(f.get(0));
        }
        //System.out.println("f2="+f);

        //System.out.println("f3="+this);
        return this;
    }

    public void cons0() {
        double c = 0;
        List<func> l = getFree();
        //System.out.println("fa="+f);
        int idx = 0;
        for (int i = 0; i < f.size(); i++) {
            func p = f.get(i);
            if (p.isCons0()) {
                c += p.eval();
                idx = i;
            }
            else {
                l.add(p);
            }
        }
        if (c != 0) {
            idx = idx >= l.size() ? 0 : idx;
            l.add(idx, new cons(c));
        }
        set(l);
    }

    //2x+3x=5x
    //2i+3i=5i
    public void mu() {
        List<func> l = getFree();
        boolean b[] = new boolean[f.size()];

        for (int i = 0; i < f.size(); i++) {

            if (!b[i]) {
                func v = f.get(i);
                double k = 1;
                for (int j = i + 1; j < f.size(); j++) {
                    holder q = e3(v, f.get(j));
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
    public boolean eq2(func f1) {
        return isEq(f, f1.f);
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

        if (isEq(o1.l, o2.l)) {
            return new holder(new mul(o1.l), o1.d + o2.d - 1, null, true);
        }

        return new holder(null, 0, null, false);
    }

    public static holder rm(mul t) {
        double d = 1;
        List<func> l = getFree();
        for (func p : t.f) {
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
    public func substitude0(var v, func p) {
        List<func> arr = getFree();
        for (func term : f) {
            arr.add(term.substitude0(v, p));
        }
        return new add(arr).simplify();
    }

    @Override
    public func copy0() {
        return new add(f);
    }

    @Override
    public String toLatex() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.size(); i++) {
            sb.append(f.get(i).toLatex());
            if (i < f.size() - 1) {
                if (f.get(i + 1).sign == -1) {
                    sb.append("-");
                }
                else {
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
        for (func t : f) {
            res.f.add(t.getReal());
        }
        return res.simplify();
    }

    @Override
    public func getImaginary() {
        add res = new add();
        res.sign = sign;
        for (func t : f) {
            res.f.add(t.getImaginary());
        }
        return res.simplify();
    }


    @Override
    public void vars0(Set<var> vars) {
        for (func term : f) {
            term.vars0(vars);
        }
    }
}
