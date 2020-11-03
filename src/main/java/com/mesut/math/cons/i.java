package com.mesut.math.cons;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;

public class i extends cons {

    public i() {
        functional = true;
        ff = this;
    }

    @Override
    public String toString2() {
        return "i";
    }

    @Override
    public func copy0() {
        return new i();
    }

    @Override
    public func getReal() {
        return ZERO;
    }

    @Override
    public func getImaginary() {
        return signf(ONE);
    }

    @Override
    public boolean eq0(func f) {
        return getClass() == f.getClass();
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

	/*static func cis(func f){
		return new cos(f).simplify().add(i.mul(new sin(f).simplify()));
	}*/

    @Override
    public func get0(variable[] vars, cons[] vals) {
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        throw new RuntimeException("error: tried to eval i");
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        return this;
    }


}
