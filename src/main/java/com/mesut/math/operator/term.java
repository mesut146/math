package com.mesut.math.operator;

public class term extends mul {
    double coeff;
    int n;

    public term(double coeff, int n) {
        this.coeff = coeff;
        this.n = n;
    }

    @Override
    public String toString() {
        if (coeff == 0) {
            return "0";
        }
        if (n == 0) {
            return "" + coeff;
        }
        if (n == 1) {
            return coeff + "*x";
        }
        return coeff + "*x^" + n;
    }
}
