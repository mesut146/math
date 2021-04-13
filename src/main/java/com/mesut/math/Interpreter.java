package com.mesut.math;

import com.mesut.math.core.Equation;
import com.mesut.math.core.FuncCall;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.operator.add;
import com.mesut.math.operator.mul;
import com.mesut.math.parser.MathParser;
import com.mesut.math.parser.ParseException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Interpreter {

    List<Equation> equations = new ArrayList<>();


    public func getValue(variable v) {
        for (Equation e : equations) {
            if (e.getLeft().eq(v)) {
                return e.getRight();
            }
        }
        return null;
    }

    public func normalize(func f) {
        if (f instanceof Equation) {
            Equation e = (Equation) f;
            e.setRight(normalize(e.getRight()));
            return e;
        }
        f = normalizeVars(f);
        f = normalizeCalls(f);
        return f;
    }

    func normalizeCalls(func f) {
        return new Visitor() {
            @Override
            public func visit(FuncCall call) {
                if (call.getName().equals("derivative")) {
                    if (call.getArgs().isEmpty()) {
                        return call.getScope().derivative();
                    }
                    else {
                        return call.getScope().derivative((variable) call.getArgs().get(0));
                    }
                }
                else if (call.getName().equals("simplify")) {
                    return call.getScope().simplify();
                }
                variable v = variable.from(call.getName());
                func val = checkVal(v);
                if (call.getArgs().isEmpty()) {
                    return val;
                }
                else {
                    if (call.getArgs().size() == 1) {
                        func arg = call.getArgs().get(0);
                        return val.substitute(val.vars().get(0), arg);
                    }
                    else {
                        for (func arg : call.getArgs()) {
                            Equation e = (Equation) arg;
                            val = val.substitute(e.leftAsVar(), e.getRight());
                        }
                        return val;
                    }
                }
            }
        }.visit(f);
    }

    func normalizeVars(func f) {
        for (variable v : f.vars()) {
            //if v is defined just replace
            func val = getValue(v);
            if (val != null) {
                f = f.substitute(v, getValue(v));
            }
        }
        return f;
    }

    void add(Equation e) {
        for (Equation ee : equations) {
            if (ee.getLeft().eq(e.getLeft())) {
                //already defined
                ee.setRight(e.getRight());
                return;
            }
        }
        equations.add(e);
    }

    func checkVal(variable v) {
        func val = getValue(v);
        if (val == null) {
            throw new RuntimeException(v + " is not defined");
        }
        return val;
    }

    public void execute(String line) throws ParseException {
        MathParser parser = new MathParser(new StringReader(line));
        func f = parser.equation();

        if (f.isVariable()) {
            func val = checkVal((variable) f);
            print(val);
        }
        else {
            f = normalize(f);
            if (f instanceof Equation) {
                Equation equation = (Equation) f;
                add(equation);
            }
            else {
                print(f);
            }
        }
    }

    void print(Object f) {
        if (f == null) {
            throw new RuntimeException("can't print a null value");
        }
        if (f instanceof func) {
            func ff = (func) f;
            if (ff.vars().isEmpty()) {
                System.out.println(ff.eval());
            }
            else {
                System.out.println(f);
            }
        }
        else {
            System.out.println(f);
        }
    }

    public void clear() {
        equations.clear();
    }

    static class Visitor {
        public func visit(func f) {
            if (f.isAdd()) {
                return visit(((add) f));
            }
            else if (f.isMul()) {
                return visit(((mul) f));
            }
            else if (f instanceof FuncCall) {
                return visit(((FuncCall) f));
            }
            else if (f instanceof Equation) {
                return visit((Equation) f);
            }
            return f;
        }

        public func visit(add f) {
            for (ListIterator<func> it = f.f.listIterator(); it.hasNext(); ) {
                func term = it.next();
                it.set(visit(term));
            }
            return f;
        }

        public func visit(mul f) {
            for (ListIterator<func> it = f.f.listIterator(); it.hasNext(); ) {
                func term = it.next();
                it.set(visit(term));
            }
            return f;
        }

        public func visit(FuncCall call) {
            return call;
        }

        public func visit(Equation equation) {
            equation.setLeft(visit(equation.getLeft()));
            equation.setRight(visit(equation.getRight()));
            return equation;
        }
    }

}
