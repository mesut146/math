package com.mesut.math.ipret;

import com.mesut.math.core.Equation;
import com.mesut.math.core.FuncCall;
import com.mesut.math.core.func;

import java.util.List;
import java.util.ListIterator;

public class Visitor {

    public func visit(func f) {
        if (f.isAdd() || f.isMul()) {
            visit(f.list);
            return f;
        }
        else if (f instanceof FuncCall) {
            return visit(((FuncCall) f));
        }
        else if (f instanceof Equation) {
            return visit((Equation) f);
        }
        else if (f.isPow()) {
            visitAB(f);
            return f;
        }
        return f;
    }

    private void visitAB(func f) {
        f.a = visit(f.a);
        f.b = visit(f.b);
    }

    public void visit(List<func> list) {
        for (ListIterator<func> it = list.listIterator(); it.hasNext(); ) {
            func term = it.next();
            it.set(visit(term));
        }
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
