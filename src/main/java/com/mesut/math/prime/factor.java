package com.mesut.math.prime;

import com.mesut.math.core.func;
import com.mesut.math.core.set;

import java.util.ArrayList;
import java.util.List;

public class factor {

    List<Integer> base = new ArrayList<>();
    List<Integer> pow = new ArrayList<>();
    int x;

    public static List<factor> factorize(set s) {
        List<factor> l = new ArrayList<>();
        for (func term : s.getList()) {
            l.add(factorize((int) term.eval()));
        }
        return l;
    }

    public static factor factorize(int x) {
        factor res = new factor();
        res.x = x;
        int p = 0;
        while (x % 2 == 0) {
            p++;
            x = x / 2;
        }
        if (p > 0) {
            res.base.add(2);
            res.pow.add(p);
        }
        for (int i = 3; i <= x; i += 2) {
            p = 0;
            while (x % i == 0) {
                p++;
                x = x / i;
            }
            if (p > 0) {
                res.base.add(i);
                res.pow.add(p);
            }
        }
        return res;
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
