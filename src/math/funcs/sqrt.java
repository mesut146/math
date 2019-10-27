package math.funcs;
import math.core.cons;
import math.core.var;
import math.core.func;
import java.util.*;
import math.operator.*;

public class sqrt extends pow
{
    
    @Override
    public String toLatex()
    {
        return "\\sqrt{"+a.toLatex()+"}";
    }

	public sqrt(func f){
        super(f,cons.ONE.div(2));
	}
	public sqrt(double d){
	    this(new cons(d));
    }

	@Override
	public func copy0()
	{
		return new sqrt(a);
	}

	@Override
	public String toString2()
	{
		return "sqrt("+a+")";
	}

	@Override
	public boolean eq2(func f)
	{
		return a.eq(f.a);
	}

    /*@Override
    public func simplify() {
        //sqrt(a^2b)=a^b
        if(a.isPow()&&a.b.isConstant()&&a.b.eval()%2==0){
            a.b=a.b.div(2);
            return this.simplify();
        }
        if(a.isCons0()&&a.cons().isInteger()){
            cons d=evalc();
            if(d.isInteger()){
                return d;
            }
        }
        
        return this;
    }*/
}
