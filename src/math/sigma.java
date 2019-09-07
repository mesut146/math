package math;

import math.core.cons;
import math.core.var;
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
    var var;

    public sigma(func f, var v, int s, int e){
        this.fx =f;
        this.var =v;
        this.start=s;
        this.end=e;
    }
    public sigma(func f, String v, int s, int e){
        this(f,new var(v),s,e);
    }

    public func sum(){
        func d= cons.ZERO;
		System.out.println("f="+ fx);
		//System.out.println(fx.eval(var,1));
        for (int i=start;i<=end;i++){
            //System.out.println(fx.eval(i));
            d=d.add(fx.get(var,i));
			//System.out.println(d);
        }
        return d;
    }

    public double eval(){
        double d=0;
        //System.out.println("fx="+fx);
        for (int i=start;i<=end;i++){
            d+= fx.eval(var,i);
			//System.out.println(d);
        }
        return d;
    }
    @Override
    public func get(var[] v, cons[] c) {
        /*if(this.var.eq2(v)){

        }*/
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
        return null;
    }


	
	

    @Override
    public func derivative(var v) {
        return new sigma(fx.derivative(),this.var,start,end);
    }

    @Override
    public func integrate(var v) {
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
    public func substitude0(var v, func p) {
        return null;
    }

    @Override
    public func copy0() {
        return new sigma(fx, var,start,end);
    }
}
