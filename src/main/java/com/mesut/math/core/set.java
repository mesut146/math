package com.mesut.math.core;

import com.mesut.math.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//a finite set with elements of type cons
public class set extends func {

    public static boolean print = true;
    public String name = "c";
    protected List<func> elements = new ArrayList<>();

    public int size() {
        return elements.size();
    }

    public void put(func term) {
        elements.add(term);
    }

    public List<func> getElements() {
        return elements;
    }

    //sum the elements
    public double sum() {
        double sum = 0;
        for (func term : elements) {
            sum += term.eval();
        }
        return sum;
    }

    @Override
    public void vars(Set<variable> vars) {
        for (func term : elements) {
            term.vars(vars);
        }
    }

    @Override
    public func add(double val) {
        set res = new set();
        for (func term : elements) {
            res.elements.add(term.add(val));
        }
        return res;
    }

    void checkType(func other) {
        if (!(other instanceof set)) {
            throw new RuntimeException("not set " + other);
        }
    }

    void checkSize(set otherSet) {
        if (size() != otherSet.size()) {
            throw new RuntimeException("sizes must be equal: " + size() + "," + otherSet.size());
        }
    }

    @Override
    public func add(func other) {
        checkType(other);
        set otherSet = (set) other;
        checkSize(otherSet);

        set res = new set();
        for (int i = 0; i < size(); i++) {
            res.put(elements.get(i).add(otherSet.elements.get(i)));
        }
        return res;
    }

    @Override
    public func sub(double d) {
        set res = new set();
        for (func term : elements) {
            res.elements.add(term.sub(d));
        }
        return res;
    }

    @Override
    public func sub(func other) {
        checkType(other);
        set otherSet = (set) other;
        checkSize(otherSet);
        set res = new set();
        for (int i = 0; i < size(); i++) {
            res.put(elements.get(i).sub(otherSet.elements.get(i)));
        }
        return res;
    }

    @Override
    public func mul(double val) {
        set res = new set();
        for (func c : elements) {
            res.elements.add(c.mul(val));
        }
        return res;
    }

    //cross product
    @Override
    public func mul(func other) {
        checkType(other);
        set other_set = (set) other;
        set res = new set();

        for (func term1 : elements) {
            for (func c2 : other_set.elements) {
                res.elements.add(term1.mul(c2));
            }
        }
        return res;
    }

    @Override
    public func pow(double val) {
        set res = new set();
        for (func c : elements) {
            res.elements.add(c.pow(val));
        }
        return res;
    }

    public func getTerm(int index) {
        return elements.get(index);
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
        res.elements.clear();

        for (func term : elements) {
            res.elements.add(term.derivative(v));
        }
        return res;
    }

    @Override
    public func integrate(variable v) {
        set res = (set) copy();
        res.elements.clear();

        for (func term : elements) {
            res.elements.add(term.integrate(v));
        }
        return res;
    }

    @Override
    public func copy0() {
        set res = new set();
        res.name = name;
        res.a = a;
        res.elements.addAll(elements);
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
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public String print(int s1, int s2) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("{");

        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean eq0(func other) {
        return Util.isEq(elements, ((set) other).elements);
    }

    @Override
    public func substitute0(variable v, func p) {
        for (int i = 0; i < elements.size(); i++) {
            elements.set(i, elements.get(i).substitute(v, p));
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
