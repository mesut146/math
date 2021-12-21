package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.cons.e;
import com.mesut.math.cons.i;
import com.mesut.math.cons.phi;
import com.mesut.math.cons.pi;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class cons extends func {

    public static final cons ZERO = new cons(0);
    public static final cons ONE = new cons(1);
    public static final cons TWO = new cons(2);
    public static final cons NaN, INF, MINF;
    public static cons PI;
    public static cons i;
    public static cons PHI;
    public static cons E;

    static {
        NaN = makeNan();
        INF = new cons(Double.POSITIVE_INFINITY);
        MINF = new cons(Double.NEGATIVE_INFINITY);
    }

    public boolean functional = false;
    public func ff;
    public double val = 0;
    public BigDecimal big;
    boolean nan = false, inf = false;

    public cons(double d) {
        if (d == Double.POSITIVE_INFINITY) {
            val = d;
            inf = true;
        }
        else if (d == Double.NEGATIVE_INFINITY) {
            val = -d;
            sign = -1;
            inf = true;
        }
        else {
            if (Double.isNaN(d)) {
                nan = true;
            }
            else {
                if (d < 0) {
                    sign = -1;
                    val = -d;
                }
                else {
                    val = d;
                }
                big = new BigDecimal(val);
            }
        }
    }

    public cons(func f) {
        ff = f;
        functional = true;
    }

    public cons(BigDecimal b) {
        if (b.signum() == -1) {
            b = b.negate();
            sign = -1;
        }
        big = b;
        val = big.doubleValue();
    }

    public cons() {
    }

    public static void init() {
        E = new e();
        PI = new pi();
        PHI = new phi();
        i = new i();
    }

    public static func of(double a) {
        return new cons(a);
    }

    private static cons makeInf() {
        cons res = new cons();
        res.val = Double.MAX_VALUE;
        res.inf = true;
        return res;
    }

    private static cons makeNan() {
        cons res = new cons();
        res.nan = true;
        return res;
    }

    public static boolean isInteger(double value) {
        return value == (double) (int) value;
    }

    public cons from(double d) {
        return new cons(d);
    }

    public boolean isInteger() {
        if (!functional) {
            return isInteger(val);
        }
        return false;
    }

    public boolean isEven() {
        return isInteger() && val % 2 == 0;
    }

    public boolean isOdd() {
        return isInteger() && val % 2 == 1;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public func copy0() {
        if (inf) {
            return makeInf();
        }
        else if (nan) {
            return makeNan();
        }
        return new cons(val);
    }

    //all cons are real except i
    @Override
    public func getReal() {
        return this;
    }

    @Override
    public func getImaginary() {
        return ZERO;
    }

    @Override
    public String toLatex() {
        if (functional) {
            return a.toLatex();
        }
        if (nan) {
            return "NaN";
        }
        if (inf) {
            if (sign == -1) {
                return "-\\infty";
            }
            return "\\infty";
        }
        if (Config.useBigDecimal) {
            return big.toString();
        }
        if (isInteger()) {
            return Integer.toString((int) val);
        }
        return Double.toString(val);
    }

    @Override
    public func get0(variable[] vars, cons[] vals) {
        if (functional) {
            if (ff == null) {
                return new cons(val).signOther(sign);
            }
        }
        return this;
    }

    @Override
    public double eval(variable[] v, double[] vals) {
        if (functional) {
            return sign * ff.eval(v, vals);
        }
        return sign * val;
    }

    @Override
    public cons evalc(variable[] vars, double[] vals) {
        if (functional) {
            return (cons) signf(ff.evalc(vars, vals));
        }
        return this;
    }

    public BigDecimal decimal() {
        if (sign == -1) {
            return big.negate();
        }
        return big;
    }

    public boolean isMinf() {
        return inf && sign == -1;
    }

    public boolean isInf() {
        return inf;
    }

    @Override
    public func derivative(variable v) {
        return ZERO;
    }

    @Override
    public func integrate(variable v) {
        return mul(v);
    }

    @Override
    public String toString2() {
        if (nan) {
            return "NaN";
        }
        if (inf) {
            return "inf";
        }
        if (functional) {
            return ff.toString();
        }
        if (Config.useBigDecimal) {
            return big.toString();
        }
        if (isInteger()) {
            return Integer.toString((int) val);
        }
        if (Config.printDecimals > 0) {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setMaximumFractionDigits(Config.printDecimals);
            return df.format(val);
            //return String.format("%." + Config.printDecimals + "f", val);
        }
        return Double.toString(val);
    }

    @Override
    public boolean eq0(func f) {
        cons c = (cons) f;
        if (inf && c.inf) {
            return true;
        }
        if (nan && c.nan) {
            return true;
        }
        if (functional || ((cons) f).functional) {
            return getClass() == f.getClass();
        }
        if (val == f.eval()) {
            return true;
        }
        return false;
    }

}
