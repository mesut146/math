package math.op;

import math.core.Constant;
import math.core.Variable;
import math.core.func;

import java.util.*;

public class add extends func
{

    @Override
    public String toLatex()
    {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<f.size();i++){
            sb.append(f.get(i).toLatex());
            if(i<f.size()-1){
                if(f.get(i+1).sign==-1){
                    sb.append("-");
                }else{
                    sb.append("+");
                }
                
            }
        }
        return sb.toString();
    }

    
    public add(func...f1){
		for(func f2:f1){
			f.add(f2);
		}
		type=types.add;
    }
	
	public add(List<func> f1){
		f=f1;
		type=types.add;
    }
	
    @Override
    public func get(Variable v, Constant c)
    {
        func t=Constant.ZERO;
		for(func f1:f){
			t=t.add(f1.get(v, c));
		}
		return t.s(sign);
    }

	@Override
	public double get2(Variable v, double d)
	{
		double s=0;
		for(func f1:f){
			s+=f1.get2(v,d);
		}
		return sign*s;
	}

    @Override
    public func derivative(Variable v)
    {
        func r=Constant.ZERO;
		for(func f1:f){
			r=r.add(f1.derivative(v));
		}
		return r;
    }

    @Override
    public func integrate(Variable v)
    {
        func r=Constant.ZERO;
        for(func f1:f){
            r=r.add(f1.integrate(v));
        }
		return r;
    }

    @Override
    public String toString2()
    {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<f.size();i++){
			sb.append(f.get(i));
			if(i<f.size()-1){
				if(f.get(i+1).sign==1){
					sb.append("+");
				}
				
			}
		}
		return sb.toString();
    }
    
    public func simplify(){
		List<func> l=getFree();
		for(func p:f){
			if(p.isAdd()){
				l.addAll(p.f);
			}else{
				l.add(p);
			}
		}
		f=l;
		cons0();
		//System.out.println("f="+f);
		mu();
		if(f.size()==1){
			return f.get(0);
		}
        return this;
    }
	
	public void cons0(){
		double c=0;
		List<func> l=getFree();
		//System.out.println("fa="+f);
		for(int i=0;i<f.size();i++){		
			func p=f.get(i);
			if(p.isConstant()&&!p.isConsfunc()){
				c+=p.get();
			}else{
				l.add(p);
			}
		}
		if (c!=0||(l.size()==c)) l.add(new Constant(c));
		f=l;
	}
	
	public void mu(){
		List<func> l=getFree();
		boolean b[]=new boolean[f.size()];

		for(int i=0;i<f.size();i++){
			func v=f.get(i);
			if(!b[i]){
				double k=1;
				for(int j=i+1;j<f.size();j++){
					holder q;
					if((q=e3(v,f.get(j))).b){
						b[j]=true;
						k+=q.d;
						v=q.f;
					}
				}
				func n=v.mul(k);
				
				//System.out.println("v="+v+" ,k="+k+" ,n="+n+" ,"+n.getType());
				//System.out.printf("v=%s k=%s n=%s\n",v,k,v.mul(k));
				l.add(n);
			}
		}
		f.clear();
		f=l;
	}
	
	@Override
	public boolean eq2(func f1)
	{
		return isEq(f,f1.f);
	}

	public static holder e3(func f1, func f2){
		if(f1.eq(f2)) return new holder(f1,1,null,true);
		
		if(!f1.isMul()){
			f1=new mul(f1,Constant.ONE);
		}
		if(!f2.isMul()){
			f2=new mul(f2,Constant.ONE);
		}
		holder o1=rm(f1);
		holder o2=rm(f2);
		
		if(isEq(o1.l,o2.l)){
			return new holder(new mul(o1.l),o1.d+o2.d-1,null,true);
		}
		
		return new holder(null,0,null,false);
	}
	
	public static holder rm(func t){
		double d1=1;
		List<func> l1=getFree();
		for(func p:t.f){
			if(p.isConstant()&&!p.cons().functional){
				d1*=p.get();
			}else{
				l1.add(p);
			}
		}
		return new holder(null,d1,l1,false);
	}

    @Override
    public func substitude0(Variable v, func p)
    {
        List<func> l=getFree();
        for(func u:f){
            l.add(u.substitude0(v,p));
        }
        return new add(l).simplify();
    }

    @Override
    public func copy0() {
        return new add(f);
    }
}