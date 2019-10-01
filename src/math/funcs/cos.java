package math.funcs;
import math.core.*;
import java.util.*;

public class cos extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return "cos("+a.toLatex()+")";
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
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
			    /*if (Config.trigonomety.stay){

                }*/
				return new cons(eval());
			}
            
        }
        return this;
    }

    @Override
    public func copy0() {
        return new cos(a);
    }

    @Override
    public func get0(var[] v, cons[] c)
    {
        return new cos(a.get0(v, c),sign).simplify();
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*Math.cos(a.eval(v,d));
	}
    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(sign*Math.cos(a.evalc(v,d).decimal().doubleValue()));
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
    public func derivative(var v)
    {
        return new sin(a,-sign).mul(a.derivative(v));
    }

    @Override
    public func integrate(var v)
    {
        if(a.eq(v)){
            return new sin(a);
        }
        return new Integral(this,v);
    }

    @Override
    public func substitude0(var v, func p)
    {
        return new cos(a.substitude0(v,p)).simplify();
    }


    
    
}
