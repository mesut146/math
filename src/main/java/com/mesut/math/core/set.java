package com.mesut.math.core;

import com.mesut.math.Util;

import java.util.*;

//a finite set with elements of type cons
public class set extends func {

    public static boolean print = true;
    public String name = "c";
    public int start, end;
    //public var v = var.n;//cn variable
    protected List<func> list = new ArrayList<>();

    public set() {

    }

    public set(func... arr) {
        setRange(1, arr.length);
        Collections.addAll(list, arr);
    }

    public set(int... arr) {
        setRange(1, arr.length);
        for (int i : arr) {
            this.list.add(new cons(i));
        }
    }

    public void setRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int len() {
        return end - start + 1;
    }

    public int size() {
        return len();
    }

    public void put(func term) {
        list.add(term);
    }

    public List<func> getList() {
        return list;
    }

    //sum the elements
    public double sum() {
        double sum = 0;
        for (func term : list) {
            sum += term.eval();
        }
        return sum;
    }

    public set sort() {
        Comparator<func> cmp;
        /*cmp = new Comparator<cons>() {
            @Override
            public int compare(cons p1, cons p2) {
                return Double.compare(p1.val, p2.val);
            }
        };*/
        cmp = new Comparator<func>() {
            @Override
            public int compare(func p1, func p2) {
                return 0;
            }

        };
        Collections.sort(list, cmp);
        return this;
    }

    @Override
    public void vars(Set<variable> vars) {
        for (func term : list) {
            term.vars(vars);
        }
    }

    @Override
    public func add(double val) {
        set res = new set();
        for (func term : list) {
            res.list.add(term.add(val));
        }
        return res;
    }

    //linear add {1,2,3}+{4,5}={5,7,3}
    @Override
    public func add(func other) {
        if (other instanceof set) {
            set other_set = (set) other;
            set res = new set();
            //1,2  6,7,8
            //7,9,8
            for (int i = 0; i < Math.max(len(), other_set.len()); i++) {
                if (i < other_set.len()) {
                    res.put((cons) list.get(i).add(other_set.list.get(i)));
                }
                else {
                    res.put(list.get(i));
                }

            }
            if (other_set.len() > len()) {
                for (int i = len(); i < other_set.len(); i++) {
                    res.put(other_set.getTerm(i));
                }
            }
            return res;
        }
        throw new RuntimeException("cannot add set");
    }

    //cross add {1,2,3}+{4,5}={5,6,6,7,7,8}
    public func addx(func other) {
        if (other instanceof set) {
            set other_set = (set) other;
            set res = new set();
            for (func term1 : list) {
                for (func term2 : other_set.list) {
                    res.list.add(term1.add(term2));
                }
            }
            return res;
        }
        throw new RuntimeException("cannot add set");
    }

    @Override
    public func sub(double d) {
        set res = new set();

        for (func term : list) {
            res.list.add(term.sub(d));
        }
        return res;
    }

    @Override
    public func sub(func other) {
        if (other instanceof set) {
            set other_set = (set) other;
            set res = new set();
            for (func term1 : list) {
                for (func term2 : other_set.list) {
                    res.list.add(term1.sub(term2));
                }
            }
            return res;
        }
        throw new RuntimeException("cannot sub set");
    }

    //linear mul with constant
    @Override
    public func mul(double val) {
        set res = new set();

        for (func c : list) {
            res.list.add(c.mul(val));
        }
        return res;
    }

    //linear mul {1,2,3}*{4,5}={4,10,0}
    @Override
    public func mul(func other) {
        if (other instanceof set) {
            set other_set = (set) other;
            set res = new set();

            for (int i = 0; i < Math.min(len(), other_set.len()); i++) {

                if (i < other_set.len()) {
                    res.put((cons) list.get(i).add(other_set.list.get(i)));
                }
                else {
                    res.put(list.get(i));
                }

            }
            if (other_set.len() > len()) {
                for (int i = len(); i < other_set.len(); i++) {
                    res.put(other_set.getTerm(i));
                }
            }
            return res;
        }
        throw new RuntimeException("cannot add set");
    }

    //cross product
    public func mulx(func other) {
        if (other instanceof set) {
            set other_set = (set) other;
            set res = new set();
            for (func term1 : list) {
                for (func c2 : other_set.list) {
                    res.list.add(term1.mul(c2));
                }
            }
            return res;
        }
        throw new RuntimeException("cannot mul set");
    }

    @Override
    public func pow(double val) {
        set res = new set();
        for (func c : list) {
            res.list.add(c.pow(val));
        }
        return res;
    }

    public func getTerm(int index) {
        return list.get(index);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        a = a.get(vars, vals);
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        throw new RuntimeException("can' eval set");
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        throw new RuntimeException("can' eval set");
    }

    @Override
    public String toLatex() {
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func derivative(variable v) {
        set res = (set) copy();
        res.list.clear();

        for (func term : list) {
            res.list.add(term.derivative(v));
        }
        return res;
    }

    @Override
    public func integrate(variable v) {
        set res = (set) copy();
        res.list.clear();

        for (func term : list) {
            res.list.add(term.integrate(v));
        }
        return res;
    }

    @Override
    public func copy0() {
        set res = new set();
        res.name = name;
        res.a = a;
        res.list.addAll(list);
        return res;
    }

    @Override
    public String toString2() {
        if (print) {
            return print();
        }
        return String.format("%s{n}", name);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("{");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public String print(int s1, int s2) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("{");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean eq0(func other) {
        return Util.isEq(list, ((set) other).list);
    }

    @Override
    public func substitute0(variable v, func p) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).substitute(v, p));
        }
        return this;
    }

    @Override
    public func getReal() {
        return this;
    }

    @Override
    public func getImaginary() {
        return this;
    }
}
