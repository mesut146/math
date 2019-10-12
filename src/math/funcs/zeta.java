package math.funcs;

import math.core.*;
import math.*;
import java.util.*;

public class zeta extends func
{
    static{
        register("zeta",zeta.class);
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

    func s;
    public sigma sum;
    public Integral i;
    public gamma g;
    
    public zeta(Object o){
        s= Util.cast(o);
        sum=new sigma(var.n.pow(s.negate()),var.n,1,cons.INF);
        i=new Integral(var.u.pow(s.sub(1)).div(cons.E.pow(var.u).sub(1)),var.u,cons.ZERO,cons.INF);
        g=new gamma(s);
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        s.vars0(vars);
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        s=s.get(v,c);
        return this;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        // TODO: Implement this method
        return sign*sum.eval(v,d);
    }

    @Override
    public cons evalc(var[] v, double[] d)
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
        sum=(sigma) sum.derivative(v);
        return this;
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
        return new zeta(s);
    }

    @Override
    public String toString2()
    {
        // TODO: Implement this method
        return "zeta("+s+")";
    }

    @Override
    public boolean eq2(func f)
    {
        // TODO: Implement this method
        return false;
    }

    @Override
    public func substitude0(var v, func p)
    {
        // TODO: Implement this method
        return null;
    }
    
    
}
