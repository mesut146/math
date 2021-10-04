package com.mesut.math.prime;

import com.mesut.math.core.func;
import com.mesut.math.core.set;
import com.mesut.math.core.variable;

import java.util.ArrayList;
import java.util.List;

//factorization of an integer
public class factor extends func {

    List<Integer> base = new ArrayList<>();
    List<Integer> pow = new ArrayList<>();

    //factorize elements of set
    public static set factorize(set set) {
        set res = new set();
        for (func term : set.getElements()) {
            res.put(factorize((int) term.eval()));
        }
        return res;
    }

    public static factor factorize(int x) {
        factor res = new factor();
        if (x == 1) {
            res.base.add(1);
            res.pow.add(1);
            return res;
        }
        x = res.single(x, 2);
        for (int i = 3; i <= Math.sqrt(x); i += 2) {
            if (x % i == 0)
                x = res.single(x, i);
        }
        if (x > 1) {
            res.base.add(x);
            res.pow.add(1);
        }
        return res;
    }

    public double eval() {
        int res = 1;
        for (int i = 0; i < base.size(); i++) {
            res *= Math.pow(base.get(i), pow.get(i));
        }
        return res;
    }

    @Override
    public double eval(variable[] vars, double[] vals) {
        return eval();
    }

    //factorize x by @prime
    private int single(int x, int prime) {
        int pwr = 0;

        while (x % prime == 0) {
            pwr++;
            x = x / prime;
        }
        if (pwr > 0) {
            base.add(prime);
            pow.add(pwr);
        }
        return x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < base.size(); i++) {
            sb.append(base.get(i));
            if (pow.get(i) > 1) {
                sb.append("^");
                sb.append(pow.get(i));
            }
            if (i < base.size() - 1) {
                sb.append("*");
            }
        }
        return sb.toString();
    }
}
