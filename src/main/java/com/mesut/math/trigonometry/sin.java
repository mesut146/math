package com.mesut.math.trigonometry;
import com.mesut.math.core.*;
import java.util.*;

public class sin extends func
{

    @Override
    public String toLatex()
    {
        return "sin("+a.toLatex()+")";
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
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
    public func get0(var[] v, cons[] c)
    {
        return new sin(a.get0(v, c),sign).simplify();
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*Math.sin(a.eval(v,d));
	}
    
    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(sign*Math.sin(a.evalc(v,d).decimal().doubleValue()));
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
    public func derivative(var v)
    {
        return new cos(a,sign).mul(a.derivative(v));
    }
    
    @Override
    public func integrate(var v)
    {
        if(a.eq(v)){
            return new cos(a,sign).negate();
        }
        return new Integral(this,v);
    }

    @Override
    public func substitude0(var v, func p)
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
			cons p=cons.PI;
			if(a.is(0)){
				return cons.ZERO;
			}
		}
		return this;//pi/2=1  pi/3=sqrt(3)/2
	}

    @Override
    public func getReal()
    {
        //sin(a)/2*(e^b-e^-b);
        func r=a.getReal();
        func im=a.getImaginary();
        return new sin(r).simplify().mul(new cosh(im));
    }

    @Override
    public func getImaginary()
    {
        //cos(a)/2*(e^b-e^-b);
        func r=a.getReal();
        func im=a.getImaginary();
        return new cos(r).simplify().mul(new sinh(im));
    }
     

	/*static HashMap<func,func> h=new HashMap<func,func>();
    static {
        h.put(parse("pi"),parse("0"));
        h.put(parse("pi/2"),parse("1"));
        h.put(parse("pi/3"),parse("sqrt(3)/2"));
        h.put(parse("pi/4"),parse("sqrt(2)/2"));
    }*/

	a equ(func a){
        cons k;
        a r=new a();
        if (a.isMul()&&a.f.size()==2){
            if(a.f.get(0).eq(cons.PI)&&a.f.get(1).isConstant()){

            }else if(a.f.get(1).eq(cons.PI)&&f.get(0).isConstant()){

            }

        }else {
            k=cons.ONE;
        }
        return r;
    }

	static class a{
        func f,g;
        boolean b=false;
    }
}
