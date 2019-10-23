package math.trigonometry;
import math.core.cons;
import math.core.var;
import math.core.func;
import java.util.*;

public class tan extends func
{
    static{
        register("tan",tan.class);
    }
    
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

    public tan(func f){
        this.a=f;
        alter.add(new sin(f).div(new cos(f)));
		fx=true;
    }

    @Override
    public func get0(var[] v, cons[] c) {
        return signto(alter.get(0).get0(v, c));
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		return sign*Math.tan(a.eval(v,d));
	}
    @Override
    public cons evalc(var[] v, double[] d)
    {
        return new cons(sign*Math.tan(a.evalc(v,d).decimal().doubleValue()));
    }
	
    @Override
    public func derivative(var v) {
        return alter.get(0).derivative(v);
    }

    @Override
    public func integrate(var v) {
        return null;
    }

    @Override
    public String toString2() {
        return "tan("+a+")";
    }

    @Override
    public boolean eq2(func f) {
        return a.eq(f.a);
    }

    @Override
    public func substitude0(var v, func p) {
        return null;
    }

    @Override
    public func copy0() {
        return new tan(a);
    }
}
