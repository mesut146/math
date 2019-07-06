package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class cos extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

    
    public cos(func f){
        this(f,1);
    }
    public cos(func f,int s){
        type=types.cos;
		f.sign=1;
        a=f;
        sign=s;
		fx=true;
		//alter.add(parse("1-sin("+f+")^2"));
    }

    @Override
    public func simplify() {
        if (a.isConstant()){
			if(!a.cons().functional){
				return new Constant(Math.cos(a.eval()));
			}
            
        }
        return this;
    }

    @Override
    public func copy0() {
        return new cos(a);
    }

    @Override
    public func get(Variable v, Constant c)
    {
        return new cos(a.get(v, c),sign).simplify();
    }

	@Override
	public double eval(Variable v, double d)
	{
		return sign*Math.cos(a.eval(v,d));
	}


    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }

    @Override
    public String toString2()
    {
        return "cos("+a+")";
    }

    @Override
    public func derivative(Variable v)
    {
        return new sin(a,-sign).mul(a.derivative(v));
    }

    @Override
    public func integrate(Variable v)
    {
        if(a.eq(v)){
            return new sin(a);
        }
        return new Anti(this);
    }

    @Override
    public func substitude0(Variable v, func p)
    {
        return new cos(a.substitude0(v,p)).simplify();
    }


    
    
}
