package com.mesut.math.prime;

import com.mesut.math.core.func;
import com.mesut.math.core.set;

import java.util.ArrayList;
import java.util.List;

//factorization of an integer
public class factor {

    List<Integer> base = new ArrayList<>();
    List<Integer> pow = new ArrayList<>();

    //factorize elements of set
    public static List<factor> factorize(set set) {
        List<factor> list = new ArrayList<>();
        for (func term : set.getElements()) {
            list.add(factorize((int) term.eval()));
        }
        return list;
    }

    public int eval() {
        int res = 1;
        for (int i = 0; i < base.size(); i++) {
            res *= Math.pow(base.get(i), pow.get(i));
        }
        return res;
    }

    public static factor factorize(int x) {
        factor res=new factor();
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
