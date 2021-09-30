package com.mesut.math.prime;

import com.mesut.math.core.cons;
import com.mesut.math.core.set;

//prime set up to n
public class pset extends set {

    static int[] primeArray;//prime cache
    int start;
    int end;

    //up to n
    public pset(int end) {
        this(2, end);
    }

    public pset(int start, int end) {
        if (start % 2 == 0) start++;
        if (end % 2 == 0) end--;
        this.start = start;
        this.end = end;
        name = "p";
        fill();
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
