package com.mesut.math.core;

import java.util.*;

//placeholder for user defined functions
public class fx extends var
{
	String name;
	public static List<fx> ins=new ArrayList<>();//previously created funcs
    public static Map<func,func> table=new HashMap<>();
    
	public fx(){
		this("f");
	}

	public fx(String name){
		this(name, var.x);
	}

	public fx(String name,func content){
        super("var_fx_"+name);
		this.name =name;
		a=content;
        //type=types.fx;
		ins.add(this);
	}public fx(String name,func... f){
        super("var_fx_"+name);
        this.name =name;
        Collections.addAll(this.f,f);
        //type=types.fx;
        ins.add(this);
	}
    
    public static boolean has(String name){
        for(fx f:ins){
            if(f.name.equals(name)){
                return true;
            }
        }
        return false;
    }
    public static fx getFx(String name){
        for(fx f:ins){
            if(f.name.equals(name)){
                return f;
            }
        }
        return null;
    }
	@Override
	public func get0(var[] v, cons[] c)
	{
		a=a.get0(v,c);
        if(a.isConstant()){
            return new cons(this);
        }
		return this;
	}

	@Override
	public double eval(var[] v, double[] d)
	{
		//illegal
		return 0;
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        //illegal
        return cons.ZERO;
    }
    
	@Override
	public func derivative(var v)
	{
		// TODO: Implement this method
        if(a.eq(v)){
            String nm=name+"'";
            return new fx(nm,a);
        }
		return mul(a.derivative(v));
	}

	@Override
	public func integrate(var v)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public func copy0()
	{
		// TODO: Implement this method
        //System.out.println("copied="+this);
		//return new fx(name,a);
        return this;
	}

	@Override
	public String toString2()
	{
		// TODO: Implement this method
        return String.format("%s(%s)",name,a);
		//return name+"("+a+")="+b;
	}
    
    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return toString();
    }

	@Override
	public boolean eq2(func f)
	{
		return name.equals(((fx)f).name)&&a.eq(f.a);
	}

	@Override
	public func substitude0(var v, func p)
	{
        a=a.substitude(v,p);
		return this;
	}

	@Override
	public func simplify()
	{
		if(ins.indexOf(this)!=-1){
			
		}
        //System.out.println("in simp="+this);
		return this;
	}
	
	
	
}
