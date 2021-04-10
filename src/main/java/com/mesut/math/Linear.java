package com.mesut.math;

import com.mesut.math.core.variable;

//a*n+b
public class Linear {
    public static variable defaultVar = variable.n;
    public int a, b;
    public variable v;

    public Linear(int a, int b) {
        this.a = a;
        this.b = b;
        this.v = defaultVar;
    }

    public boolean isEven() {
        return a % 2 == 0 && b % 2 == 0;
    }

    public boolean isOdd() {
        return a % 2 == 0 && b % 2 == 1;
    }

    public Linear mul(int k) {
        return new Linear(a * k, b * k);
    }

    public Linear div(int k) {
        return new Linear(a / k, b / k);
    }

    public Linear add(int k) {
        return new Linear(a, b + k);
    }

    @Override
    public String toString() {
        if (v == null) {
            v = defaultVar;
        }
        StringBuilder sb = new StringBuilder();
        if (a == -1) {
            sb.append("-");
        }
        else if (a != 1) {
            sb.append(a);
        }
        sb.append(v);
        if (b > 0) {
            sb.append("+");
        }
        sb.append(b);
        return sb.toString();
    }


}
