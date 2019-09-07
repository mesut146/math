package math.cons;

import math.core.cons;
import math.core.var;
import math.core.func;
import math.funcs.*;

public class i extends cons
{
	public static cons i;
	static{
		i=new i();
	}
	
	public i(){
		functional=true;
		ff=this;
	}
	
	@Override
	public String toString2()
	{
		return "i";
	}

	@Override
	public func copy0()
	{
		return new i();
	}

	@Override
	public boolean eq2(func f)
	{
		if(getClass()==f.getClass()){
			return true;
		}
		return false;
	}

	@Override
	public func pow(func f)
	{
		if(f.isConstant()){
			if(f.is(0)){
				return cons.ONE;
			}else if(f.is(1)){
				return this;
			}
		}
		return cis(f.mul(cons.PI.div(2)));
	}

	@Override
	public func pow(double d)
	{
		return pow(new cons(d));
	}

	static func cis(func f){
		return new cos(f).add(i.mul(new sin(f).simplify()));
	}

	@Override
	public func get(var[] v, cons[] c)
	{
		return this;
	}

    @Override
    public double eval(var[] v, double[] d)
    {
        System.out.println("tried to eval i");
        return 0;
    }
	
	
}
