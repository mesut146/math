package math.core;

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
	Variable v;
	//Constant a1,a2;
    func a1,a2;
    //boolean bound=false;
	
	public Integral(func f,func v,func i1,func i2){
		a=f;
		this.v=(Variable) v;
		a1=i1;
		a2=i2;
		lim=true;
	}
	public Integral(func f,func v){
		a=f;
		this.v=(Variable) v;
	}
	
	public Integral(func f){
		a=f;
		this.v=Variable.x;
	}

	@Override
	public func get(Variable v, Constant c)
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
	public double get2(Variable v, double d)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public func derivative(Variable v)
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
	public func integrate(Variable v)
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
			return String.format("I{%s d%s,%s,%s}",a,v,a1,a2);
		}
		return String.format("I{%s d%s}",a,v);
	}

	@Override
	public boolean eq2(func f)
	{
		return a.eq(f.derivative(v));
	}

	@Override
	public func substitude0(Variable v,func p)
	{
		return new Integral(a.substitude0(v,p),this.v);
	}

    @Override
    public func simplify()
    {
        if(lim){
            
        }
        
        return this;
    }
    
    

}
