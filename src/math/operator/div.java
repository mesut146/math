package math.operator;

import math.*;
import java.util.*;
import math.core.*;

public class div extends func
{

    @Override
    public func get(var[] v, cons[] c)
    {
        // TODO: Implement this method
        return signto(a.get(v,c).div(b.get(v,c)));
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        // TODO: Implement this method
        
        return sign*a.eval(v,d)/b.eval(v,d);
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return new cons(eval(v,d));
    }

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return String.format("\\frac{%s,%s}",a,b);
    }

    @Override
    public func copy0()
    {
        // TODO: Implement this method
        return new div(a,b);
    }

	
    public div(func f1,func f2){
        a=f1;
        b=f2;
		type=types.div;
    }

    @Override
    public func derivative(var v)
    {

		return a.derivative(v).mul(b).sub(a.mul(b.derivative(v))).div(b.pow(2));
    }

    @Override
    public func integrate(var v)
    {
        if(b.isConstant()){
            return a.integrate(v).div(b);
        }
        // 1/lnx  
        // 
        return new Anti(this);
    }
    
    @Override
    public String toString2()
    {
		StringBuilder sb=new StringBuilder();
		if(a.isConstant()||a.isVariable()){
			sb.append(a);
		}else{
			sb.append("("+a+")");
		}
		sb.append("/");
		if(b.isConstant()||b.isVariable()){
			sb.append(b);
		}else{
			sb.append("("+b+")");
		}
		return sb.toString();
    }
	
    public func simplify(){
        if(a.is(0)){
            return cons.ZERO;
        }
        if(a.isConstant()&&b.isConstant()){
            return evalc();
        }
        if(a.isDiv()){// (a/b)/c=a/(b*c)
            return signto(a.a.div(a.b.mul(b)));
        }
		if(b.is(1)){
			return a;
		}
		if(a.eq(b)){
			return cons.ONE;
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
	
	public void group(){
		func p=cons.ONE;
		func q=p;
		//(x^2+x)/x^3
		if(a.isAdd()){
			
		}
		if(b.isAdd()){
			
		}
		a=p;
		b=q;
	}
	
	private func group0(add a){
		List<func> l=a.f;
		//x^5+x^3
		List<func> base=getFree();
		List<func> pow=getFree();
		func p=cons.ONE;
		for(int i=0;i<l.size();i++){
			func f=l.get(i);
			if(f.isPow()){
				base.add(f.a);
				pow.add(f.b);
				if(true){
					
				}
			}else{
				base.add(f);
				pow.add(cons.ONE);
			}
		}
        return null;
	}

	@Override
	public boolean eq2(func f)
	{
		if(a.eq(f.a)&&b.eq(f.b)){
			return true;
		}
		return false;
	}

    @Override
    public func substitude0(var v, func p)
    {
        return a.substitude(v,p).div(b.substitude(v,p));
    }


	
	
}
