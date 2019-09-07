package math;

import math.core.cons;
import math.core.var;
import math.core.func;

public class Integral extends func
{

    @Override
    public String toLatex()
    {
        StringBuilder s=new StringBuilder("\\int");
        if(lim){
            s.append(String.format("_{%s}^{%s}",a1,a2,a,v));
        }
        s.append(String.format("%s d%s",a1,a2,a,v));
        return s.toString();
    }
    
	boolean lim=false;
	var v;
	//Constant a1,a2;
    func a1,a2;
    //boolean bound=false;
	
	//f(x),dx,lower,upper
	public Integral(func f,var v,func i1,func i2){
		a=f;
		this.v=v;
		a1=i1;
		a2=i2;
		lim=true;
	}
    //f(x),dx
	public Integral(func f,var v){
		a=f;
		this.v= v;
	}
	
	public Integral(func f){
		a=f;
		this.v=var.x;
	}

	@Override
	public func get(var[] v, cons[] c)
	{
        Integral i=(Integral)copy();
        if(lim){
            i.a1=i.a1.get(v,c);
            i.a2=i.a2.get(v,c);
        }
        i.a=i.a.get(v,c);
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
            sum=sum+eval(v,p)*dx;
            //if((k/10)%i==0)System.out.println(sum);
        }
        return sum;
    }

	@Override
	public func derivative(var v)
	{
		if(this.v.eq(v)){
			return a;
		}
		if(lim){
			return new Integral(a.derivative(v),this.v,a1,a2);
		}
		return new Integral(a.derivative(v),this.v);
	}

	@Override
	public func integrate(var v)
	{
		if(!this.v.eq(v)){
			if(lim){
				return new Integral(a.integrate(v),this.v,a1,a2);
			}
			return new Integral(a.integrate(v),this.v);
		}
		return null;
	}

	@Override
	public func copy0()
	{
		if(lim){
			return new Integral(a,v,a1,a2);
		}
		return new Integral(a,v);
	}

	@Override
	public String toString2()
	{
		if(lim){
			return String.format("Integral{%s d%s,%s,%s}",a,v,a1,a2);
		}
		return String.format("Integral{%s d%s}",a,v);
	}

	@Override
	public boolean eq2(func f)
	{
        Integral i=(Integral) f;
        if(lim==i.lim){
            if(lim){
                return a.eq(i.a)&&v.eq(i.v)&&a1.eq(i.a1)&&a2.eq(i.a2);
            }else{
                return a.eq(i.a)&&v.eq(i.v);
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
