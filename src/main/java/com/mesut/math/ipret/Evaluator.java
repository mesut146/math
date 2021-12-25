package com.mesut.math.ipret;

import com.mesut.math.core.*;
import com.mesut.math.prime.factor;

import java.util.List;

public class Evaluator extends Visitor {
    Interpreter interpreter;

    public Evaluator(Interpreter interpreter) {
        this.interpreter = interpreter;
    }


    @Override
    public func visit(FuncCall call) {
        switch (call.getName()) {
            case "derivative":
            case "der":
                if (call.getArgs().isEmpty()) {
                    throw new RuntimeException("specicy an expr");
                }
                if (call.getArgs().size() == 1) {
                    throw new RuntimeException("specify a derivative variable");
                }
                else {
                    func res = call.getArg(0);
                    for (int i = 1; i < call.getArgs().size(); i++) {
                        res = res.derivative(call.getArg(i).asVar());
                    }
                    return res;
                }
            case "simplify":
            case "simp":
                validateArgSize(call, 1);
                return call.getArg(0).simplify();
            case "int":
            case "integral":
            case "integrate": {
                //first arg is func
                //second arg is var
                //last two are interval(optional)
                Integral integral = new Integral(call.getArg(0), call.getArg(1));
                if (call.getArgs().size() > 2) {
                    integral.lower = call.getArg(2);
                    integral.upper = call.getArg(3);
                }
                return integral;
            }
            case "factor":
            case "factorize":
            case "factorise": {
                validateArgSize(call, 1);
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
                validateArgSize(call, 1);
                return call.getArg(0).getImaginary();
            }
            case "real":
            case "re": {
                validateArgSize(call, 1);
                return call.getArg(0).getReal();
            }
        }
        return custom(call);
    }

    void validateArgSize(FuncCall call, int size) {
        if (call.getArgs().size() != size) {
            throw new RuntimeException("invalid arg count expected: " + size);
        }
    }

    private func custom(FuncCall call) {
        Equation decl = interpreter.findEq(call.getName());
        if (decl.getLeft().asCall().getArgs().size() != call.getArgs().size()) {
            throw new RuntimeException("unmatched arg count");
        }
        func rhs = interpreter.checkRhs(call.getName());
        if (call.getArgs().isEmpty()) {
            //eval
            return new cons(rhs.eval());
        }
        List<func> declArgs = decl.getLeft().asCall().getArgs();
        for (int i = 0; i < call.getArgs().size(); i++) {
            func arg = call.getArg(i);
            rhs = rhs.substitute(declArgs.get(i).asVar(), arg);
        }
        return rhs;

    }
}
