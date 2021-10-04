package com.mesut.math.parser2;

import com.mesut.math.core.*;
import com.mesut.math.operator.add;
import com.mesut.math.operator.div;
import com.mesut.math.operator.mul;
import com.mesut.math.operator.pow;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AstBuilder {

    public static func make(String line) throws IOException {
        Lexer lexer = new Lexer(new StringReader(line));
        Parser parser = new Parser(lexer);
        return new AstBuilder().visitEq(parser.eq());
    }

    public func visitEq(Ast.eq eq) {
        if (eq.eq1 != null) {
            return new Equation(visitLhs(eq.eq1.lhs), visitExpr(eq.eq1.expr));
        }
        else {
            return visitExpr(eq.expr);
        }
    }

    private func visitLhs(Ast.lhs lhs) {
        if (lhs.var != null) {
            return visitVar(lhs.var);
        }
        else {
            return visitFuncCall(lhs.funcCall);
        }
    }

    private func visitFuncCall(Ast.funcCall funcCall) {
        List<func> args = new ArrayList<>();
        if (funcCall.args != null) {
            args.add(visitExpr(funcCall.args.expr));
            for (Ast.argsg1 g1 : funcCall.args.rest) {
                args.add(visitExpr(g1.expr));
            }
        }
        return new FuncCall(funcCall.IDENT.value, args);
    }

    private func visitVar(Ast.var var) {
        return variable.from(var.IDENT.value);
    }

    private func visitExpr(Ast.expr expr) {
        func res = visitTerm(expr.term);
        for (Ast.exprg1 g1 : expr.rest) {
            if (g1.g2.PLUS != null) {
                res = new add(res, visitExpr(g1.expr));
            }
            else {
                res = new add(res, visitExpr(g1.expr).negate());
            }
        }
        return res;
    }

    private func visitTerm(Ast.term term) {
        func res = visitPow(term.pow);
        for (Ast.termg1 g1 : term.rest) {
            if (g1.g2.STAR != null) {
                res = new mul(res, visitTerm(g1.term));
            }
            else {
                res = new div(res, visitTerm(g1.term).negate());
            }
        }
        return res;
    }

    private func visitPow(Ast.pow pow) {
        func res = visitUnary(pow.unary);
        for (Ast.powg1 g1 : pow.rest) {
            res = new pow(res, visitPow(g1.pow));
        }
        return res;
    }

    private func visitUnary(Ast.unary unary) {
        if (unary.unary1 != null) {
            return visitElem(unary.unary1.elem).negate();
        }
        else {
            return visitElem(unary.unary2.elem).fac();
        }
    }

    private func visitElem(Ast.elem elem) {
        if (elem.cons != null) {
            if (elem.cons.NUM != null) {
                return cons.of(Double.parseDouble(elem.cons.NUM.value));
            }
            else if (elem.cons.E != null) {
                return cons.E;
            }
            else if (elem.cons.I != null) {
                return cons.i;
            }
            else if (elem.cons.PHI != null) {
                return cons.PHI;
            }
            else {
                return cons.INF;
            }
        }
        else if (elem.var != null) {
            return variable.from(elem.var.IDENT.value);
        }
        else if (elem.funcCall != null) {
            return visitFuncCall(elem.funcCall);
        }
        else {
            return visitExpr(elem.elem4.expr);
        }
    }
}
