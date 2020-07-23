package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;
import com.mesut.math.funcs.exp;
import com.mesut.math.funcs.fac;
import com.mesut.math.funcs.inv;
import com.mesut.math.funcs.sqrt;
import com.mesut.math.operator.*;
import com.mesut.math.parser.MathParser;
import com.mesut.math.parser.ParseException;
import com.mesut.math.taylor;
import com.mesut.math.taylorsym;
import com.mesut.math.trigonometry.atan;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.*;

//base class for all expressions
public abstract class func {

    //used ny parser
    public static Map<String, Class<?>> map = new HashMap<>();//name to function class

    static {
        Config.init();
    }

    //public types type = types.func;
    public int sign = 1;//positive by default
    public boolean fx = false;
    public func a = null, b = null;//left - right for div and others
    public List<func> f = new ArrayList<>();//list of internal functions
    public List<func> alter = new ArrayList<>();//alternative representations

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
        map.put(fname, cls);
    }

    public static List<func> getFree() {
        return new ArrayList<>();
    }

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

    public static func parse(String expr) {
        try {
            return new MathParser(new StringReader(expr)).expr();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Error(expr);
        }
    }

    public func sqrt() {
        return new sqrt(this);
    }

    public void set(List<func> l) {
        f.clear();
        f.addAll(l);
        l.clear();
    }

    //get---------------------
    public final func get(cons val) {
        return get(variable.x, val);
    }

    public final func get(double val) {
        return get(variable.x, new cons(val));
    }

    public final func get(variable v, double val) {
        return get(v, new cons(val));
    }

    public final func get(variable[] vars, double[] vals) {
        cons[] c = new cons[vals.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = new cons(vals[i]);
        }
        return get0(vars, c);
    }

    public final func get(variable[] v, cons[] c) {
        func f = sign(get0(v, c));
        return f.simplify();
    }

    //comma separated assignments
    //x=5,y=1,z=5...
    public final func get(String s) {
        String[] sp = s.split(",");
        variable[] va = new variable[sp.length];
        cons[] ca = new cons[sp.length];
        int i = 0;
        for (String eq : sp) {
            String[] lr = eq.split("=");
            va[i] = new variable(lr[0]);
            ca[i] = new cons(Double.parseDouble(lr[1]));
            i++;
        }
        return get0(va, ca);
    }

    public func get(variable v, cons c) {
        return get0(new variable[]{v}, new cons[]{c});
    }

    //substitute vals with vars
    public abstract func get0(variable[] vars, cons[] vals);

    //eval----------------------------------
    public double eval() {
        return eval(variable.x, 0);
    }

    //comma separated vars and val array
    public final double eval(String vars, double... values) {
        if (values.length == 0) {
            throw new RuntimeException("value size must be positive: " + values.length);
        }
        variable[] varArr = new variable[values.length];
        String[] sp = vars.split(",");
        for (int i = 0; i < values.length; i++) {
            varArr[i] = new variable(sp[i]);
        }
        return eval(varArr, values);
    }

    public double eval(variable v, double d) {
        return eval(new variable[]{v}, new double[]{d});
    }

    //auto find var and assign val
    public double eval(double val) {
        List<variable> vars = vars();

        //default var is x if none is specified
        if (vars.size() == 0 || vars.contains(variable.x)) {
            return eval(variable.x, val);
        }
        //todo throw error if more than 1 var
        return eval(vars.get(0), val);
    }

    public abstract double eval(variable[] vars, double[] vals);

    public abstract cons evalc(variable[] vars, double[] vals);

    public cons evalc() {
        return evalc(new variable[]{}, new double[]{});
    }


    //--------------------------------------------
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
        List<variable> set = vars();
        if (set.isEmpty()) {
            //no var means we are cons
            return cons.ZERO;
        }
        return derivative(set.get(0));
    }

    public abstract func derivative(variable v);

    public final func derivative(String varStr) {
        return derivative(new variable(varStr));
    }

    public func derivative(int n) {
        return derivative(n, variable.x);
    }

    public func derivative(int n, Object ov) {
        variable v = Util.var(ov);
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
        return integrate(variable.x);
    }

    public abstract func integrate(variable v);

    public double integrate(Object lower, Object upper, Object var) {
        return new Integral(this, var, lower, upper).eval();
    }

    public double integrate(double a, double b, variable v) {
        return new Integral(this, v, new cons(a), new cons(b)).eval();
    }

    //public func(){}

	/*public static func sign(func f, int s)
     {
     func z=f.copy();
     z.sign *= s;
     return z;
     }*/

    public double integrate(double a, double b) {
        return integrate(a, b, variable.x);
    }

    public func simplify() {
        return this;
    }

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

    /*public String getType() {
        return type.toString();
    }*/

    public cons asCons() {
        return (cons) this;
    }

    public boolean is(double d) {
        //System.out.println(cons().functional);
        return isConstant() && !asCons().functional && eval() == d;
    }

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

    public List<variable> vars() {
        Set<variable> set = new HashSet<>();
        vars0(set);
        return new ArrayList<>(set);
    }

    public abstract void vars0(Set<variable> vars);

    public func substitute(variable v, func f) {
        return signf(substitute0(v, f));
    }

    public abstract func substitute0(variable v, func p);

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

