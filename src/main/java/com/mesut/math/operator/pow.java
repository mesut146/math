package com.mesut.math.operator;

import com.mesut.math.Config;
import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.funcs.exp;
import com.mesut.math.funcs.ln;
import com.mesut.math.trigonometry.cos;
import com.mesut.math.trigonometry.sin;

import java.util.Set;

public class pow extends func {


    public pow(func f1, func f2) {
        a = f1;
        b = f2;
    }

    @Override
    public boolean isPow() {
        return true;
    }

    @Override
    public func getReal() {
        func ln = new ln(a).simplify();
        func br = b.getReal();
        func bi = b.getImaginary();
        func cr = ln.getReal();
        func ci = ln.getImaginary();
        func l = new exp(br.mul(cr).sub(bi.mul(ci))).simplify();
        func r = new cos(br.mul(ci).add(bi.mul(cr))).simplify();
        return sign(l.mul(r));
    }

    @Override
    public func getImaginary() {
        func ln = new ln(a).simplify();
        func br = b.getReal();
        func bi = b.getImaginary();
        func cr = ln.getReal();
        func ci = ln.getImaginary();
        func l = new exp(br.mul(cr).sub(bi.mul(ci))).simplify();
        func r = new sin(br.mul(ci).add(bi.mul(cr))).simplify();
        return sign(l.mul(r));
    }


    @Override
    public String toLatex() {
        // TODO: Implement this method
        return "(" + a.toLatex() + ")" + "^" + b.toLatex();
    }

    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
        b.vars0(vars);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        func p = a.get(vars, vals);
        func q = b.get(vars, vals);
        /*if(p.isConstant()&&q.isConstant()){
         //System.out.println("getif "+new Constant(Math.pow(p.eval(),q.eval())).s(sign));
         //System.out.println("s="+sign);
         return new Constant(Math.pow(p.eval(),q.eval())).s(sign);
         }*/
        return sign(p.pow(q));
        //return p.pow(q).s(sign);
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        //System.out.println("eval");
        return sign * Math.pow(a.eval(v, vals), b.eval(v, vals));
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        //System.out.println("evalc="+s(a.evalc(v,d).pow(b.evalc(v,d))));
        return sc(a.evalc(vars, vals).pow(b.evalc(vars, vals)));
    }

    @Override
    public func derivative(variable v) {
        /*if(a.isVariable()&&a.eq2(v)&&b.isConstant()){
         //x^n
         return b.mul(a.pow(b.sub(Constant.ONE)));
         }else if (a.isConstant()&&b.isVariable()&&b.eq2(v)){
         //n^x
         return mul(new ln(a).simplify());
         }*/
        if (a.isConstant()) {//a^f=a^f*ln(a)*f'
            return sign(a.pow(b).mul(new ln(a).simplify()).mul(b.derivative(v)));
        }
        if (b.isConstant()) {//f^b=b*f^(b-1)*f'
            return sign(b.mul(a.pow(b.sub(1))).mul(a.derivative(v)));
        }
        //f^g
        //System.out.println("a="+a.isConsfunc()+" b="+b+" ,"+new ln(a).simplify());
        /*
         func f=func.parse(String.format("%s*ln(%s)*%s^%s+%s*%s^(%s-1)*%s",b.derivative(),a,a,b,b,a,b,a.derivative()));
         System.out.println("f="+new ln(a).simplify());
         */
        return sign(b.derivative(v).mul(new ln(a).simplify()).mul(this).add(b.mul(a.derivative(v)).mul(a.pow(b.sub(1)))));
    }

    @Override
    public func integrate(variable v) {
        if (a.eq(v) && b.isConstant()) {
            //x^n
            func t = b.add(1);
            return a.pow(t).div(t);
        }
        else if (a.isConstant() && b.eq(v)) {
            //n^x
            return div(new ln(a));
        }
        //f^g
        if (b.isConstant()) {
            func du = a.derivative(v);//dx
            variable u = new variable("u");
            //System.out.println("du="+du);
            return u.pow(b).div(du).integrate(u).substitute0(u, a);
        }
        return new Integral(this);
    }

    @Override
    public String toString2() {
        StringBuilder sb = new StringBuilder();

        if ((!a.isConstant() && !a.isVariable()) || a.sign == -1) {
            sb.append(a.top());
        }
        else {
            sb.append(a);
        }

        sb.append("^");

        if (!b.isConstant() && !b.isVariable() || b.sign == -1) {
            sb.append(b.top());
        }
        else sb.append(b);

        return sb.toString();
    }

    public func simplify() {
        //System.out.println("simp of pow="+this);
        if (!Config.pow.simplify) {
            return this;
        }
        if (a.is(0)) {
            return cons.ZERO;
        }
        //System.out.println("a="+a+" b="+b);
        if (b.is(0) || a.is(1)) {
            //System.out.println("y");
            return signf(cons.ONE);
        }
        //e^ln(x)=x
        if (a.eq(cons.E)) {
            if (b instanceof ln) {
                return signf(b.a);
            }
            /*if (b.isMul() && b.find(types.ln) != -1) {//e^(f*g*ln(x))=f
                mul b2 = ((mul) b).wout(types.ln);
                ln l = (ln) ((mul) b).get(types.ln);
                b2.sign *= l.sign;
                return sign(l.a.pow(b2));
            }*/
        }

        //System.out.println("hh="+this);
        if (Config.pow.simpCons && a.isCons0() && b.isCons0()) {

            if (Config.useBigDecimal) {
                return this.evalc();
            }
            return new cons(this.eval());

        }
        /*if (a.isConstant() && b.isConstant())
        {
            return new cons(this);
        }*/
        if (b.is(1)) {
            return signf(a);
        }
        if (a.isPow()) {
            /*func p=a.b.mul(b);
            func o=a.a.pow(p);
            func u=b.mul(a.b);*/
            return sign(a.a.pow(a.b.mul(b)));
        }
        if (a.sign == -1 && b.isConstant()) {
            if (b.eval() % 2 == 0) {
                a.sign = 1;
            }
            else {
                sign *= a.sign;
                a.sign = 1;
            }
        }
        //System.out.println("a2="+a+" b2="+b);
        return this;
    }

    @Override
    public boolean eq2(func f) {
        if (a.eq(f.a) && b.eq(f.b)) {
            return true;
        }
        return false;
    }

    @Override
    public func substitute0(variable v, func p) {
        return a.substitute(v, p).pow(b.substitute(v, p));
    }

    @Override
    public func copy0() {
        return new pow(a, b);
    }
}
