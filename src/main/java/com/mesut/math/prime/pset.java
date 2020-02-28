package com.mesut.math.prime;

import com.mesut.math.Config;
import com.mesut.math.core.cons;
import com.mesut.math.core.set;

import java.io.*;
import java.nio.ByteBuffer;

public class pset extends set {

    int n;
    static int[] primeArray;//prime cache
    static int plen;

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

    //read primes from cache and generate pset object
    public void fill() {
        for (int value : primeArray) {
            if (value <= n) {
                list.add(new cons(value));
            } else {
                break;
            }
        }
    }

    //return i th prime
    public static int get(int i) {
        if (i < 1) {
            throw new RuntimeException(String.format("invalid index %d for primes", i));
        }
        return primeArray[i];
    }





}
