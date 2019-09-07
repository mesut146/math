package math.funcs;
import math.core.*;

public class li extends func
{

    var dx;
    public li(func f){
        dx=new var("t");
        a=func.parse(String.format("1/ln(%s)",dx));
        b=f;//upper limit
    }
    
    @Override
    public func get(var[] v, cons[] c)
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
