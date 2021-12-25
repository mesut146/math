package com.mesut.math.parser;

import com.mesut.math.core.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AstBuilder {

    public static func make(String line) throws IOException {
        Lexer lexer = new Lexer(new StringReader(line));
        Parser parser = new Parser(lexer);
        return new AstBuilder().visitLine(parser.line());
    }

    private func visitLine(Ast.line line) {
        func e = visitExpr(line.expr);
        if (line.g1 == null) return e;
        if (!(e.isVariable() || e instanceof FuncCall)) {
            throw new RuntimeException("invalid lhs");
        }
        return new Equation(e, visitExpr(line.g1.expr));
    }


    private func visitFuncCall(Ast.funcCall funcCall) {
        List<func> args = new ArrayList<>();
        if (funcCall.args != null) {
            args.add(visitExpr(funcCall.args.expr));
            for (Ast.argsg1 g1 : funcCall.args.rest) {
                args.add(visitExpr(g1.expr));
            }
        }
        return func.makeFunc(visitName(funcCall.name), args);
    }

    private String visitName(Ast.name name) {
        if (name.IDENT != null) {
            return name.IDENT.value;
        }
        else {
            return name.PI.value;
        }
    }

    private func visitExpr(Ast.expr expr) {
        func res = visitMul(expr.mul);
        for (Ast.exprg1 g1 : expr.rest) {
            if (g1.g2.PLUS != null) {
                res = res.add(visitMul(g1.mul));
            }
            else {
                res = res.add(visitMul(g1.mul).negate());
            }
        }
        return res;
    }

    private func visitMul(Ast.mul term) {
        func res = visitUnary(term.unary);
        for (Ast.mulg1 g1 : term.rest) {
            if (g1.g2.STAR != null) {
                res = res.mul(visitUnary(g1.unary));
            }
            else {
                res = res.div(visitUnary(g1.unary).negate());
            }
        }
        return res;
    }

    private func visitPow(Ast.pow pow) {
        func res = visitBang(pow.bang);
        if (pow.rest != null) {
            res = res.pow(visitUnaryOrBang(pow.rest.unaryOrBang));
        }
        return res;
    }

    private func visitUnaryOrBang(Ast.unaryOrBang unaryOrBang) {
        if (unaryOrBang.unaryorbang1 != null) {
            return visitUnary(unaryOrBang.unaryorbang1.unary);
        }
        else {
            return visitBang(unaryOrBang.bang);
        }
    }

    private func visitBang(Ast.bang bang) {
        func lhs = visitElem(bang.elem);
        if (bang.BANG != null) {
            return lhs.fac();
        }
        return lhs;
    }

    private func visitUnary(Ast.unary unary) {
        if (unary.unary1 != null) {
            return visitUnary(unary.unary1.unary).negate();
        }
        else {
            return visitPow(unary.pow);
        }
    }

    private func visitElem(Ast.elem elem) {
        if (elem.cons != null) {
            return visitCons(elem.cons);
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

    private func visitCons(Ast.cons c) {
        if (c.NUM != null) {
            return cons.of(Double.parseDouble(c.NUM.value));
        }
        else if (c.E != null) {
            return cons.E;
        }
        else if (c.I != null) {
            return cons.i;
        }
        else if (c.PI != null) {
            return cons.PI;
        }
        else if (c.PHI != null) {
            return cons.PHI;
        }
        else if (c.INF != null) {
            return cons.INF;
        }
        else {
            throw new RuntimeException("invalid cons: " + c);
        }
    }
}
