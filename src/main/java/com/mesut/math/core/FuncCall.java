package com.mesut.math.core;

import com.mesut.math.Util;

import java.util.List;
import java.util.Set;

public class FuncCall extends func {
    func scope;
    String name;
    List<func> args;

    public FuncCall(func scope, String name, List<func> args) {
        this.scope = scope;
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public List<func> getArgs() {
        return args;
    }

    public func call(func f) {
        if (name.equals("derivative")) {
            if (args.isEmpty()) {
                return scope.derivative();
            }
            else {
                return scope.derivative((variable) args.get(0));
            }
        }
        return this;
    }

    @Override
    public func substitute0(variable v, func p) {
        if (scope != null) scope = scope.substitute(v, p);
        //args
        return this;
    }

    @Override
    public void vars(Set<variable> vars) {
        if (scope != null) scope.vars(vars);
        for (func a : args) {
            a.vars(vars);
        }
    }

    @Override
    public String toString2() {
        if (scope == null) {
            return name + "(" + Util.join(args, ", ") + ")";
        }
        return scope.top() + "." + name + "(" + Util.join(args, ", ") + ")";
    }
}
