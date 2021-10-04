package com.mesut.math.prime;

import com.mesut.math.core.cons;
import com.mesut.math.core.func;
import com.mesut.math.core.set;

//prime set up to n
public class pset extends set {
    int start;
    int end;

    public pset(func end) {
        this((int) end.eval());
    }

    public pset(func start, func end) {
        this((int) start.eval(), (int) end.eval());
    }

    //up to n
    public pset(int end) {
        this(2, end);
    }

    public pset(int start, int end) {
        this.start = start;
        this.end = end;
        name = "p";
        fill();
    }

    //return i th prime
    public static int get(int i) {
        if (i < 1) {
            throw new RuntimeException(String.format("invalid prime index %d", i));
        }
        if (i - 1 >= PrimeGenerator.primes.length) {
            throw new RuntimeException("prime index is too large, max is: " + (PrimeGenerator.primes.length));
        }
        return PrimeGenerator.primes[i - 1];
    }

    //read primes from cache and generate pset object
    public void fill() {
        PrimeGenerator.init();
        for (int value : PrimeGenerator.primes) {
            if (value < start) {
                continue;
            }
            if (value <= end) {
                elements.add(new cons(value));
            }
            else {
                break;
            }
        }
    }


}
