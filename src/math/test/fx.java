package math.test;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

import java.util.*;

public class fx extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

	String name;
	static List<fx> ins=new ArrayList<>();
    
	public fx(){
		this("f");
	}
	public fx(String s){
		this(s, Variable.x);
	}
	public fx(String s,func f){
		name=s;
		a=f;
		ins.add(this);
	}
    public static boolean has(String n){
        for(fx f:ins){
            if(f.name.equals(n)){
                return true;
            }
        }
        return false;
    }
    public static fx get(String n){
        for(fx f:ins){
            if(f.name.equals(n)){
                return f;
            }
        }
        return null;
    }
	@Override
	public func get(Variable v, Constant c)
	{
		// TODO: Implement this method
		return null;
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
		// TODO: Implement this method
		return null;
	}

	@Override
	public func integrate(Variable v)
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
		return name+"("+a+")="+b;
	}

	@Override
	public boolean eq2(func f)
	{
		return name.equals(((fx)f).name)&&a.eq(f.a);
	}

	@Override
	public func substitude0(Variable v, func p)
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
