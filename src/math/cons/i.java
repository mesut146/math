package math.cons;

import math.core.Constant;
import math.core.Variable;
import math.core.func;
import math.funcs.*;

public class i extends Constant
{
	public static Constant i;
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
				return Constant.ONE;
			}else if(f.is(1)){
				return i;
			}
		}
		return cis(f.mul(Constant.PI.div(2)));
	}

	@Override
	public func pow(double d)
	{
		return pow(new Constant(d));
	}

	static func cis(func f){
		return new cos(f).add(i.mul(new sin(f).simplify()));
	}

	@Override
	public func get(Variable[] v, Constant[] c)
	{
		return null;
	}
	
	
}
