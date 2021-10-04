package com.mesut.math;

import com.mesut.math.core.*;
import com.mesut.math.operator.add;
import com.mesut.math.operator.mul;
import com.mesut.math.parser.MathParser;
import com.mesut.math.parser.ParseException;
import com.mesut.math.prime.factor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Interpreter {

    static List<String> commands = Arrays.asList("der", "derivative", "integral", "integrate", "plot", "factor");
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
                    case "pset": {
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
                //already defined
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

    public void execute(String line) throws ParseException {
        int i = line.indexOf(' ');
        if (i != -1) {
            String cmd = line.substring(0, i);
            if (commands.contains(cmd)) {
                String rest = line.substring(i + 1);
                switch (cmd) {
                    case "plot":
                    case "factor":
                    case "der":
                    case "derivative":
                    case "int":
                    case "integral":
                    case "integrate":
                }
            }
        }
        MathParser parser = new MathParser(new StringReader(line));
        func f = parser.line();

        if (f.isVariable()) {
            func val = checkVal(((variable) f).getName());
            System.out.println(val.eval());
            //print(val);
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
        if (f instanceof factor) {
            System.out.println(f);
        }
        else if (f instanceof set) {
            System.out.println(f);
        }
        else if (f instanceof func) {
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
