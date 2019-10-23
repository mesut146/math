package math.cons;

import math.core.*;
import math.trigonometry.*;

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
    public func getReal()
    {
        return ZERO;
    }

    @Override
    public func getImaginary()
    {
        return signto(ONE);
    }
   
	@Override
	public boolean eq2(func f)
	{
		return getClass()==f.getClass();
	}

	/*@Override
	public func pow(func f)
	{
		if(f.isConstant()){
			if(f.is(0)){
				return signto(cons.ONE);
			}else if(f.is(1)){
				return this;
			}
		}
		return signto(cis(f.mul(cons.PID2)));
	}

	@Override
	public func pow(double d)
	{
		return pow(new cons(d));
	}*/

	static func cis(func f){
		return new cos(f).simplify().add(i.mul(new sin(f).simplify()));
	}

	@Override
	public func get0(var[] v, cons[] c)
	{
		return this;
	}

    @Override
    public double eval(var[] v, double[] d)
    {
        //System.out.println("error: tried to eval i");
        throw new RuntimeException("error: tried to eval i");
        //return 0;
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return this;
    }
	
	
}
