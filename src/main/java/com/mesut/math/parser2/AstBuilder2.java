package com.mesut.math.parser2;

import com.mesut.math.core.Equation;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AstBuilder2 {

    public static func make(String line) throws IOException {
        Lexer lexer = new Lexer(new StringReader(line));
        Parser parser = new Parser(lexer);
        return new AstBuilder2().visitLine(parser.line());
    }

    private func visitLine(Ast.line line) {
        func e = visitExpr(line.expr);
        if (line.g1 == null) return e;
        return new Equation(e, visitExpr(line.g1.expr));
    }


    private func visitFuncCall(Ast.funcCall funcCall) {
        List<func> args = new ArrayList<>();
        if (funcCall.args != null) {
            args.add(visitLine(funcCall.args.line));
            for (Ast.argsg1 g1 : funcCall.args.rest) {
                args.add(visitLine(g1.line));
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

    private func visitVar(Ast.var var) {
        return variable.from(var.IDENT.value);
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
