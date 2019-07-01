package math.cons;
import math.core.Constant;
import math.core.func;

public class pi extends Constant
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

	public pi(){
		functional=true;
		val=Math.PI;
		ff=this;
	}

	@Override
	public String toString2()
	{
		return "pi";
	}

	@Override
	public boolean eq2(func f)
	{
		return f.getClass()==getClass();
	}

    @Override
    public func copy0()
    {
        return new pi();
    }
	
    
	
}
