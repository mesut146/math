package math.core;

import math.*;
import math.core.*;
import java.util.*;

public class sigma extends func
{

    func start,end;//var or cons
    public func fx;
    var var;

    public sigma(Object f,Object v,Object s,Object e){
        fx=type(f);
        var=(var)type(v);
        start=type(s);
        end=type(e);
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        fx.vars0(vars);
        start.vars0(vars);
        end.vars0(vars);
    }
    
    func type(Object o){
        if(o instanceof var){
            return (var)o;
        }else if(o instanceof String){
            return parse((String)o);
        }else if(o instanceof Integer){
            return new cons((Integer)o);
        }else if(o instanceof func){
            return (func)o;
        }
        else{
            throw new RuntimeException("unexpected type: "+o.getClass()+" ,"+o);
        }
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        /*if(this.var.eq2(v)){

         }*/
        return this;
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		func s=start.get(v,d);
        func e=end.get(v,d);
        //sigma(x^n/n^2,n=1 to k) get(x=2,k=33)
        
        if(!isInt(s)){
            throw new RuntimeException("starting index must be an integer");
        }
        if(!isInt(e)){
            throw new RuntimeException("ending index must be an integer");
        }
        int si=(int) s.eval();
        int ei=(int) e.eval();
        
        func fx2=fx.get(v,d);
        double precision=Math.pow(10,-Config.digits);
        double sum=fx2.eval(var,si),last=0;
        
        for(int i=si+1;i<=ei;i++){
            sum+=fx2.eval(var,i);
            //System.out.println(fx2.eval(var,i));
            //System.out.println(sum);
            //1.6449340668
            //1.6449240668982262
            /*if(Math.abs(sum-last)<=precision||i==Config.maxIteration){
                System.out.println("asd");
                return sum;
            }*/
            last=sum;
        }
		return sum;
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
        return new sigma(fx.derivative(), this.var, start, end);
    }

    @Override
    public func integrate(var v)
    {
        return new sigma(fx.integrate(v), v, start, end);
    }
    
    boolean isInt(func f){
        if(f.isConstant()){
            if(f.eq(cons.INF)){
                return true;
            }
            double d=f.eval();
            return d==(long)d;
        }
        return false;
    }

    @Override
    public String toString2()
    {
        return String.format("sum(%s,%s=%s to %s)",fx,var,start,end);
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
        return false;
    }

    @Override
    public func substitude0(var v, func p)
    {
        fx=fx.substitude(v,p);
        return this;
    }

    @Override
    public func copy0()
    {
        return new sigma(fx, var, start, end);
    }
}
