package math;

import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class sigma extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

    int start,end;
    func fx;
    Variable var;

    public sigma(func f, Variable v, int s, int e){
        this.fx =f;
        this.var =v;
        this.start=s;
        this.end=e;
    }
    public sigma(func f, String v, int s, int e){
        this(f,new Variable(v),s,e);
    }

    public func sum(){
        func d= Constant.ZERO;
		System.out.println("f="+ fx);
		//System.out.println(fx.get(var,1));
        for (int i=start;i<=end;i++){
            //System.out.println(fx.get(i));
            d=d.add(fx.get(var,i));
			//System.out.println(d);
        }
        return d;
    }

    public double get(){
        double d=0;
        for (int i=start;i<=end;i++){
            d+= fx.get2(var,i);
			//System.out.println(d);
        }
        return d;
    }
    @Override
    public func get(Variable v, Constant c) {
        if(this.var.eq2(v)){

        }
        return this;
    }

	@Override
	public double get2(Variable v, double d)
	{
		// TODO: Implement this method
		return 0;
	}

	
	

    @Override
    public func derivative(Variable v) {
        return new sigma(fx.derivative(),this.var,start,end);
    }

    @Override
    public func integrate(Variable v) {
        return new sigma(fx.integrate(v),v,start,end);
    }

    @Override
    public String toString2() {
        return "sum("+ var +"="+start+" to "+end+",fx="+ fx +")";
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
        return new sigma(fx, var,start,end);
    }
}
