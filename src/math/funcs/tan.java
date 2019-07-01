package math.funcs;
import math.*;

public class tan extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }


    public tan(func f){
        this.a=f;
        alter.add(new sin(f).div(new cos(f)));
		fx=true;
    }

    @Override
    public func get(Variable v, Constant c) {
        return alter.get(0).get(v, c).s(sign);
    }

	@Override
	public double get2(Variable v, double d)
	{
		return sign*Math.tan(a.get2(v,d));
	}

	
	

    @Override
    public func derivative(Variable v) {
        return alter.get(0).derivative(v);
    }

    @Override
    public func integrate(Variable v) {
        return null;
    }

    @Override
    public String toString2() {
        return "tan("+a+")";
    }

    @Override
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public func substitude0(Variable v, func p) {
        return null;
    }

    @Override
    public func copy0() {
        return new tan(a);
    }
}
