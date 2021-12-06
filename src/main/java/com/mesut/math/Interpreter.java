package com.mesut.math;

import com.mesut.math.core.*;
import com.mesut.math.operator.add;
import com.mesut.math.operator.mul;
import com.mesut.math.prime.factor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Interpreter {

    List<Equation> equations = new ArrayList<>();

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
                switch (call.getName()) {
                    case "derivative":
                    case "der":
                        if (call.getArgs().isEmpty()) {
                            throw new RuntimeException("missing args");
                        }
                        if (call.getArgs().size() == 1) {
                            return call.getArg(0).derivative();
                        }
                        else {
                            func res = call.getArg(0);
                            for (int i = 1; i < call.getArgs().size(); i++) {
                                res = res.derivative((variable) call.getArg(i));
                            }
                            return res;
                        }
                    case "simplify":
                        return call.getArg(0).simplify();
                    case "int":
                    case "integral":
                    case "integrate":
                        //first arg is func
                        //second arg is var
                        //last two are interval(optional)
                        Integral integral = new Integral(call.getArg(0), call.getArg(1));
                        if (call.getArgs().size() > 2) {
                            integral.lower = call.getArg(2);
                            integral.upper = call.getArg(3);
                        }
                        return integral;
                    case "factor":
                    case "factorize":
                    case "factorise": {
                        if (call.getArg(0) instanceof set) {
                            return factor.factorize(((set) call.getArg(0)));
                        }
                        else {
                            int x = (int) call.getArg(0).asCons().val;
                            return factor.factorize(x);
                        }
                    }
                    case "plot":
                    case "graph": {
                        //todo
                        throw new RuntimeException("not implemented yet");
                    }
                    case "im":
                    case "imaginary": {
                        return call.getArg(0).getImaginary();
                    }
                    case "real":
                    case "re": {
                        return call.getArg(0).getReal();
                    }
                }
                //todo more
                //custom func
                func rhs = checkVal(call.getName());
                if (call.getArgs().isEmpty()) {
                    return rhs;
                }
                else {
                    if (call.getArgs().isEmpty()) {
                        //eval
                        //return call.eval();
                    }
                    if (call.getArgs().size() == 1) {
                        func arg = call.getArg(0);
                        return rhs.substitute(rhs.vars().get(0), arg);
                    }
                    else {
                        //named args
                        //f(x=1,y=2)
                        //todo predefined order preserved
                        //f(x,y) = x+y
                        //f(1,2) = f(x=1,y=2)
                        for (func arg : call.getArgs()) {
                            if (!(arg instanceof Equation)) {
                                throw new RuntimeException("named arg is expected");
                            }
                            Equation e = (Equation) arg;
                            rhs = rhs.substitute(e.leftAsVar(), e.getRight());
                        }
                        return rhs;
                    }
                }
            }
        }.visit(f);
    }

    private func getValue(String name) {
        for (Equation e : equations) {
            if (e.getLeft().isVariable()) {
                variable v = e.getLeft().asVar();
                if (v.getName().equals(name)) {
                    return e.getRight();
                }
            }
            else {
                FuncCall call = ((FuncCall) e.getLeft());
                if (call.getName().equals(name)) {
                    return e.getRight();
                }
            }
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

    func checkVal(String v) {
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
        else {
            f = normalize(f);
            if (f instanceof Equation) {
                Equation equation = (Equation) f;
                add(equation);
                return null;
            }
            else {
                return print(f);
            }
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

    public static class Output {
        public String text;
        List<Point> points = new ArrayList<>();
        //or graph data


        public Output(String text) {
            this.text = text;
        }
    }

    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
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
