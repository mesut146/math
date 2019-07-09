package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class sin extends func
{

    @Override
    public String toLatex()
    {
        return "sin("+a.toLatex()+")";
    }

    
    public sin(func f){
        this(f,1);
    }
    public sin(func f,int s){
        type=types.sin;
        a=f;
        sign=s*f.sign;
		f.sign=1;
		fx=true;
		//alter.add(parse("1-cos("+f+")^2"));
    }

    @Override
    public func get(Variable[] v, Constant[] c)
    {
        return new sin(a.get(v, c),sign).simplify();
    }

	@Override
	public double eval(Variable[] v, double[] d)
	{
		return sign*Math.sin(a.eval(v,d));
	}

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }

    @Override
    public String toString2()
    {
        return "sin("+a+")";
    }

    @Override
    public func derivative(Variable v)
    {
        return new cos(a,sign).mul(a.derivative(v));
    }
    
    @Override
    public func integrate(Variable v)
    {
        if(a.eq(v)){
            return new cos(a,sign).negate();
        }
        return new Anti(this);
    }

    @Override
    public func substitude0(Variable v, func p)
    {
        return new sin(a.substitude0(v,p),sign);
    }

    @Override
    public func copy0() {
        return new sin(a);
    }

	@Override
	public func simplify()
	{
	    //System.out.println("sin simp "+a);
		if(a.isConstant()){
			Constant p=Constant.PI;
			if(a.is(0)){
				return Constant.ZERO;
			}
		}
		return this;//pi/2=1  pi/3=sqrt(3)/2
	}

	/*static HashMap<func,func> h=new HashMap<func,func>();
    static {
        h.put(parse("pi"),parse("0"));
        h.put(parse("pi/2"),parse("1"));
        h.put(parse("pi/3"),parse("sqrt(3)/2"));
        h.put(parse("pi/4"),parse("sqrt(2)/2"));
    }*/

	a equ(func a){
        Constant k;
        a r=new a();
        if (a.isMul()&&a.f.size()==2){
            if(a.f.get(0).eq(Constant.PI)&&a.f.get(1).isConstant()){

            }else if(a.f.get(1).eq(Constant.PI)&&f.get(0).isConstant()){

            }

        }else {
            k=Constant.ONE;
        }
        return r;
    }

	static class a{
        func f,g;
        boolean b=false;
    }
}
