package com.mesut.math.core;

import com.mesut.math.*;

import java.util.*;

public class Integral extends func
{
    public var dv;
    public func a1,a2;

    //f(x),dv
    public Integral(Object f,Object v){
        this.a=Util.cast(f);
        this.dv=Util.var(v);
    }

    //f(x) dx
    public Integral(Object f){
        this.a=Util.cast(f);
        this.dv=var.x;
    }
    //f(x),dv,lower,upper
    public Integral(Object f,Object v,Object i1,Object i2){
        this.a=Util.cast(f);
        this.dv=Util.var(v);
        this.a1=Util.cast(i1);
        this.a2=Util.cast(i2);
        //lim=true;
    }
    public Integral(){

    }
    
    public boolean lim(){
        return a1!=null&&a2!=null;
    }
    @Override
    public func getReal()
    {
        if(lim()){
            if(a1.isReal()&&a2.isReal()){
                return sign(new Integral(a.getReal(),dv,a1,a2));
            }
            
        }
        return sign(new Integral(a.getReal(),dv));
    }

    @Override
    public func getImaginary()
    {
        if(lim()){
            if(a1.isReal()&&a2.isReal()){
                return sign(new Integral(a.getImaginary(),dv,a1,a2));
            }

        }
        return sign(new Integral(a.getImaginary(),dv));
    }

    @Override
    public String toLatex()
    {
        StringBuilder s=new StringBuilder("\\int");
        if(lim()){
            s.append(String.format("_{%s}^{%s}",a1,a2,a,dv));
        }
        s.append(String.format("%s d%s",a1,a2,a,dv));
        return s.toString();
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        if(a!=null){
            a.vars0(vars);
        }
        if(lim()){//if improper then remove dummy var
            vars.remove(dv);
            a1.vars0(vars);
            a2.vars0(vars);
        }
    }
    
    public boolean isLimCons(){
        return lim()&&a1.isConstant()&&a2.isConstant();
    }
    
    //f=f(x) v=preferred var
    public void setDummy(func f,var v){
        List<var> l=f.vars();
        if(l.contains(v)){
            dv=makeDummy(l);
        }else{
            dv=v;
        }
    }
    
    public var makeDummy(List<var> l){
        List<String> pref=Arrays.asList("x","t","u","y","u","w");
        List<String> str=new ArrayList<>();
        for(var v:l){
            str.add(v.name);
        }
        
        if(str.containsAll(pref)){
            for(char i='a';i<='z';i++){
                if(i!='d'&&!str.contains(String.valueOf(i))){
                    return new var(i);
                }
            }
            return new var("x0");
        }else{
            for(String sv:pref){
                if(!str.contains(sv)){
                    return new var(sv);
                }
            }
        }
        return var.x;
    }
    
    public Integral make(){
        Integral i=new Integral();
        
        return i;
    }
    
	@Override
	public func get0(var[] v, cons[] c)
	{
        Integral i=(Integral)copy();
        if(lim()){
            i.a1=i.a1.get0(v,c);
            i.a2=i.a2.get0(v,c);
        }
        i.a=i.a.get0(v,c);
		return i;
	}

	@Override
	public double eval(var[] v, double[] d)
	{
        a=a.get(v,d);
        System.out.println("int="+a+" dv="+dv);
		return riemannSum();
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(eval(v,d));
    }

    double riemannSum(){
        assert a1.isConstant()&&a2.isConstant();
        double sum=0;
        double k=Config.integral.interval;
        double low=a1.eval();
        double dx=(a2.eval() - low) / k;
        //a+n*dx
        //func fx=new add(new cons(a), new mul(new var("n"), new cons(dx))).simplify();
        //System.out.println(fx);

        for(int i=0;i<=k;i++){
            double p=low+i*dx;
            sum=sum+a.eval(dv,p)*dx;
            System.out.println(sum);
            if((k/10)%i==0)System.out.println(sum);
        }
        return sum;
    }

	@Override
	public func derivative(var v)
	{
		if(dv.eq(v)&&!lim()){//not always
			return signf(a);
		}
		if(lim()){
            List<var> l1=a1.vars();
            List<var> l2=a2.vars();
            List<var> l3=a.vars();
            boolean b1=l1.contains(v);
            boolean b2=l2.contains(v);
            if(b1||b2){
                if(l3.contains(v)){
                    
                }else{//fubuni's theorem
                    func ls=a.substitude(dv,a2).mul(a2.derivative(v));
                    func rs=a.substitude(dv,a1).mul(a1.derivative(v));
                    return ls.sub(rs);
                }
            }
            /*if(b2){
                return a.substitude(dv,a2).mul(a2.derivative(v));
            }
            if(b1){
                return a.substitude(dv,a1).mul(a1.derivative(v)).negate();
            }*/
			return new Integral(a.derivative(v),this.dv,a1,a2);
		}
		return new Integral(a.derivative(v),this.dv);
	}

	@Override
	public func integrate(var v)
	{
		if(!this.dv.eq(v)){
			if(lim()){
				return new Integral(a.integrate(v),this.dv,a1,a2);
			}
			return new Integral(a.integrate(v),this.dv);
		}
		return null;
	}

	@Override
	public func copy0()
	{
		if(lim()){
			return new Integral(a,dv,a1,a2);
		}
		return new Integral(a,dv);
	}

	@Override
	public String toString2()
	{
		if(lim()){
			return String.format("Integral{%s d%s,%s,%s}",a,dv,a1,a2);
		}
		return String.format("Integral{%s d%s}",a,dv);
	}

	@Override
	public boolean eq2(func f)
	{
        Integral i=(Integral) f;
        if(lim()==i.lim()){
            if(lim()){
                return a.eq(i.a)&&dv.eq(i.dv)&&a1.eq(i.a1)&&a2.eq(i.a2);
            }else{
                return a.eq(i.a)&&dv.eq(i.dv);
            }
            
        }
		return false;
	}

	@Override
	public func substitude0(var v,func p)
	{
        a=a.substitude(v,p);
        a1=a1.substitude(v,p);
        a2=a2.substitude(v,p);
        return this;
		//return new Integral(a.substitude0(v,p),this.v);
	}

    @Override
    public func simplify()
    {
        if(lim()){
            
        }
        
        return this;
    }
    
    

}
