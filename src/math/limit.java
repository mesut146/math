package math;

import math.core.cons;
import math.core.var;
import math.core.func;

public class limit extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }
    
    var v;
    cons c;

    public limit(func f, var v, cons c){
        this.a=f;
        this.v=v;
        this.c=c;
    }
    public limit(func f, var v, double c){
        this.a=f;
        this.v=v;
        this.c=new cons(c);
    }
    public limit(func f, String v, cons c){
        this.a=f;
        this.v=new var(v);
        this.c=c;
    }
    public limit(func f, String v, double c){
        this.a=f;
        this.v=new var(v);
        this.c=new cons(c);
    }
    @Override
    public func get(var[] v, cons[] c) {
        if(!this.c.isInf()){
            return a.get(this.v, this.c.val).get(v, c);
        }
        return null;
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
        return null;
    }


	
	

    @Override
    public func derivative(var v) {
        return null;
    }

    @Override
    public func integrate(var v) {
        return null;
    }

    @Override
    public String toString2() {
        return "lim("+v+"="+c+","+a+")";
    }

    @Override
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public func substitude0(var v, func p) {
        return null;
    }

    @Override
    public func copy0() {
        return new limit(a,v,c);
    }
}
