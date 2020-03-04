package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;
import com.mesut.math.funcs.exp;
import com.mesut.math.funcs.fac;
import com.mesut.math.funcs.inv;
import com.mesut.math.funcs.sqrt;
import com.mesut.math.operator.*;
import com.mesut.math.parser2.MathParser;
import com.mesut.math.parser2.ParseException;
import com.mesut.math.taylor;
import com.mesut.math.taylorsym;
import com.mesut.math.trigonometry.atan;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class func {

    public enum types {
        func, fx, constant, variable, add, sub, mul, div, pow, ln, sin, cos
    }

    //public types type = types.func;
    public int sign = 1;//positive by default
    public boolean fx = false;
    public func a = null, b = null;//left - right for div and others
    public List<func> f = new ArrayList<>();//list of internal functions
    public List<func> alter = new ArrayList<>();//alternative representations
    public static Map<String, Class<?>> map = new HashMap<>();

    static {
        Config.init();
    }

    //TODO make args Object autocast
    public static func makeFunc(String name, List<func> args) {
        Class<func> c;
        Constructor<func> co;
        func res = null;
        if (map.containsKey(name)) {
            try {
                c = (Class<func>) map.get(name);
                if (args.size() == 1) {
                    co = c.getDeclaredConstructor(func.class);
                    res = co.newInstance(args.get(0));
                }
                else {
                    //System.out.println("args="+args);
                    Class<?>[] arr = new Class<?>[args.size()];
                    for (int i = 0; i < args.size(); i++) {
                        arr[i] = func.class;
                    }
                    co = c.getDeclaredConstructor(arr);
                    res = co.newInstance(args.toArray(new func[args.size()]));
                }

            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println(this);
            }
        }
        else {
            if (com.mesut.math.core.fx.has(name)) {
                res = com.mesut.math.core.fx.getFx(name);
            }
            else {
                res = new fx(name, args.toArray(new func[args.size()]));
            }
        }
        return res;
    }

    //register func name and its class ex. (sin,math.trigo.sin)
    public static void register(String fname, Class<? extends func> cls) {
        //System.out.println(fname);
        map.put(fname, cls);

    }

    public func sqrt() {
        return new sqrt(this);
    }


    public void set(List<func> l) {
        f.clear();
        f.addAll(l);
        l.clear();
    }

    public final func get(cons c) {
        //System.out.println("fget(c)="+c);
        return get(var.x, c);
    }

    public final func get(double d) {
        //System.out.println("fget(d)="+d);
        return get(var.x, new cons(d));
    }

    public final func get(var v, double d) {
        //System.out.println("fget(v,d)="+v+","+d);
        return get(v, new cons(d));
    }

    public final func get(var[] v, double[] d) {
        cons[] c = new cons[d.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = new cons(d[i]);
        }
        return get0(v, c);
    }

    public final func get(var[] v, cons[] c) {
        func f = sign(get0(v, c));
        return f.simplify();
    }

    public final func get(String s) {
        String[] sp = s.split(",");
        var[] va = new var[sp.length];
        cons[] ca = new cons[sp.length];
        int i = 0;
        for (String eq : sp) {
            String[] lr = eq.split("=");
            va[i] = new var(lr[0]);
            ca[i] = new cons(Double.parseDouble(lr[1]));
            i++;
        }
        return get0(va, ca);
    }

    public func get(var v, cons c) {
        return get0(new var[]{v}, new cons[]{c});
    }

    public double eval() {
        return eval(var.x, 0);
    }

    public final double eval(String s, double... d) {
        if (d.length == 0) {
            Pattern p = Pattern.compile("(\\w)+=(\\d+(\\.\\d+)?)");
            Matcher m = p.matcher(s);
            List<var> vl = new ArrayList<>();
            List<Double> dl = new ArrayList<>();
            while (m.find()) {
                vl.add(new var(m.group(1)));
                dl.add(Double.parseDouble(m.group(2)));
                //System.out.println(m.group(1));
                //System.out.println(m.group(2));

            }
            return eval(vl.toArray(new var[0]), cast(dl));
        }
        var[] v = new var[d.length];
        String[] sp = s.split(",");
        for (int i = 0; i < d.length; i++) {
            v[i] = new var(sp[i]);
        }
        return eval(v, d);
    }

    double[] cast(List<Double> list) {
        double[] r = new double[list.size()];

        for (int i = 0; i < r.length; i++) {
            r[i] = list.get(i);
        }
        return r;
    }

    public abstract func get0(var[] v, cons[] c);

    public abstract double eval(var[] v, double[] d);

    public abstract cons evalc(var[] v, double[] d);

    public cons evalc() {
        return evalc(new var[]{}, new double[]{});
    }

    public double eval(var v, double d) {
        return eval(new var[]{v}, new double[]{d});
    }

    public double eval(double d) {
        List<var> vars = vars();

        if (vars.size() == 0 || vars.contains(var.x)) {
            return eval(var.x, d);
        }
        return eval(vars.get(0), d);
    }

    //multiplicative inverse
    public func MulInverse() {
        return cons.ONE.div(this);
    }

    public func inverse() {
        return new inv(this);
    }

    //a
    public abstract func getReal();

    //b
    public abstract func getImaginary();

    //a+b*i
    public func getComplex() {
        return getReal().add(cons.i.mul(getImaginary()));
    }

    //r*e^(i*a)
    public func getPolar() {
        func r = getReal();
        func im = getImaginary();
        func abs = r.pow(2).add(im.pow(2)).sqrt();
        func arg = new atan(im.div(r));
        return abs.mul(new exp(cons.i.mul(arg)));
    }// |r|

    public func getAbs() {
        func r = getReal();
        func im = getImaginary();
        return r.pow(2).add(im.pow(2)).sqrt();
    }//theta

    public func getArg() {
        return new atan(getImaginary().div(getReal()));
    }

    public boolean isReal() {
        return getComplex().is(0);
    }

    public boolean isComplex() {
        return !getComplex().is(0);
    }

    public final func derivative() {
        List<var> set = vars();
        if (set.isEmpty()) {
            return cons.ZERO;
        }
        return derivative(set.get(0));
    }

    public abstract func derivative(var v);

    public final func derivative(String varStr) {
        return derivative(new var(varStr));
    }

    public func derivative(int n) {
        return derivative(n, var.x);
    }

    public func derivative(int n, Object ov) {
        var v = Util.var(ov);
        if (n < 1) {
            return this;
        }
        func res = this;
        for (int i = 0; i < n; i++) {
            res = res.derivative(v);
        }
        return res;
    }


    public final func integrate() {
        return integrate(var.x);
    }

    public abstract func integrate(var v);

    public double integrate(Object lower, Object upper, Object var) {
        return new Integral(this, var, lower, upper).eval();
    }

    public double integrate(double a, double b, var v) {
        return new Integral(this, v, new cons(a), new cons(b)).eval();
    }

    public double integrate(double a, double b) {
        return integrate(a, b, var.x);
    }

    public func simplify() {
        return this;
    }

    //public func(){}

	/*public static func sign(func f, int s)
     {
     func z=f.copy();
     z.sign *= s;
     return z;
     }*/

    public func sign(int s) {
        sign *= s;
        return this;
    }

    //sign other function form this
    public func sign(func other) {
        other.sign *= sign;
        return other;
    }

    //sign with copy
    public func signf(func other) {
        func copy = other.copy();
        copy.sign *= sign;
        return copy;
    }

    public cons sc(func o) {
        return (cons) o.sign(sign);
    }

    //if both are cons the result should be boxed
    func makeCons(func x, func f) {
        if (isConstant() && f.isConstant()) {
            //return new cons(x);
        }
        return x;
    }

    public func add(func other) {
        func x = new add(this, other);
        if (Config.add.simplify) {
            x = x.simplify();
        }
        return makeCons(x, other);
    }

    public func add(double other) {
        return add(new cons(other));
    }

    public func sub(func f) {
        func x = new add(this, f.negate());
        if (Config.add.simplify) {
            x = x.simplify();
        }
        return makeCons(x, f);
    }

    public func sub(double d) {
        return sub(new cons(d));

    }

    public func mul(func f) {
        if (f instanceof poly) {
            return f.mul(this);
        }
        if (f.is(1)) {
            return this;
        }
        else if (f.is(0)) {
            return cons.ZERO;
        }
        func x = new mul(this, f);
        if (Config.mul.simplify) {
            x = x.simplify();
        }
        return makeCons(x, f);
        //return new mul(this, f).simplify(); 
    }

    public func mul(double d) {
        return mul(new cons(d));
    }

    public func div(func f) {
        func x = new div(this, f);
        if (Config.div.simplify) {
            x = x.simplify();
        }
        return makeCons(x, f);
    }

    public func div(double val) {
        return div(new cons(val));
    }

    public func pow(func f) {
        func x = new pow(this, f);
        if (Config.pow.simplify) {
            x = x.simplify();
        }
        return makeCons(x, f);
    }

    public func pow(double d) {
        return pow(new cons(d));
    }

    public func fac() {
        return new fac(this);
    }

    public cons asCons() {
        return (cons) this;
    }

    public boolean is(double d) {
        //System.out.println(cons().functional);
        return isConstant() && !asCons().functional && eval() == d;
    }

    /*public String getType() {
        return type.toString();
    }*/

    //negate without copy
    public func negate0() {
        sign = -sign;
        return this;
    }

    //negate with copy
    public func negate() {
        func res = copy();
        res.sign = -res.sign;
        return res;
    }

    public final func copy() {
        func res = copy0();
        res.sign = sign;
        return res;
    }

    public abstract func copy0();//internal

    public static List<func> getFree() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        String str = toString2();
        if (sign == -1) {
            str = "-" + str;
        }
        return str;
    }

    public abstract String toString2();

    public abstract String toLatex();

    //print with parenthesis
    public String top() {
        if (fx) {
            return toString();
        }
        return "(" + toString() + ")";
    }

    //get index of that type
    public int find(Class<func> type) {
        for (int i = 0; i < f.size(); i++) {
            if (f.get(i).getClass() == type) {
                return i;
            }
        }
        return -1;
    }


    public boolean isNumber() {
        return isConstant() && !isConsfunc();
    }

    public boolean isConstant() {
        return false;
    }

    public boolean isConsfunc() {
        return isConstant() && asCons().functional;
    }

    public boolean isCons0() {
        return isConstant() && !asCons().functional;
    }

    public boolean isVariable() {
        return false;
    }

    public boolean isAdd() {
        return false;
    }

    public boolean isMul() {
        return false;
    }

    public boolean isDiv() {
        return false;
    }

    public boolean isPow() {
        return false;
    }

    public boolean isOperator() {
        return isAdd() || isMul() || isDiv() || isPow();
    }

    //var^cons
    public boolean isPolynom() {
        return isPow() && a.isVariable() && b.isConstant();
    }

    //cons^var
    public boolean isPower() {
        return isPow() && b.isVariable() && a.isConstant();
    }

    public String name() {
        String res = getClass().getName();
        return res.substring(res.lastIndexOf(".") + 1);
    }

    @Override
    public boolean equals(Object other) {
        if ((other instanceof func)) {
            return eq((func) other);
        }
        return false;
    }

    public boolean eq(func other) {
        if (other != null && getClass() == other.getClass()) {
            return sign == other.sign && eq2(other);
        }
        return false;
    }

    //equal without sign
    public boolean eqws(func other) {

        if (other != null && getClass() == other.getClass()) {
            return eq2(other);
        }
        return false;
    }

    public abstract boolean eq2(func f);

    public static boolean isEq(List<func> l1, List<func> l2) {
        int len = l1.size();
        if (len != l2.size()) {
            return false;
        }
        boolean[] b = new boolean[len];//processed flags for l2
        boolean matchedAny = false;//flag for an element is not matched

        for (func first : l1) {
            for (int j = 0; j < len; j++) {
                if (!b[j] && first.eq(l2.get(j))) {
                    b[j] = true;
                    matchedAny = true;
                    break;
                }
            }
            if (!matchedAny) {
                return false;
            }
        }
        return false;
    }

    public List<var> vars() {
        Set<var> set = new HashSet<>();
        vars0(set);
        return new ArrayList<>(set);
    }

    public abstract void vars0(Set<var> vars);

    public static func parse(String expr) {
        try {
            return new MathParser(new StringReader(expr)).expr();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Error(expr);
        }
        //return Parser.parse(expr).simplify();
    }

    public func substitude(var v, func f) {
        return signf(substitude0(v, f));
    }

    public abstract func substitude0(var v, func p);

    //center,terms
    public taylor taylor(double at, int n) {
        taylor taylor = new taylor(this, at);
        taylor.calc(n);
        return taylor;
    }

    //var,center,terms
    public taylorsym taylorsym(Object var, func at, int n) {
        taylorsym taylorsym = new taylorsym(this, var, at);
        taylorsym.calc(n);
        return taylorsym;
    }


    public double numericDerivative(double at) {
        return numericDerivative(at, Config.numericDerivativePrecision);
    }

    public double numericDerivative(double at, int precision) {
        double h = Math.pow(10, -precision);
        return (eval(at + h) - eval(at)) / h;
    }

}

