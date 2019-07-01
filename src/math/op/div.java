package math.op;


import math.Constant;
import math.Variable;
import math.func;

public class div extends func {

    public div(func f1, func f2){
        a=f1;
        b=f2;
    }
    @Override
    public func get(Variable v, Constant c) {
        return a.get(v,c).div(b.get(v,c));
    }

    @Override
    public double get2(Variable v, double d) {
        return 0;
    }

    @Override
    public String toLatex() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("\\frac{%s}_{%s}",a,b));
        return sb.toString();
    }

    @Override
    public func derivative(Variable v) {
        return null;
    }

    @Override
    public func integrate(Variable v) {
        return null;
    }

    @Override
    public func copy0() {
        return new div(a,b);
    }

    @Override
    public String toString2() {
        return a.top()+"/"+b.top();
    }

    @Override
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public func substitude0(Variable v, func p) {
        return null;
    }
}
