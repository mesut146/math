package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

public class gamma extends Integral
{
	Variable v;
	
	public gamma(Variable v){
		super(func.parse("t^x*e^(-t)".replace("x",v.toString())),Variable.t, Constant.ZERO,Constant.INF);
		this.v=v;
	}

	@Override
	public func get(Variable v, Constant c)
	{
		// TODO: Implement this method
		return super.get(v, c);
	}
	
	
}