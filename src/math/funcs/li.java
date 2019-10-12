package math.funcs;
import math.core.*;
import java.util.*;

public class li extends func
{
    static{
        register("li",li.class);
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


    var dx;
    public li(func f){
        dx=new var("t");
        a=func.parse(String.format("1/ln(%s)",dx));
        b=f;//upper limit
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        // TODO: Implement this method
        return a.integrate(2,b.eval(v,d),dx);
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return new cons(eval(v,d));
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
        a=a.derivative(v);
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
        return null;
    }

    @Override
    public String toString2()
    {
        // TODO: Implement this method
        return "li("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        // TODO: Implement this method
        return a.eq(f.a);
    }

    @Override
    public func substitude0(var v, func p)
    {
        // TODO: Implement this method
        return null;
    }
    
}
