package com.mesut.math.operator;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

import java.util.List;
import java.util.Set;

public class div extends func {

    public div(func f1, func f2) {
        a = f1;
        b = f2;
        //type = types.div;
    }

    @Override
    public boolean isDiv() {
        return true;
    }

    @Override
    public func getReal() {
        func cd = b.getReal().pow(2).add(b.getImaginary().pow(2));
        func ac = a.getReal().mul(b.getReal());
        func bd = a.getImaginary().mul(b.getImaginary());
        return sign(ac.add(bd).div(cd));
    }

    @Override
    public func getImaginary() {
        func cd = b.getReal().pow(2).add(b.getImaginary().pow(2));
        func ad = a.getReal().mul(b.getImaginary());
        func bc = a.getImaginary().mul(b.getReal());
        return sign(bc.sub(ad).div(cd));
    }


    @Override
    public void vars0(Set<variable> vars) {
        a.vars0(vars);
        b.vars0(vars);
    }

    @Override
    public func get0(variable[] v, cons[] c) {
        return signf(a.get(v, c).div(b.get(v, c)));
    }

    @Override
    public double eval(variable[] v, double[] d) {
        // TODO: Implement this method

        return sign * a.eval(v, d) / b.eval(v, d);
    }

    @Override
    public cons evalc(variable[] v, double[] d) {
        // TODO: Implement this method
        return new cons(eval(v, d));
    }

    @Override
    public String toLatex() {
        // TODO: Implement this method
        return String.format("\\frac{%s,%s}", a, b);
    }

    @Override
    public func copy0() {
        // TODO: Implement this method
        return new div(a, b);
    }


    @Override
    public func derivative(variable v) {
        func l = a.derivative(v).mul(b);
        func r = a.mul(b.derivative(v));
        return sign(a.derivative(v).mul(b).sub(a.mul(b.derivative(v))).div(b.pow(2)));
    }

    @Override
    public func integrate(variable v) {
        if (b.isConstant()) {
            return a.integrate(v).div(b);
        }
        // 1/lnx  
        // 
        return new Integral(this, v);
    }

    @Override
    public String toString2() {
        StringBuilder sb = new StringBuilder();
        if (a.isConstant() || a.isVariable()) {
            sb.append(a);
        }
        else {
            sb.append("(").append(a).append(")");
        }
        sb.append("/");
        if (b.isConstant() || b.isVariable()) {
            sb.append(b);
        }
        else {
            sb.append("(" + b + ")");
        }
        return sb.toString();
    }

    public func simplify() {
        if (a.is(0)) {
            return cons.ZERO;
        }
        /*if(a.isCons0()&&b.isCons0()){
            return evalc();
        }*/
        if (a.isDiv()) {// (a/b)/c=a/(b*c)
            return sign(a.a.div(a.b.mul(b)));
        }
        if (b.isDiv()) {// a/(b/c)=a*c/b
            return sign(a.mul(b.b).div(b.a));
        }
        if (a.sign == -1) {
            sign *= -1;
            a = a.copy();
            a.sign = 1;
        }
        if (b.sign == -1) {
            sign *= -1;
            b = b.copy();
            b.sign = 1;
        }
        if (b.is(1)) {
            return signf(a);
        }
        if (b.is(0)) {
            return signf(cons.INF);
        }
        if (a.eq(b)) {
            return signf(cons.ONE);
        }
        return this;
        /*List<func> p=getFree();
		List<func> q=getFree();
		List<func> m=getFree();
		List<func> n=getFree();
		if(a.isMul()){
			p.addAll(a.f);
		}else{
			p.add(a);
		}
		if(b.isMul()){
			q.addAll(b.f);
		}else{
			q.add(b);
		}
		boolean b[]=new boolean[q.size()];
		//(x^2)/(x^3)
		boolean simp=false;
		for(int i=0;i<p.size();i++){
			func v=p.get(i);
			func pow=v.isPow()?v.b:Constant.ONE;
			v=v.isPow()?v.a:v;
			for(int j=0;j<q.size();j++){
				if(!b[j]){
					t o;
					if((o=mul.e3(v,q.get(j))).b){
						b[j]=true;
						pow=pow.sub(o.f);
						simp=true;
					}
				}
			}
			m.add(v.pow(pow));
		}
		for(int j=0;j<q.size();j++){
			if(!b[j]){
				n.add(q.get(j));
			}
		}
        if(simp) return new div(new mul(m).simplify(),new mul(n).simplify()).simplify();
		
		return this;*/
    }

    public void group() {
        func p = cons.ONE;
        func q = p;
        //(x^2+x)/x^3
        if (a.isAdd()) {

        }
        if (b.isAdd()) {

        }
        a = p;
        b = q;
    }

    private func group0(add a) {
        List<func> l = a.f;
        //x^5+x^3
        List<func> base = getFree();
        List<func> pow = getFree();
        func p = cons.ONE;
        for (int i = 0; i < l.size(); i++) {
            func f = l.get(i);
            if (f.isPow()) {
                base.add(f.a);
                pow.add(f.b);
                if (true) {

                }
            }
            else {
                base.add(f);
                pow.add(cons.ONE);
            }
        }
        return null;
    }

    @Override
    public boolean eq2(func f) {
        if (a.eq(f.a) && b.eq(f.b)) {
            return true;
        }
        return false;
    }

    @Override
    public func substitude0(variable v, func p) {
        return a.substitude(v, p).div(b.substitude(v, p));
    }


}
