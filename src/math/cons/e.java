package math.cons;
import math.*;

public class e extends Constant
{
	public e(){
		functional=true;
		val=Math.E;
		ff=this;
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
