package math.funcs;
import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;
import math.op.*;

public class ln extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

	public ln(func f){
		type=types.ln;
		a=f;
		fx=true;
		if(f.isPow()){
			//alter.add(f.b.mul(new ln(f.a)).simplify());
		}
	}

	@Override
	public func get(Variable v, Constant c)
	{
		return new ln(a.get(v, c)).s(sign);
	}

	@Override
	public double get2(Variable v, double d)
	{
		return sign*Math.log(a.get2(v,d));
	}

	@Override
	public String toString2()
	{
		return "ln("+a+")";
	}

	@Override
	public func derivative(Variable v)
	{
		return a.derivative(v).div(a).s(sign);
	}

    @Override
    public func integrate(Variable v)
    {
        if(a.eq(v)){
            return mul(v).sub(v);
        }
        if(true){
            return new mul(this,Constant.ONE).byParts();
        }
        return new Anti(this);
    }

    @Override
    public func copy0() {
        return new ln(a);
    }

    @Override
	public boolean eq2(func f)
	{
		return a.eq(f.a);
	}

    @Override
    public func substitude0(Variable v, func p)
    {
        return new ln(a.substitude0(v,p));
    }

	@Override
	public func simplify()
	{	
		/*if(a.isConstant()){
			if(a.eq(Constant.E)){
				return Constant.ONE;
			}
			return new Constant(this);
		}*/
        if(a.eq(parse("e^f(x)"))){
            return a.b;
        }
        if(a.eq(Constant.E)){
            return Constant.ONE;
        }
		/*for(func r:rules.keySet()){
			if(r.getClass()==getClass()){
				if(this.eq(r)){
                    //matcher?
					func g=rules.get(r).simplify();
					//System.out.println("g="+g);
					return r.name().equals("fx")?g.b:g;
				}
			}
		}*/
		return this;
	}

	public static func pull(func f){
        return null;
    }
	
	
}
