package math;

import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class limit extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }
    
    Variable v;
    Constant c;

    public limit(func f, Variable v, Constant c){
        this.a=f;
        this.v=v;
        this.c=c;
    }
    public limit(func f, Variable v, double c){
        this.a=f;
        this.v=v;
        this.c=new Constant(c);
    }
    public limit(func f, String v, Constant c){
        this.a=f;
        this.v=new Variable(v);
        this.c=c;
    }
    public limit(func f, String v, double c){
        this.a=f;
        this.v=new Variable(v);
        this.c=new Constant(c);
    }
    @Override
    public func get(Variable v, Constant c) {
        if(!this.c.isInf()){
            return a.get(this.v, this.c.val).get(v, c);
        }
        return null;
    }

	@Override
	public double get2(Variable v, double d)
	{
		// TODO: Implement this method
		return 0;
	}

	
	

    @Override
    public func derivative(Variable v) {
        return null;
    }

    @Override
    public func integrate(Variable v) {
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
    public func substitude0(Variable v, func p) {
        return null;
    }

    @Override
    public func copy0() {
        return new limit(a,v,c);
    }
}
