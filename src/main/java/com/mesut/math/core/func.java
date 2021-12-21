package com.mesut.math.core;

import com.mesut.math.Config;
import com.mesut.math.Util;
import com.mesut.math.funcs.exp;
import com.mesut.math.funcs.fac;
import com.mesut.math.funcs.inv;
import com.mesut.math.funcs.sqrt;
import com.mesut.math.operator.*;
import com.mesut.math.parser.AstBuilder;
import com.mesut.math.taylor;
import com.mesut.math.taylorsym;
import com.mesut.math.trigonometry.atan;

import java.lang.reflect.Constructor;
import java.util.*;

//base class for all expressions
public abstract class func {

    //used by parser
    public static Map<String, Class<?>> map = new HashMap<>();//name to function class

    static {
        Config.init();
    }

    public int sign = 1;//positive by default
    public boolean fx = false;
    public func a = null, b = null;//left - right for div and others
    public List<func> list = new ArrayList<>();//list of internal functions(args)
    public List<func> alter = new ArrayList<>();//alternative representations

    //TODO make args Object autocast
    @SuppressWarnings("unchecked")
    public static func makeFunc(String name, List<func> args) {
        Class<func> clazz;
        Constructor<func> cons;
        func res = null;
        if (map.containsKey(name)) {
            try {
                clazz = (Class<func>) map.get(name);
                if (args.size() == 1) {
                    cons = clazz.getDeclaredConstructor(func.class);
                    res = cons.newInstance(args.get(0));
                }
                else {
                    Class<?>[] arr = new Class<?>[args.size()];
                    for (int i = 0; i < args.size(); i++) {
                        arr[i] = func.class;
                    }
                    cons = clazz.getDeclaredConstructor(arr);
                    res = cons.newInstance((Object[]) args.toArray(new func[0]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            //user defined
            return new FuncCall(name, args);
        }
        return res;
    }

    //register func name and its class ex. (sin,math.trigo.sin)
    public static <T extends func> void register(String fname, Class<T> cls) {
        map.put(fname, cls);
    }

    public static List<func> getFree() {
        return new ArrayList<>();
    }

    public static func parse(String expr) {
        try {
            return AstBuilder.make(expr).simplify();
            //return new MathParser(new StringReader(expr)).expr().simplify();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(expr);
        }
    }

    public func sqrt() {
        return new sqrt(this).simplify();
    }

    public void set(List<func> l) {
        list.clear();
        list.addAll(l);
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
        func f = signOther(get0(v, c));
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
    public func get0(variable[] vars, cons[] vals) {
        return this;
    }

    //eval----------------------------------
    public double eval() {
        return eval(variable.x, 0);
    }

    //comma separated vars and val array
    public final double eval(String vars, double... values) {
        if (values.length == 0) {
            return evalStr(vars);
        }
        else {
            variable[] varArr = new variable[values.length];
            String[] sp = vars.split(",");
            for (int i = 0; i < values.length; i++) {
                varArr[i] = new variable(sp[i]);
            }
            return eval(varArr, values);
        }
    }


    private double evalStr(String s) {
        String[] sp = s.split(",");
        variable[] vars = new variable[sp.length];
        double[] vals = new double[sp.length];
        int i = 0;
        for (String eq : sp) {
            String[] lr = eq.split("=");
            vars[i] = new variable(lr[0]);
            vals[i] = Double.parseDouble(lr[1]);
            i++;
        }
        return eval(vars, vals);
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

    public double eval(variable[] vars, double[] vals) {
        return 0;
    }

    public cons evalc(variable[] vars, double[] vals) {
        return null;
    }

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

    public func getReal() {
        return null;
    }

    public func getImaginary() {
        return null;
    }

    //re()+i*im()
    public func getComplex() {
        return getReal().add(cons.i.mul(getImaginary()));
    }

    //r*e^(i*a)
    public func getPolar() {
        func r = getReal();
        func im = getImaginary();
        func abs = r.pow(2).add(im.pow(2)).sqrt();
        func arg = new atan(im.div(r));
        return abs.mul(exp.make(cons.i.mul(arg)));
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
        else if (set.size() == 1) {
            return derivative(set.get(0));
        }
        else {
            throw new RuntimeException("specify a derivative variable");
        }
    }

    public func derivative(variable v) {
        throw new RuntimeException("not defined");
    }

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

    public func integrate(variable v) {
        throw new RuntimeException("integrate not defined");
    }

    public double integrate(Object lower, Object upper, Object var) {
        return new Integral(this, var, lower, upper).eval();
    }

    public double integrate(double a, double b, variable v) {
        return new Integral(this, v, new cons(a), new cons(b)).eval();
    }

    public double integrate(double a, double b) {
        return integrate(a, b, variable.x);
    }

    public func simplify() {
        return this;
    }

    public func signOther(int s) {
        sign *= s;
        return this;
    }

    //sign other function from this
    public func signOther(func other) {
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
        return (cons) o.signOther(sign);
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
        func x = new mul(this, f).simplify();
        return makeCons(x, f);
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
        return isConstant() && !asCons().functional && eval() == d;
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

    public func copy0() {
        return this;
    }

    @Override
    public String toString() {
        String str = toString2();
        if (sign == -1) {
            str = "-" + str;
        }
        return str;
    }

    public String toString2() {
        throw new RuntimeException("not printable");
    }

    public String toLatex() {
        return toString();
    }

    //print with parenthesis
    public String top() {
        if (fx) {
            return toString();
        }
        return "(" + toString() + ")";
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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof func)) return false;
        return eq((func) other);
    }

    public boolean eq(func other) {
        if (other != null && getClass() == other.getClass()) {
            return sign == other.sign && eq0(other);
        }
        return false;
    }

    //equal without sign
    public boolean eqws(func other) {
        if (other != null && getClass() == other.getClass()) {
            return eq0(other);
        }
        return false;
    }

    public boolean eq0(func f) {
        throw new RuntimeException("eq not defined");
    }

    public List<variable> vars() {
        Set<variable> set = new HashSet<>();
        vars(set);
        return new ArrayList<>(set);
    }

    public void vars(Set<variable> vars) {
    }

    public final func substitute(variable v, func f) {
        return signf(substitute0(v, f));
    }

    public final func substitute(String v, func f) {
        return substitute(new variable(v), f);
    }

    //x=y,n=5
    public final func substitute(String s) {
        func res = this;
        for (String sp : s.split(",")) {
            String[] lr = sp.split("=");
            variable v = new variable(lr[0]);
            func val = func.parse(lr[1]);
            res = substitute(v, val);
        }
        return res;
    }

    public func substitute0(variable v, func p) {
        return this;
    }

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

    public variable asVar() {
        return (variable) this;
    }

    public String latexParen() {
        return "\\left(" + toLatex() + "\\right)";
    }
}

