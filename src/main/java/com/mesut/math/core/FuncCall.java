package com.mesut.math.core;

import com.mesut.math.Util;

import java.util.List;
import java.util.Set;

public class FuncCall extends func {
    String name;
    List<func> args;

    public FuncCall(String name, List<func> args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public List<func> getArgs() {
        return args;
    }

    public func getArg(int i) {
        return args.get(i);
    }

    @Override
    public func substitute0(variable v, func p) {
        for (int i = 0; i < args.size(); i++) {
            args.set(i, args.get(i).substitute(v, p));
        }
        return this;
    }

    @Override
    public void vars(Set<variable> vars) {
        for (func a : args) {
            a.vars(vars);
        }
    }

    @Override
    public String toString2() {
        return name + "(" + Util.join(args, ", ") + ")";
    }

    @Override
    public boolean eq0(func f) {
        return false;
    }
}
