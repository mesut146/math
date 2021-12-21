package com.mesut.math.ipret;

import com.mesut.math.core.*;
import com.mesut.math.prime.factor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    List<Equation> equations = new ArrayList<>();

    public func normalize(func f) {
        if (f instanceof Equation) {
            Equation e = (Equation) f;
            e.setRight(normalize(e.getRight()));
            return e;
        }
        f = normalizeVars(f);
        f = new Evaluator(this).visit(f);
        return f;
    }


    private func getValue(String name) {
        Equation e = findEq(name);
        if (e != null) {
            return e.getRight();
        }
        return null;
    }

    func normalizeVars(func f) {
        for (variable v : f.vars()) {
            //if v is defined just replace
            func val = getValue(v.getName());
            if (val != null) {
                f = f.substitute(v, getValue(v.getName()));
            }
        }
        return f;
    }

    Equation findEq(String name) {
        for (Equation e : equations) {
            if (e.getLeft().isVariable()) {
                variable v = e.getLeft().asVar();
                if (v.getName().equals(name)) {
                    return e;
                }
            }
            else {
                FuncCall call = ((FuncCall) e.getLeft());
                if (call.getName().equals(name)) {
                    return e;
                }
            }
        }
        return null;
    }

    void add(Equation e) {
        for (Equation prev : equations) {
            if (prev.getLeft().eq(e.getLeft())) {
                //already defined so just replace
                prev.setRight(e.getRight());
                return;
            }
        }
        equations.add(e);
    }

    public func checkVal(String v) {
        func val = getValue(v);
        if (val == null) {
            throw new RuntimeException(v + " is not defined");
        }
        return val;
    }

    public Output exec(String line) throws IOException {
        return execute(line);
    }

    public Output execute(String line) throws IOException {
        func f = func.parse(line);

        if (f.isVariable()) {
            func val = checkVal(f.asVar().getName());
            return print(val);
        }
        else if (f instanceof Equation) {
            Equation equation = (Equation) normalize(f);
            add(equation);
            return null;
        }
        else {
            f = normalize(f);
            return print(f);
        }
    }

    Output print(func f) {
        if (f == null) {
            throw new RuntimeException("can't print a null value");
        }
        if (f instanceof factor) {
            return new Output(f.toString());
        }
        else if (f instanceof set) {
            return new Output(f.toString());
        }
        else {
            if (f.vars().isEmpty()) {
                double val = f.eval();
                if ((int) val == val) {
                    return new Output("" + (int) val);
                }
                else {
                    return new Output("" + val);
                }
            }
            else {
                return new Output(f.toString());
            }
        }
    }

    public void clear() {
        equations.clear();
    }

}
