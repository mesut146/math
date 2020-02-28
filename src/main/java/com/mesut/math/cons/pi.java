package com.mesut.math.cons;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.*;

public class pi extends cons
{

    @Override
    public String toLatex()
    {
        return "\\pi";
    }

	public pi(){
		functional=true;
		val=Math.PI;
		ff=this;
	}

    @Override
    public func get0(var[] v, cons[] c)
    {
        return this;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        return val;
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        return this;
    }
    
	@Override
	public String toString2()
	{
		//return "pi";
        return String.valueOf('\u03C0');
	}

	@Override
	public boolean eq2(func f)
	{
		return f.getClass()==pi.class;
	}

    @Override
    public func copy0()
    {
        return new pi();
    }
	
    
	
}