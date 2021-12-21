package com.mesut.math.parser;

import java.io.IOException;

public class Parser {
    Lexer lexer;
    Token la;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        la = lexer.next();
    }

    Token consume(int type) {
        if (la.type != type) {
            throw new RuntimeException("unexpected token: " + la + " expecting: " + type);
        }
        try {
            Token res = la;
            la = lexer.next();
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Ast.line line() {
        Ast.line res = new Ast.line();
        res.expr = expr();
        if (la.type == Tokens.EQ) {
            res.g1 = lineg1();
        }
        return res;
    }

    public Ast.lineg1 lineg1() {
        Ast.lineg1 res = new Ast.lineg1();
        res.EQ = consume(Tokens.EQ);
        res.expr = expr();
        return res;
    }

    public Ast.expr expr() {
        Ast.expr res = new Ast.expr();
        res.mul = mul();
        while (la.type == Tokens.PLUS || la.type == Tokens.MINUS) {
            res.rest.add(exprg1());
        }
        return res;
    }

    public Ast.exprg2 exprg2() {
        Ast.exprg2 res = new Ast.exprg2();
        switch (la.type) {
            case Tokens.PLUS: {
                res.which = 1;
                res.PLUS = consume(Tokens.PLUS);
                break;
            }
            case Tokens.MINUS: {
                res.which = 2;
                res.MINUS = consume(Tokens.MINUS);
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [PLUS,MINUS] got: " + la);
            }
        }
        return res;
    }

    public Ast.exprg1 exprg1() {
        Ast.exprg1 res = new Ast.exprg1();
        res.g2 = exprg2();
        res.mul = mul();
        return res;
    }

    public Ast.mul mul() {
        Ast.mul res = new Ast.mul();
        res.unary = unary();
        while (la.type == Tokens.SLASH || la.type == Tokens.STAR) {
            res.rest.add(mulg1());
        }
        return res;
    }

    public Ast.mulg2 mulg2() {
        Ast.mulg2 res = new Ast.mulg2();
        switch (la.type) {
            case Tokens.STAR: {
                res.which = 1;
                res.STAR = consume(Tokens.STAR);
                break;
            }
            case Tokens.SLASH: {
                res.which = 2;
                res.SLASH = consume(Tokens.SLASH);
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [SLASH,STAR] got: " + la);
            }
        }
        return res;
    }

    public Ast.mulg1 mulg1() {
        Ast.mulg1 res = new Ast.mulg1();
        res.g2 = mulg2();
        res.unary = unary();
        return res;
    }

    public Ast.unary unary() {
        Ast.unary res = new Ast.unary();
        switch (la.type) {
            case Tokens.MINUS: {
                res.which = 1;
                Ast.unary.Unary1 unary1 = res.unary1 = new Ast.unary.Unary1();
                unary1.MINUS = consume(Tokens.MINUS);
                unary1.unary = unary();
                break;
            }
            case Tokens.IDENT:
            case Tokens.PI:
            case Tokens.I:
            case Tokens.PHI:
            case Tokens.E:
            case Tokens.INF:
            case Tokens.LP:
            case Tokens.NUM: {
                res.which = 2;
                res.pow = pow();
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [IDENT,PI,I,PHI,E,INF,LP,MINUS,NUM] got: " + la);
            }
        }
        return res;
    }

    public Ast.pow pow() {
        Ast.pow res = new Ast.pow();
        res.bang = bang();
        if (la.type == Tokens.POW) {
            res.rest = powg1();
        }
        return res;
    }

    public Ast.powg1 powg1() {
        Ast.powg1 res = new Ast.powg1();
        res.POW = consume(Tokens.POW);
        res.unaryOrBang = unaryOrBang();
        return res;
    }

    public Ast.unaryOrBang unaryOrBang() {
        Ast.unaryOrBang res = new Ast.unaryOrBang();
        switch (la.type) {
            case Tokens.MINUS: {
                res.which = 1;
                Ast.unaryOrBang.Unaryorbang1 unaryorbang1 = res.unaryorbang1 = new Ast.unaryOrBang.Unaryorbang1();
                unaryorbang1.MINUS = consume(Tokens.MINUS);
                unaryorbang1.unary = unary();
                break;
            }
            case Tokens.IDENT:
            case Tokens.PI:
            case Tokens.I:
            case Tokens.PHI:
            case Tokens.E:
            case Tokens.INF:
            case Tokens.LP:
            case Tokens.NUM: {
                res.which = 2;
                res.bang = bang();
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [IDENT,PI,I,PHI,E,INF,LP,MINUS,NUM] got: " + la);
            }
        }
        return res;
    }

    public Ast.bang bang() {
        Ast.bang res = new Ast.bang();
        res.elem = elem();
        if (la.type == Tokens.BANG) {
            res.BANG = consume(Tokens.BANG);
        }
        return res;
    }

    public Ast.elem elem() {
        Ast.elem res = new Ast.elem();
        switch (la.type) {
            case Tokens.I:
            case Tokens.PHI:
            case Tokens.E:
            case Tokens.INF:
            case Tokens.NUM: {
                res.which = 1;
                res.cons = cons_no_PI();
                break;
            }
            case Tokens.LP: {
                res.which = 4;
                Ast.elem.Elem4 elem4 = res.elem4 = new Ast.elem.Elem4();
                elem4.LP = consume(Tokens.LP);
                elem4.expr = expr();
                elem4.RP = consume(Tokens.RP);
                break;
            }
            case Tokens.PI: {
                Token PIf1 = consume(Tokens.PI);
                switch (la.type) {
                    case Tokens.LP: {
                        res.which = 3;
                        res.funcCall = funcCall1(PIf1);
                        break;
                    }
                    default: {
                        res.which = 1;
                        res.cons = cons1(PIf1);
                    }
                }
                break;
            }
            case Tokens.IDENT: {
                Token IDENTf1 = consume(Tokens.IDENT);
                switch (la.type) {
                    case Tokens.LP: {
                        res.which = 3;
                        res.funcCall = funcCall2(IDENTf1);
                        break;
                    }
                    default: {
                        res.which = 2;
                        res.var = var1(IDENTf1);
                    }
                }
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [IDENT,PI,I,PHI,E,INF,LP,NUM] got: " + la);
            }
        }
        return res;
    }

    public Ast.funcCall funcCall() {
        Ast.funcCall res = new Ast.funcCall();
        res.name = name();
        res.LP = consume(Tokens.LP);
        if (la.type == Tokens.IDENT || la.type == Tokens.PI || la.type == Tokens.I || la.type == Tokens.PHI || la.type == Tokens.E || la.type == Tokens.INF || la.type == Tokens.LP || la.type == Tokens.MINUS || la.type == Tokens.NUM) {
            res.args = args();
        }
        res.RP = consume(Tokens.RP);
        return res;
    }

    public Ast.funcCall funcCall2(Token IDENTf1) {
        Ast.funcCall res = new Ast.funcCall();
        res.name = name2(IDENTf1);
        res.LP = consume(Tokens.LP);
        if (la.type == Tokens.IDENT || la.type == Tokens.PI || la.type == Tokens.I || la.type == Tokens.PHI || la.type == Tokens.E || la.type == Tokens.INF || la.type == Tokens.LP || la.type == Tokens.MINUS || la.type == Tokens.NUM) {
            res.args = args();
        }
        res.RP = consume(Tokens.RP);
        return res;
    }

    public Ast.funcCall funcCall1(Token PIf1) {
        Ast.funcCall res = new Ast.funcCall();
        res.name = name1(PIf1);
        res.LP = consume(Tokens.LP);
        if (la.type == Tokens.IDENT || la.type == Tokens.PI || la.type == Tokens.I || la.type == Tokens.PHI || la.type == Tokens.E || la.type == Tokens.INF || la.type == Tokens.LP || la.type == Tokens.MINUS || la.type == Tokens.NUM) {
            res.args = args();
        }
        res.RP = consume(Tokens.RP);
        return res;
    }

    public Ast.name name() {
        Ast.name res = new Ast.name();
        switch (la.type) {
            case Tokens.IDENT: {
                res.which = 1;
                res.IDENT = consume(Tokens.IDENT);
                break;
            }
            case Tokens.PI: {
                res.which = 2;
                res.PI = consume(Tokens.PI);
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [IDENT,PI] got: " + la);
            }
        }
        return res;
    }

    public Ast.name name2(Token IDENTf1) {
        Ast.name res = new Ast.name();
        res.which = 1;
        res.IDENT = IDENTf1;
        return res;
    }

    public Ast.name name1(Token PIf1) {
        Ast.name res = new Ast.name();
        res.which = 2;
        res.PI = PIf1;
        return res;
    }

    public Ast.args args() {
        Ast.args res = new Ast.args();
        res.line = line();
        while (la.type == Tokens.COMMA) {
            res.rest.add(argsg1());
        }
        return res;
    }

    public Ast.argsg1 argsg1() {
        Ast.argsg1 res = new Ast.argsg1();
        res.COMMA = consume(Tokens.COMMA);
        res.line = line();
        return res;
    }

    public Ast.cons cons() {
        Ast.cons res = new Ast.cons();
        switch (la.type) {
            case Tokens.E: {
                res.which = 1;
                res.E = consume(Tokens.E);
                break;
            }
            case Tokens.PI: {
                res.which = 2;
                res.PI = consume(Tokens.PI);
                break;
            }
            case Tokens.PHI: {
                res.which = 3;
                res.PHI = consume(Tokens.PHI);
                break;
            }
            case Tokens.I: {
                res.which = 4;
                res.I = consume(Tokens.I);
                break;
            }
            case Tokens.INF: {
                res.which = 5;
                res.INF = consume(Tokens.INF);
                break;
            }
            case Tokens.NUM: {
                res.which = 6;
                res.NUM = consume(Tokens.NUM);
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [PI,I,PHI,E,INF,NUM] got: " + la);
            }
        }
        return res;
    }

    public Ast.cons cons_no_PI() {
        Ast.cons res = new Ast.cons();
        switch (la.type) {
            case Tokens.E: {
                res.which = 1;
                res.E = consume(Tokens.E);
                break;
            }
            case Tokens.PHI: {
                res.which = 3;
                res.PHI = consume(Tokens.PHI);
                break;
            }
            case Tokens.I: {
                res.which = 4;
                res.I = consume(Tokens.I);
                break;
            }
            case Tokens.INF: {
                res.which = 5;
                res.INF = consume(Tokens.INF);
                break;
            }
            case Tokens.NUM: {
                res.which = 6;
                res.NUM = consume(Tokens.NUM);
                break;
            }
            default: {
                throw new RuntimeException("expecting one of [I,PHI,E,INF,NUM] got: " + la);
            }
        }
        return res;
    }

    public Ast.cons cons1(Token PIf1) {
        Ast.cons res = new Ast.cons();
        res.which = 2;
        res.PI = PIf1;
        return res;
    }

    public Ast.var var() {
        Ast.var res = new Ast.var();
        res.IDENT = consume(Tokens.IDENT);
        return res;
    }

    public Ast.var var1(Token IDENTf1) {
        Ast.var res = new Ast.var();
        res.IDENT = IDENTf1;
        return res;
    }

}
