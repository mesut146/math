package com.mesut.math.prime;
import java.util.*;
import com.mesut.math.*;
import com.mesut.math.core.*;

//n th prime p(n)
public class prime extends func
{
    
    public prime(Object n){
        a=Util.cast(n);
    }

    @Override
    public func getReal()
    {
        return null;
    }

    @Override
    public func getImaginary()
    {
        return null;
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        a=a.get0(v,c);
        return this;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        return pset.get((int)a.eval(v,d));
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(eval(v,d));
    }

    @Override
    public String toLatex()
    {
        return toString();
    }

    @Override
    public func derivative(var v)
    {
        if(a.vars().contains(v)){
            return a.derivative(v).mul(new fx("p(n)'"));
        }
        return cons.ZERO;
    }

    @Override
    public func integrate(var v)
    {
        return null;
    }

    @Override
    public func copy0()
    {
        return new prime(a);
    }

    @Override
    public String toString2()
    {
        return "prime("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        return false;
    }

    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
    }

    @Override
    public func substitude0(var v, func p)
    {
        a=a.substitude0(v,p);
        return this;
    }
    
}
