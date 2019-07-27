package math.op;

import math.Config;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

import java.util.*;

public class mul extends func
{

    @Override
    public String toLatex()
    {
        StringBuilder sb=new StringBuilder();
        func x;
        for(int i=0;i<f.size();i++){
            x=f.get(i);
            if(x.isAdd()){
                sb.append("(");
                sb.append(x.toLatex());
                sb.append(")");
            }else{
                sb.append(x.toLatex());
            }
            
            if(i<f.size()-1){
                sb.append("*");
            }
        }
        return sb.toString();
    }

    
    public mul(func...f1){
		for(func f2:f1){
			f.add(f2);
		}
		type=types.mul;
    }
	
	public mul(List<func> f1){
		f=f1;
		type=types.mul;
    }

    @Override
    public func get(Variable[] v, Constant[] c)
    {
        func t=Constant.ONE;
        for(func f1:f){
            t=t.mul(f1.get(v, c));
        }
		return s(t);
    }

	@Override
	public double eval(Variable[] v, double[] d)
	{
		double m=1;
        for(func f1:f){
            m*=f1.eval(v,d);
        }
		return sign*m;
	}

	
    @Override
    public func derivative(Variable v)
    {
        //a*b ==> a'*b+a*b'
		func p=f.get(0);
		func q=f.size()==2?f.get(1):new mul(f.subList(1,f.size()));
		func o=p.derivative(v).mul(q).add(p.mul(q.derivative(v)));
        //System.out.println("o="+p);
        return o;
    }

    @Override
    public func integrate(Variable v)
    {
        int i=find(types.constant);
        if(i!=-1){
            List<func> l=getFree();
            l.addAll(f);      
            func k=l.remove(i);
            func m=new mul(l);
            return k.mul(m.integrate(v));
        }if(f.size()==1){
            f.add(Constant.ONE);
        }
        order();
        return byParts();
    }
    
    int find(types t){
        int i=0;
        for(func p:f){
            if(p.type==t){
                return i;
            }
            i++;
        }
        return -1;
    }
    
    
    public func byParts(){
        func a=f.get(0);
        func b=f.get(1);
        func g=b.integrate();
        func h=a.derivative().mul(g);
        System.out.println(String.format("a=%s,b=%s,g=%s,h=%s",a,b,g,h));
        
        return a.mul(g).sub(h.integrate());
    }
    
    void order(){
        if(f.size()<2){
            return;
        }
        //laptü
        //System.out.println("f="+f);
        func a=f.get(0);
        func b=f.get(1);
        if(logarithmic(b)||b.isPolinom()||b.isPower()){
            f.set(0,b);
            f.set(1,a);
        }
    }
    
    void swap(){
        func a=f.get(0);
        func b=f.get(1);
        f.set(0,b);
        f.set(1,a);
    }
    
    boolean logarithmic(func p){
        //System.out.println("f="+f);
        if(p.type==types.ln){
            return true;
        }
        return false;
    }

    @Override
    public String toString2()
    {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<f.size();i++){
			func p=f.get(i);
			if(p.isAdd()){
				sb.append(p.top());
			}else if(p.isPow()){
                sb.append(p.toString());
            }else {
				sb.append(p);
			}
			if(i<f.size()-1){
				sb.append("*");
			}
		}
		return sb.toString();
    }

    
    public func simplify(){
		List<func> l=getFree();
        //System.out.println("before f="+f);
		for(func p:f){
            if(p.is(0)){
                return Constant.ZERO;
            }
			if(p.isMul()){
				l.addAll(p.f);
                sign*=p.sign;
			}
            else if(p.is(-1)){
                sign*=-1;
            }
			else if(!p.is(1)){
                l.add(p);
            }
		}
		f=l;
        if (f.size()==0){
            return Constant.ONE.sign(sign);
        }if(f.size()==1){
            return f.get(0).sign(sign);
        }
        //System.out.println("after f="+f);
		//l.clear();
		/*for(int i=0;i<f.size();i++){
			func p=f.get(i).copy();
            sign*=p.sign;
            p.sign=1;

		}*/
		//f=l;
		/*if (f.size()==0){
			return Constant.ONE.sign(sign);
		}
		cons0();
		mu();
		if(f.size()==1){
			return f.get(0).sign(sign);
		}
		func p=new mul();
		func q=new mul();
		boolean b=false;
		for(func v:f){
			if(v.isDiv()){
				b=true;
				p.f.add(v.a);
				q.f.add(v.b);
			}else{
				p.f.add(v);
			}
		}
        if(b) return p.simplify().div(q.simplify());
		sort();*/

		return this;
    }

    void sort(){
        int i=find(types.constant);
        if(i>0){
            List<func> l=new LinkedList<func>(f);
            func c=l.remove(i);
            l.add(0,c);
            f=l;
        }
    }
    
    String pr(List<func> l){
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(func y:l){
            sb.append(y.getClass()+",");
        }
        sb.append("]");
        return sb.toString();
    }
	
	public void cons0(){
		double c=1;
		List<func> l=getFree();
		//System.out.println("f="+f);
        //System.out.println("cl="+pr(f));
		for(int i=0;i<f.size();i++){		
			func p=f.get(i);
			//System.out.println("p="+p);
			if(p.isConstant()&&!p.cons().functional){
				c*=p.eval();
				//System.out.println("c="+c);
			}else{
				l.add(p);
			}
		}
        //System.out.println("l="+l.eval(0).getClass());
		if(c<0){
		    sign=-sign;
        }else if (c!=1){
		    l.add(new Constant(c));
        }
        //System.out.println(f);
		f=l;
	}
	
	
	public void mu(){
		List<func> l=getFree();
		//System.out.println("mu="+this);
		boolean b[]=new boolean[f.size()];
		for(int i=0;i<f.size();i++){
			func v=f.get(i).copy();
			if(!b[i]){
                func base;
				func pow;
				
                if(v.isPow()){
                    pow=v.b;
                    base=v.a;
                }else{
                    pow=Constant.ONE;
                    base=v;
                }
                //System.out.println("v="+base+" p="+pow);
				for(int j=i+1;j<f.size();j++){
					holder h=e3(base,f.get(j));
                    //System.out.println("h="+h.f);
					if(h.b){
						b[j]=true;
						pow=pow.add(h.f);
					}
				}
				//System.out.println("v2="+base+" p2="+pow);
				l.add(base.pow(pow));
			}
		}
		f=l;
		//System.out.println("f2="+f);
	}

	public static holder e3(func f1, func f2){
        //System.out.println("f1="+f1.getClass()+" f2="+f2.getClass()+" "+f1.eq(f2));
		if(f1.eq(f2)) return new holder(Constant.ONE,0,null,true);
        
		if(f2.isPow()){
            func p=Constant.ONE;
			p=f2.b;
			f2=f2.a;
            if (f1.eq(f2)){
                return new holder(p,0,null,true);
            }
		}
		return new holder(null,0,null,false);
	}
	
	@Override
	public boolean eq2(func f1)
	{
		return isEq(f,f1.f);
	}

    @Override
    public func substitude0(Variable v, func p)
    {
        List<func> l=getFree();
        for(func u:f){
            l.add(u.substitude0(v,p));
        }
        func m=new mul(l);
        return Config.mul.simplify?m.simplify():m;
    }

    @Override
    public func copy0() {
        return new mul(f);
    }
}
