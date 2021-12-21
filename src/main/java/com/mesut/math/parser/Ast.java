package com.mesut.math.parser;

import java.util.List;
import java.util.ArrayList;

public class Ast{
    public static class line{
        public expr expr;
        public lineg1 g1;

        public String toString(){
            StringBuilder sb = new StringBuilder("line{");
            sb.append(expr.toString());
            if(g1 != null) sb.append(",");
            sb.append(g1 == null?"":g1.toString());
            return sb.append("}").toString();
        }
    }
    public static class lineg1{
        public Token EQ;
        public expr expr;

        public String toString(){
            StringBuilder sb = new StringBuilder("lineg1{");
            sb.append("'" + EQ.value + "'");
            sb.append(",");
            sb.append(expr.toString());
            return sb.append("}").toString();
        }
    }
    public static class expr{
        public mul mul;
        public List<exprg1> rest = new ArrayList<>();

        public String toString(){
            StringBuilder sb = new StringBuilder("expr{");
            sb.append(mul.toString());
            if(!rest.isEmpty()) sb.append(",");
            if(!rest.isEmpty()){
                sb.append('[');
                for(int i = 0;i < rest.size();i++){
                    sb.append(rest.get(i).toString());
                    if(i < rest.size() - 1) sb.append(",");
                }
                sb.append(']');
            }
            return sb.append("}").toString();
        }
    }
    public static class exprg2{
        public int which;
        public Token PLUS;
        public Token MINUS;

        public String toString(){
            StringBuilder sb = new StringBuilder("exprg2{");
            if(which == 1){
                sb.append("'" + PLUS.value + "'");
            }
            else if(which == 2){
                sb.append("'" + MINUS.value + "'");
            }
            return sb.append("}").toString();
        }
    }
    public static class exprg1{
        public exprg2 g2;
        public mul mul;

        public String toString(){
            StringBuilder sb = new StringBuilder("exprg1{");
            sb.append(g2.toString());
            sb.append(",");
            sb.append(mul.toString());
            return sb.append("}").toString();
        }
    }
    public static class mul{
        public unary unary;
        public List<mulg1> rest = new ArrayList<>();

        public String toString(){
            StringBuilder sb = new StringBuilder("mul{");
            sb.append(unary.toString());
            if(!rest.isEmpty()) sb.append(",");
            if(!rest.isEmpty()){
                sb.append('[');
                for(int i = 0;i < rest.size();i++){
                    sb.append(rest.get(i).toString());
                    if(i < rest.size() - 1) sb.append(",");
                }
                sb.append(']');
            }
            return sb.append("}").toString();
        }
    }
    public static class mulg2{
        public int which;
        public Token STAR;
        public Token SLASH;

        public String toString(){
            StringBuilder sb = new StringBuilder("mulg2{");
            if(which == 1){
                sb.append("'" + STAR.value + "'");
            }
            else if(which == 2){
                sb.append("'" + SLASH.value + "'");
            }
            return sb.append("}").toString();
        }
    }
    public static class mulg1{
        public mulg2 g2;
        public unary unary;

        public String toString(){
            StringBuilder sb = new StringBuilder("mulg1{");
            sb.append(g2.toString());
            sb.append(",");
            sb.append(unary.toString());
            return sb.append("}").toString();
        }
    }
    public static class unary{
        public int which;
        Unary1 unary1;
        public pow pow;
        public static class Unary1{
                public Token MINUS;
                public unary unary;
                public String toString(){
                        StringBuilder sb = new StringBuilder();
                        sb.append("'" + MINUS.value + "'");
                        sb.append(",");
                        sb.append(unary.toString());
                        return sb.toString();
                }
        }
        public String toString(){
            StringBuilder sb = new StringBuilder("unary{");
            if(which == 1){
                sb.append(unary1);
            }
            else if(which == 2){
                sb.append(pow.toString());
            }
            return sb.append("}").toString();
        }
    }
    public static class pow{
        public bang bang;
        public powg1 rest;

        public String toString(){
            StringBuilder sb = new StringBuilder("pow{");
            sb.append(bang.toString());
            if(rest != null) sb.append(",");
            sb.append(rest == null?"":rest.toString());
            return sb.append("}").toString();
        }
    }
    public static class powg1{
        public Token POW;
        public unaryOrBang unaryOrBang;

