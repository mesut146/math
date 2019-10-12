package math.funcs;
import math.core.*;
import java.util.*;
import math.*;

public class inv extends func
{

    //inverse of p
    public inv(func p){
        a=p;
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
        // TODO: Implement this method
        return 0;
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
        return cons.ONE.div(a.derivative(v));
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
        return new inv(a.copy());
    }

    @Override
    public String toString2()
    {
        // TODO: Implement this method
        return "inv("+a+")";
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
    public taylor taylor()
    {
        taylor t=new taylor();
        
        return t;
    }
    
    
    
}
