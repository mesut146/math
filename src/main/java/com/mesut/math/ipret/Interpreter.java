package com.mesut.math.ipret;

import com.mesut.math.core.Equation;
import com.mesut.math.core.FuncCall;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    List<Equation> equations = new ArrayList<>();

    public Equation findEq(String name) {
        for (Equation eq : equations) {
            if (eq.getLeft().isVariable()) {
                if (eq.getLeft().asVar().getName().equals(name)) {
                    return eq;
                }
            }
            else {
                FuncCall call = eq.getLeft().asCall();
                if (call.getName().equals(name)) {
                    return eq;
                }
            }
        }
        return null;
    }

    public func getRhs(String name) {
        Equation eq = findEq(name);
        return eq == null ? null : eq.getRight();
    }

    public func checkRhs(String name) {
        Equation eq = findEq(name);
        if (eq == null) {
            throw new RuntimeException(name + " is not defined");
        }
        return eq.getRight();
    }

    void add(Equation e) {
        Equation prev;
        if (e.getLeft().isVariable()) {
            prev = findEq(e.getLeft().asVar().getName());
        }
        else {
            prev = findEq(e.getLeft().asCall().getName());
        }
        if (prev != null) {
            //already defined so just replace
            prev.setLeft(e.getLeft());//in case of signature change
            prev.setRight(e.getRight());
        }
        else {
            //define
            equations.add(e);
        }
    }

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

    func normalizeVars(func f) {
        for (variable v : f.vars()) {
            //if v is defined just replace
            func val = getRhs(v.getName());
            if (val != null) {
                f = f.substitute(v, getRhs(v.getName()));
            }
        }
        return f;
    }

    public Output exec(String line) throws IOException {
        return execute(line);
    }

    public Output execute(String line) throws IOException {
        func f = func.parse(line);

        if (f.isVariable()) {
            //print var or cons
            func val = checkRhs(f.asVar().getName());
            //val = normalize(val);
            return print(val);
        }
        else if (f.isEq()) {
            Equation eq = (Equation) f;
            if (eq.getLeft().isVariable()) {
                //rhs must be cons
                if (!eq.getRight().vars().isEmpty()) {
                    throw new RuntimeException("rhs is not constant");
                }
            }
            else {
                //rhs vars must match lhs but lateinit
            }
            eq.setRight(normalize(eq.getRight()));
            add(eq);
            return null;
        }
        else {
            //normal expr
            f = normalize(f);
            return print(f);
        }
    }

    Output print(func f) {
        if (f == null) {
            throw new RuntimeException("can't print a null value");
        }
        return new Output(f);
        /*if (f instanceof factor) {
            return new Output(f);
        }
        else if (f instanceof set) {
            return new Output(f);
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
        }*/
    }

    public void clear() {
        equations.clear();
    }

}