        public String toString(){
            StringBuilder sb = new StringBuilder("powg1{");
            sb.append("'" + POW.value + "'");
            sb.append(",");
            sb.append(unaryOrBang.toString());
            return sb.append("}").toString();
        }
    }
    public static class unaryOrBang{
        public int which;
        Unaryorbang1 unaryorbang1;
        public bang bang;
        public static class Unaryorbang1{
                public Token MINUS;
                public unary unary;
                public String toString(){
                        StringBuilder sb = new StringBuilder();
                        sb.append("'" + MINUS.value + "'");
                        sb.append(",");
                        sb.append(unary.toString());
                        return sb.toString();
                }
        }
        public String toString(){
            StringBuilder sb = new StringBuilder("unaryOrBang{");
            if(which == 1){
                sb.append(unaryorbang1);
            }
            else if(which == 2){
                sb.append(bang.toString());
            }
            return sb.append("}").toString();
        }
    }
    public static class bang{
        public elem elem;
        public Token BANG;

        public String toString(){
            StringBuilder sb = new StringBuilder("bang{");
            sb.append(elem.toString());
            if(BANG != null) sb.append(",");
            sb.append(BANG == null?"":"'" + BANG.value + "'");
            return sb.append("}").toString();
        }
    }
    public static class elem{
        public int which;
        public cons cons;
        public var var;
        public funcCall funcCall;
        Elem4 elem4;
        public static class Elem4{
                public Token LP;
                public expr expr;
                public Token RP;
                public String toString(){
                        StringBuilder sb = new StringBuilder();
                        sb.append("'" + LP.value + "'");
                        sb.append(",");
                        sb.append(expr.toString());
                        sb.append(",");
                        sb.append("'" + RP.value + "'");
                        return sb.toString();
                }
        }
        public String toString(){
            StringBuilder sb = new StringBuilder("elem{");
            if(which == 1){
                sb.append(cons.toString());
            }
            else if(which == 2){
                sb.append(var.toString());
            }
            else if(which == 3){
                sb.append(funcCall.toString());
            }
            else if(which == 4){
                sb.append(elem4);
            }
            return sb.append("}").toString();
        }
    }
    public static class funcCall{
        public name name;
        public Token LP;
        public args args;
        public Token RP;

        public String toString(){
            StringBuilder sb = new StringBuilder("funcCall{");
            sb.append(name.toString());
            sb.append(",");
            sb.append("'" + LP.value + "'");
            if(args != null) sb.append(",");
            sb.append(args == null?"":args.toString());
            sb.append(",");
            sb.append("'" + RP.value + "'");
            return sb.append("}").toString();
        }
    }
    public static class name{
        public int which;
        public Token IDENT;
        public Token PI;

        public String toString(){
            StringBuilder sb = new StringBuilder("name{");
            if(which == 1){
                sb.append("'" + IDENT.value + "'");
            }
            else if(which == 2){
                sb.append("'" + PI.value + "'");
            }
            return sb.append("}").toString();
        }
    }
    public static class args{
        public line line;
        public List<argsg1> rest = new ArrayList<>();

        public String toString(){
            StringBuilder sb = new StringBuilder("args{");
            sb.append(line.toString());
            if(!rest.isEmpty()) sb.append(",");
            if(!rest.isEmpty()){
                sb.append('[');
                for(int i = 0;i < rest.size();i++){
                    sb.append(rest.get(i).toString());
                    if(i < rest.size() - 1) sb.append(",");
                }
                sb.append(']');
            }
            return sb.append("}").toString();
        }
    }
    public static class argsg1{
        public Token COMMA;
        public line line;

        public String toString(){
            StringBuilder sb = new StringBuilder("argsg1{");
            sb.append("'" + COMMA.value + "'");
            sb.append(",");
            sb.append(line.toString());
            return sb.append("}").toString();
        }
    }
    public static class cons{
        public int which;
        public Token E;
        public Token PI;
        public Token PHI;
        public Token I;
        public Token INF;
        public Token NUM;

        public String toString(){
            StringBuilder sb = new StringBuilder("cons{");
            if(which == 1){
                sb.append("'" + E.value + "'");
            }
            else if(which == 2){
                sb.append("'" + PI.value + "'");
            }
            else if(which == 3){
                sb.append("'" + PHI.value + "'");
            }
            else if(which == 4){
                sb.append("'" + I.value + "'");
            }
            else if(which == 5){
                sb.append("'" + INF.value + "'");
            }
            else if(which == 6){
                sb.append("'" + NUM.value + "'");
            }
            return sb.append("}").toString();
        }
    }
    public static class var{
        public Token IDENT;

        public String toString(){
            StringBuilder sb = new StringBuilder("var{");
            sb.append("'" + IDENT.value + "'");
            return sb.append("}").toString();
        }
    }
}
