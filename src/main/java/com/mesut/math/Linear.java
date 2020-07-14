package com.mesut.math;

//a*n+b
public class Linear {
    public int a, b;

    public Linear(int a, int b) {
        this.a = a;
        this.b = b;
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
        StringBuilder sb = new StringBuilder();
        if (a != 1) {
            if (a == -1) {
                sb.append("-");
            }
            else {
                sb.append(a);
            }
        }
        sb.append("*n");
        if (b > 0) {
            sb.append("+");
        }
        sb.append(b);
        return sb.toString();
    }


}
