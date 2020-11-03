package com.mesut.math.core;

import com.mesut.math.Util;

import java.util.*;

//class for user defined function
public class fx extends variable {
    public static List<fx> ins = new ArrayList<>();//previously created funcs
    String name;
    func value;
    int derivativeLevel = 0;

    public fx() {
        this("f");
    }

    public fx(String name) {
        this(name, variable.x);
    }

    public fx(String name, func... args) {
        super("var_fx_" + name);
        this.name = name;
        Collections.addAll(super.f, args);
        ins.add(this);
    }

    public fx(String name, List<func> args) {
        super("var_fx_" + name);
        this.name = name;
        f.addAll(args);
        ins.add(this);
    }

    public func getValue() {
        return value;
    }

    public List<func> args() {
        return f;
    }

    public func arg() {
        return args().get(0);
    }

    public static boolean has(String name) {
        for (fx f : ins) {
            if (f.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static fx getFx(String name) {
        for (fx f : ins) {
            if (f.name.equals(name)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        for (ListIterator<func> it = args().listIterator(); it.hasNext(); ) {
            it.set(it.next().get(vars, vals));
        }
        return this;
    }

    @Override
    public double eval(variable[] vars, double[] vals) {
        if (value == null) {
            //illegal
            throw new RuntimeException("cant evaluate undefined function " + this);
        }
        return value.eval(vars, vals);
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        if (value == null) {
            //illegal
            throw new RuntimeException("cant evaluate undefined function " + this);
        }
        return value.evalc(vars, vals);
    }

    @Override
    public func derivative(variable v) {
        if (vars().contains(v)) {
            return new derivative(this, v);
        }
        return cons.ZERO;
    }

    @Override
    public func integrate(variable v) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0() {
        return new fx(name, args());
    }

    @Override
    public String toString2() {
        return String.format("%s(%s)", name, Util.join(args(), ","));
    }

    @Override
    public String toLatex() {
        return toString();
    }

    @Override
    public void vars0(Set<variable> vars) {
        for (func arg : args()) {
            arg.vars0(vars);
        }
    }

    @Override
    public boolean eq0(func f) {
        return name.equals(((fx) f).name) && a.eq(f.a);
    }

    @Override
    public func substitute0(variable v, func p) {
        a = a.substitute(v, p);
        return this;
    }

    @Override
    public func simplify() {
        return this;
    }


}
