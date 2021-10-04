package com.mesut.math.prime;

import com.mesut.math.core.func;
import com.mesut.math.core.set;
import com.mesut.math.core.variable;

public class countPrimes extends func {
    set set;

    public countPrimes(func set) {
        this.set = (set) set;
    }

    @Override
    public double eval(variable[] vars, double[] vals) {
        set res = (set) set.get(vars, vals);
        int count = 0;
        for (func elem : res.getElements()) {
            int val = (int) elem.eval();
            if (val % 2 == 0) continue;
            boolean isPrime = true;
            for (int i = 3; i * i < val; i += 2) {
                if (val % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                count++;
            }
        }
        return count;
    }
}
