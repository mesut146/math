package com.mesut.math.prime;

import com.mesut.math.core.cons;
import com.mesut.math.core.set;

//prime set up to n
public class pset extends set {

    static int[] primeArray;//prime cache
    int n;
    //static int plen;

    //up to n
    public pset(int n) {
        if (n % 2 == 0) {
            n = n - 1;
        }
        this.n = n;
        start = 1;
        name = "p";
        fill();
        end = list.size();
    }

    public pset() {

    }

    //return i th prime
    public static int get(int i) {
        if (i < 1) {
            throw new RuntimeException(String.format("invalid index %d for primes", i));
        }
        return primeArray[i - 1];
    }

    //read primes from cache and generate pset object
    public void fill() {
        PrimeGenerator.init();
        primeArray = PrimeGenerator.primes;
        for (int value : primeArray) {
            if (value <= n) {
                list.add(new cons(value));
            }
            else {
                break;
            }
        }
    }


}
