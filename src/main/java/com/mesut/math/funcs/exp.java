package com.mesut.math.funcs;

import com.mesut.math.core.Integral;
import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.variable;
import com.mesut.math.operator.pow;

//e^x
public class exp extends pow {
    func pow;

    public exp(func f) {
        super(cons.E, f);
        pow = f;
    }

    @Override
    public func derivative(variable v) {
        return signf(mul(pow.derivative(v)));
    }

    @Override
    public func integrate(variable v) {
        if (pow.eq(v)) {
            return this;
        }
        return new Integral(this, v);
    }

    @Override
    public boolean eq2(func f) {
        return b.eq(f.b);
    }


}
