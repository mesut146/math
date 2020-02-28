package com.mesut.math.trigonometry;
import com.mesut.math.core.*;
import java.util.*;

public class acos extends func
{

    public acos(func p){
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
        return sign*Math.acos(a.eval(v,d));
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
        func f1=a.derivative(v).negate();
        func f2=cons.ONE.sub(a.pow(2)).sqrt();
        return f1.div(f2);
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
        return new acos(a);
    }

    @Override
    public String toString2()
    {
        return "acos("+a+")";
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
    
}
