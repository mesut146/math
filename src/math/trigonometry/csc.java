package math.trigonometry;
import math.core.cons;
import math.core.var;
import math.core.func;
import java.util.*;

public class csc extends func
{
    
    @Override
    public func getReal()
    {
        return alter.get(0).getReal();
    }

    @Override
    public func getImaginary()
    {
        return alter.get(0).getImaginary();
    }


    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return toString();
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
    }

    public csc(func f){
        this.a=f;
        alter.add(div(new sin(f)).inv());
		fx=true;
    }

    @Override
    public func get0(var[] v, cons[] c) {
        return signf(alter.get(0).get0(v, c));
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*1.0/Math.sin(a.eval(v,d));
	}
    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(eval(v,d));
    }
	
    @Override
    public func derivative(var v) {
        return sign(alter.get(0).derivative(v));
    }

    @Override
    public func integrate(var v) {
        return null;
    }

    @Override
    public String toString2() {
        return "csc("+a+")";
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitude0(var v, func p) {
        a=a.substitude(v,p);
        return this;
    }

    @Override
    public func copy0() {
        return new csc(a);
    }
}