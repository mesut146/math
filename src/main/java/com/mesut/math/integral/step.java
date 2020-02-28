package com.mesut.math.integral;

import com.mesut.math.core.*;
import java.util.*;

public class step extends func
{

    public step(func p){
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
        double av=a.eval(v,d);
        if(av>=0){
            return 1;
        }
        return 0;
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
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
        return toString();
    }

    @Override
    public func derivative(var v)
    {
        return null;
    }

    @Override
    public func integrate(var v)
    {
        return null;
    }

    @Override
    public func copy0()
    {
        return new step(a);
    }

    @Override
    public String toString2()
    {
        return "u("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }

    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
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
        if(a.isConstant()){
            return evalc();
        }
        if(vars().size()==0){
            return evalc();
        }
        return this;
    }
    
    
}
