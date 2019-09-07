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
	
	//f(fx),dx,lower,upper
	public Integral(func f,func v,func i1,func i2){
		a=f;
		this.v=(var) v;
		a1=i1;
		a2=i2;
		lim=true;
	}
	public Integral(func f,func v){
		a=f;
		this.v=(var) v;
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
		return 0;
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return null;
    }

    

	@Override
	public func derivative(var v)
	{
		if(this.v.eq(v)){
			return a.copy();
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
		return a.eq(f.derivative(v));
	}

	@Override
	public func substitude0(var v,func p)
	{
        a=a.substitude(v,p);
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
