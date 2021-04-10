package com.mesut.math.operator;

import com.mesut.math.Config;
import com.mesut.math.Util;
import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class mul extends func {

    static int idc = 0;
    int id;

    public mul(func... args) {
        Collections.addAll(f, args);
        id = idc++;
    }

    public mul(List<func> args) {
        f.addAll(args);
        id = idc++;
    }

    @Override
    public boolean isMul() {
        return true;
    }

    @Override
    public void vars(Set<variable> vars) {
        for (func term : f) {
            term.vars(vars);
        }
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        mul res = new mul();
        for (func term : f) {
            res.f.add(term.get(vars, vals));
        }
        return signOther(res.simplify());
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        double res = 1;
        for (func term : f) {
            res *= term.eval(v, vals);
        }
        return sign * res;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        func m = cons.ONE;
        for (func term : f) {
            m = m.mul(term.evalc(vars, vals));
        }
        return sc(m);
    }

    @Override
    public func derivative(variable v) {
        //a*b ==> a'*b+a*b'
        func p = left();
        func q = right();
        func o = p.derivative(v).mul(q).add(p.mul(q.derivative(v)));
        return signOther(o);
    }

    @Override
    public func integrate(variable v) {
        return new Integral(this, v);
    }

    public func byParts() {
        func a = f.get(0);
        func b = f.get(1);
        func g = b.integrate();
        func h = a.derivative().mul(g);
        //System.out.printf("a=%s,b=%s,g=%s,h=%s%n", a, b, g, h);
        return a.mul(g).sub(h.integrate());
    }

    @Override
    public func getReal() {
        func p = left();
        func q = right();
        func ac = p.getReal().mul(q.getReal());
        func bd = p.getImaginary().mul(q.getImaginary());
        return signOther(ac.sub(bd));
    }

    @Override
    public func getImaginary() {
        func p = left();//a+bi
        func q = right();//c+di
        func ad = p.getReal().mul(q.getImaginary());
        func bc = p.getImaginary().mul(q.getReal());//

        return signOther(ad.add(bc));
    }

    func left() {
        return f.get(0);
    }

    func right() {
        return f.size() == 2 ? f.get(1) : new mul(f.subList(1, f.size()));
    }

    @Override
    public String toString2() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);

            if (term.isAdd()) {
                sb.append(term.top());
            }
            else if (term.isPow()) {
                sb.append(term.top());
            }
            else {
                sb.append(term);
            }
            if (i < f.size() - 1) {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    @Override
    public String toLatex() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < f.size(); i++) {
            func term = f.get(i);

            if (term.isAdd()) {
                sb.append("(");
                sb.append(term.toLatex());
                sb.append(")");
            }
            else {
                sb.append(term.toLatex());
            }

            if (i < f.size() - 1) {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    public func simplify() {
        if (!Config.mul.simplify) {
            return this;
        }
        List<func> list = getFree();
        for (func term : f) {
            term = term.simplify();
            if (term.is(0)) {
                return cons.ZERO;
            }
            if (term.isMul()) {//merge
                list.addAll(term.f);
                sign *= term.sign;
                term.sign = 1;
            }
            else if (term.is(1) || term.is(-1)) {
                sign *= term.eval();
            }
            else {
                list.add(term);
            }
        }
        set(list);
        //System.out.println("after f="+f+" s="+sign);
        if (f.size() == 0) {
            return signf(cons.ONE);
        }
        if (f.size() == 1) {
            return signf(f.get(0));
        }
        //log("before mu");
        mu();
        //log("after mu");
        if (f.size() == 1) {
            return signf(f.get(0));
        }
        cons0();
        //log("after cons");
        if (f.size() == 1) {
            return signf(f.get(0));
        }
        return this;
    }

    //multiply cons's
    public void cons0() {
        double c = 1;
        cons bc = cons.ONE;
        List<func> l = getFree();
        //System.out.println("f="+f);
        //System.out.println("cl="+pr(f));
        for (int i = 0; i < f.size(); i++) {
            func p = f.get(i);
            //System.out.println("p="+p);
            if (p.isCons0()) {
                if (Config.useBigDecimal) {
                    bc = (cons) bc.mul(p.evalc());
                }
                c *= p.eval();
                //System.out.println("c="+c);
            }
            else {
                l.add(p);
            }
        }

        if (Config.useBigDecimal) {
            if (bc.sign == -1) {
                sign = -sign;
                bc = (cons) bc.negate();
            }
            if (!bc.is(1)) {
                l.add(bc);
            }
        }
        if (c < 0) {
            sign = -sign;
            c = -c;
        }
        if (c != 1) {
            l.add(new cons(c));
        }
        set(l);
    }

    //  x^a*x^b=x^(a+b)
    //  a^x*b^x=(a*b)^x
    public void mu() {
        List<func> list = getFree();
        boolean[] b = new boolean[f.size()];
        boolean flag = false;
        //System.out.println("id="+id+" in mu");
        for (int i = 0; i < f.size() - 1; i++) {
            if (b[i]) {
                continue;
            }
            func term = f.get(i);
            //System.out.println("v="+v+" org="+f.get(i));
            func base = term;
            func power = cons.ONE;
            //int vsig=v.sign;
            if (term.isPow()) {
                power = term.b;
                base = term.a;
            }
            //System.out.println("v="+base+" p="+power);
            for (int j = i + 1; j < f.size(); j++) {
                if (b[j]) {
                    continue;
                }
                holder h = e3(base, f.get(j));
                //System.out.println("h="+h.f);

                if (h.b) {
                    if (!flag) {
                        flag = true;
                    }
                    //System.out.println("h="+f.get(j)+" s="+h.sign);
                    b[j] = true;
                    power = power.add(h.f);
                    //vsig *= h.sign;
                }
            }
            //func r=base.pow(power);
            //System.out.println("id="+id+" base="+base+" pow="+power+" all="+r);

            //l.add(base.pow(power).sign(vsig));
            if (flag) {
                list.add(base.pow(power));
            }
            else {
                list.add(term);
            }
        }
        if (!b[f.size() - 1]) {
            list.add(f.get(f.size() - 1));
        }
        //System.out.println("id="+id+" out mu");
        set(list);
    }

    public static holder e3(func f1, func f2) {
        if (f1.eqws(f2)) {
            holder h = new holder(cons.ONE, 0, null, true);
            h.sign = f2.sign;
            return h;
        }

        if (f2.isPow()) {
            func p = f2.b;
            func base = f2.a;
            if (f1.eqws(base)) {
                holder h = new holder(p, 0, null, true);
                h.sign = base.sign;
                return h;
            }
        }
        return new holder(null, 0, null, false);
    }

    @Override
    public boolean eq0(func f1) {
        return Util.isEq(f, f1.f);
    }

    @Override
    public func substitute0(variable v, func p) {
        List<func> l = getFree();
        for (func u : f) {
            l.add(u.substitute(v, p));
        }
        return new mul(l);
    }

    @Override
    public func copy0() {
        return new mul(f);
    }
}
