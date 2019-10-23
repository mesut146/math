package math.prime;

import math.core.*;
import java.util.*;

//number of primes less than p

public class pi extends func
{

    public pi(func p){
        //p>=1
        a=p;
        
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        a=a.get(v,c);
        return this;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        int x=(int) a.eval(v,d);
        int i=0;
        while(pset.p[i]<=x){
            i++;
        }
        return i;
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return new cons(eval(v,d));
    }

    @Override
    public func getReal()
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func getImaginary()
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func derivative(var v)
    {
        // TODO: Implement this method
        return null;
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
        return null;
    }

    @Override
    public String toString2()
    {
        return "pi("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }

    @Override
    public void vars0(Set<var> vars)
    {
        // TODO: Implement this method
        a.vars0(vars);
    }

    @Override
    public func substitude0(var v, func p)
    {
        a=a.substitude(v,p);
        return this;
    }
    
}
