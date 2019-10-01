package math.core;

import math.*;
import math.core.*;
import java.util.*;

public class Integral extends func
{

    @Override
    public String toLatex()
    {
        StringBuilder s=new StringBuilder("\\int");
        if(lim){
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
        if(lim){//if improper then remove dummy var
            vars.remove(dv);
        }
    }
    
	boolean lim=false;
	public var dv;
	//Constant a1,a2;
    func a1,a2;
    //boolean bound=false;
	
	//f(x),dx,lower,upper
	public Integral(func f,var v,func i1,func i2){
		this.a=f;
		this.dv=v;
		this.a1=i1;
		this.a2=i2;
		this.lim=true;
	}
    //f(x),dx
	public Integral(func f,var v){
		this.a=f;
		this.dv= v;
	}
	
	public Integral(func f){
		this.a=f;
		this.dv=var.x;
	}
    
    public boolean isLimCons(){
        return lim&&a1.isConstant()&&a2.isConstant();
    }
    
	@Override
	public func get0(var[] v, cons[] c)
	{
        Integral i=(Integral)copy();
        if(lim){
            i.a1=i.a1.get0(v,c);
            i.a2=i.a2.get0(v,c);
        }
        i.a=i.a.get0(v,c);
		return i;
	}

	@Override
	public double eval(var[] v, double[] d)
	{
		// TODO: Implement this method
        a=a.get(v,d);
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
		if(this.dv.eq(v)){
			return a;
		}
		if(lim){
			return new Integral(a.derivative(v),this.dv,a1,a2);
		}
		return new Integral(a.derivative(v),this.dv);
	}

	@Override
	public func integrate(var v)
	{
		if(!this.dv.eq(v)){
			if(lim){
				return new Integral(a.integrate(v),this.dv,a1,a2);
			}
			return new Integral(a.integrate(v),this.dv);
		}
		return null;
	}

	@Override
	public func copy0()
	{
		if(lim){
			return new Integral(a,dv,a1,a2);
		}
		return new Integral(a,dv);
	}

	@Override
	public String toString2()
	{
		if(lim){
			return String.format("Integral{%s d%s,%s,%s}",a,dv,a1,a2);
		}
		return String.format("Integral{%s d%s}",a,dv);
	}

	@Override
	public boolean eq2(func f)
	{
        Integral i=(Integral) f;
        if(lim==i.lim){
            if(lim){
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
        if(lim){
            
        }
        
        return this;
    }
    
    

}
