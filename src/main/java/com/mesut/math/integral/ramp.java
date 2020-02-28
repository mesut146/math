package com.mesut.math.integral;
import com.mesut.math.core.*;
import java.util.*;

public class ramp extends func
{

    public ramp(func f){
        a=f;
        alter.add(f.mul(new step(f)));
        alter.add(new Integral(new step(var.t),var.t,cons.MINF,f));
        //laplace(ramp(t))=1/s^2
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        return new ramp(a.get(v,c));
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        double x;
        if((x=a.eval(v,d))>=0){
            return x;
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
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func derivative(var v)
    {
        return new step(a).mul(a.derivative(v));
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
        return new ramp(a);
    }

    @Override
    public String toString2()
    {
        // TODO: Implement this method
        return "ramp("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        // TODO: Implement this method
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
        // TODO: Implement this method
        a=a.substitude(v,p);
        return this;
    }
    
}
