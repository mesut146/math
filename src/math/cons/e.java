package math.cons;
import math.core.cons;
import math.core.var;
import math.core.func;

public class e extends cons
{
	public e(){
		functional=true;
		val=Math.E;
		ff=this;
	}

	@Override
	public String toLatex() {
		return "e";
	}

	@Override
	public double eval(var[] v, double[] d) {
		return val;
	}

	@Override
    public func copy0()
    {
        return new e();
    }

	@Override
	public String toString2()
	{
		return "e";
	}
	
	
}
