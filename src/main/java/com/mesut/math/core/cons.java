package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.cons.e;
import com.mesut.math.cons.i;
import com.mesut.math.cons.phi;
import com.mesut.math.cons.pi;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Set;

public class cons extends func {

    public static final cons ZERO = new cons(0),
            ONE = new cons(1),
            TWO = new cons(2);
    public static final cons NaN, INF, MINF;
    public static final cons E = new e();
    public static final cons PI = new pi();
    public static final cons i = new i();
    public static final cons PHI = new phi();
    //public static final func PID2 = PI.div(2), PID3 = PI.div(3);

    static {
        NaN = makeNan();
        INF = makeInf();
        MINF = makeInf();
        MINF.sign = -1;
		/*PI2=(Constant)PI.div(2);
		PI3=(Constant)PI.div(3);
		PI4=(Constant)PI.div(4);
		PI5=(Constant)PI.div(5);
		PI6=(Constant)PI.div(6);
		*/
    }

    public boolean functional = false;
    public func ff;
    public double val = 0;
    public BigDecimal big;
    //public static MathContext mc = new MathContext(Config.precision);
    //public static final Constant PI2,PI3,PI4,PI5,PI6;
    boolean nan = false, inf = false;

    public cons(double d) {
        if (d < 0) {
            sign = -1;
            d = -d;
        }
        val = d;
        if (Double.isInfinite(val)) {
            inf = true;
        }
        else if (Double.isNaN(val)) {
            nan = true;
        }
        else {
            big = new BigDecimal(val);
        }
        //type = types.constant;
    }

    public cons(func f) {
        ff = f;
        functional = true;
        //type = types.constant;
    }

    public cons(BigDecimal b) {
        //type = types.constant;
        if (b.signum() == -1) {
            b = b.negate();
            sign = -1;
        }
        big = b;
        val = big.doubleValue();
    }

    public cons() {
        //type = types.constant;
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

    //i will override this
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
    public func get0(variable[] v, cons[] c) {
        if (functional) {
            if (ff == null) {
                return new cons(val).sign(sign);
            }
            //System.out.println("ff="+ff);
            //return ff.get(v, c).s(sign);
        }
        return this;
    }

    @Override
    public void vars0(Set<variable> vars) {

    }

    @Override
    public double eval(variable[] v, double[] d) {
        if (functional) {
            //System.out.println("ff="+ff);
            return sign * ff.eval(v, d);
        }
        return sign * val;
    }

    /*@Override
    public func add(func f)
    {
        if(f.isCons0()&&Config.useBigDecimal){
            BigDecimal bd=decimal().add(f.cons().decimal(),mc);
            return new cons(bd);
        }
        return super.add(f);
    }*/

    /*@Override
    public func mul(func f)
    {
        //(6+x)*4567
        if(isCons0()&&f.isCons0()){
            if(Config.useBigDecimal){
                BigDecimal bd=decimal().multiply(f.cons().decimal(),mc);
                return new cons(bd);
            }else{
                return new cons(eval()*f.eval());
            }
            
        }
        return super.mul(f);
    }*/

    /*@Override
    public func pow(func f)
    {
        if(f.isCons0()&&Config.useBigDecimal){
            
            BigDecimal bd=decimal().pow(f.cons().decimal().intValue(),mc);
            //System.out.println("in cpow="+this+" f="+f);
            //System.out.println("bd="+new Constant(bd));
            return new cons(bd);
        }
        return super.pow(f);
    }*/

    @Override
    public cons evalc(variable[] v, double[] d) {
        if (functional) {
            //System.out.println("ff="+ff);
            return (cons) signf(ff.evalc(v, d));
        }
        return this;
    }

    public BigDecimal decimal() {
        if (sign == -1) {
            return big.negate();
        }
        return big;
    }

    public boolean isInteger() {
        if (!functional) {
            return val == (int) val;
        }
        return false;
    }

    public boolean isMinf() {
        return inf && sign == -1;
    }

    public boolean isInf() {
        return inf;
    }

    public boolean isBig() {
        return big != null;
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

    public cons from(double d) {
        return new cons(d);
    }

    @Override
    public boolean eq2(func f) {
        cons c = (cons) f;
        if (inf && c.inf) {
            return true;
        }
        if (nan && c.nan) {
            return true;
        }
        //System.out.println("this="+this+" that="+f);
        if (functional || ((cons) f).functional) {
            //return ff.eq(((Constant)f).ff);
            //System.out.println(getClass()==f.getClass());
            return getClass() == f.getClass();
        }
        if (val == f.eval()) {
            return true;
        }
        return false;
    }

    @Override
    public func substitude0(variable v, func p) {
        return this;
    }


}
