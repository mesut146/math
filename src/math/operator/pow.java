package math.operator;

import math.*;
import math.core.*;
import math.funcs.*;
import java.util.*;

public class pow extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return "(" + a.toLatex() + ")" + "^" + b.toLatex();
    }
    
    @Override
    public void vars0(Set<var> vars)
    {
        a.vars0(vars);
        b.vars0(vars);
    }

    public pow(func f1, func f2)
    {
        a = f1;
        b = f2;
		type = types.pow;
        //System.out.println("pow created="+this);
    }

    @Override
    public func get0(var[] v, cons[] c)
    {
		func p=a.get0(v, c);
		func q=b.get0(v, c);
        /*if(p.isConstant()&&q.isConstant()){
         //System.out.println("getif "+new Constant(Math.pow(p.eval(),q.eval())).s(sign));
         //System.out.println("s="+sign);
         return new Constant(Math.pow(p.eval(),q.eval())).s(sign);
         }*/
        return signto(p.pow(q));
        //return p.pow(q).s(sign);
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		//System.out.println("eval");
		return sign * Math.pow(a.eval(v, d), b.eval(v, d));
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        //System.out.println("evalc="+s(a.evalc(v,d).pow(b.evalc(v,d))));
        return sc(a.evalc(v, d).pow(b.evalc(v, d)));
    }

    @Override
    public func derivative(var v)
    {
        /*if(a.isVariable()&&a.eq2(v)&&b.isConstant()){
         //x^n
         return b.mul(a.pow(b.sub(Constant.ONE)));
         }else if (a.isConstant()&&b.isVariable()&&b.eq2(v)){
         //n^x
         return mul(new ln(a).simplify());
         }*/
        if (a.isConstant())
        {//a^f=a^f*ln(a)*f'
            return signto(a.pow(b).mul(new ln(a).simplify()).mul(b.derivative(v)));
        }
        if (b.isConstant())
        {//f^b=b*f^(b-1)*f'
            return signto(b.mul(a.pow(b.sub(1))).mul(a.derivative(v)));
        }
        //f^g
        //System.out.println("a="+a.isConsfunc()+" b="+b+" ,"+new ln(a).simplify());
        /*
         func f=func.parse(String.format("%s*ln(%s)*%s^%s+%s*%s^(%s-1)*%s",b.derivative(),a,a,b,b,a,b,a.derivative()));
         System.out.println("f="+new ln(a).simplify());
         */
        return signto(b.derivative(v).mul(new ln(a).simplify()).mul(this).add(b.mul(a.derivative(v)).mul(a.pow(b.sub(1)))));
    }

    @Override
    public func integrate(var v)
    {
        if (a.eq(v) && b.isConstant())
        {
            //x^n
            func t=b.add(1);
            return a.pow(t).div(t);
        }
        else if (a.isConstant() && b.eq(v))
        {
            //n^x
            return div(new ln(a));
        }
        //f^g
        if (b.isConstant())
        {
            func du=a.derivative(v);//dx
            var u=new var("u");
            //System.out.println("du="+du);
            return u.pow(b).div(du).integrate(u).substitude0(u, a);
        }
        return new Integral(this);
    }

    @Override
    public String toString2()
    {
        StringBuffer sb=new StringBuffer();
        if ((!a.isConstant() && !a.isVariable()) || a.sign == -1)
        {
            sb.append(a.top());
        }
        else
        {
            sb.append(a);
        }

        sb.append("^");

        if (!b.isConstant() && !b.isVariable() || b.sign == -1)
        {
            sb.append(b.top());
        }
        else sb.append(b);

        return sb.toString();
    }

    public func simplify()
    {
		//System.out.println("simp of pow="+this);
        if (!Config.pow.simplify)
        {
            return this;
        }
        if (a.is(0))
        {
            return cons.ZERO;
        }
		//System.out.println("a="+a+" b="+b);
        if (b.is(0) || a.is(1))
        {
			//System.out.println("y");
            return signto(cons.ONE);
        }
        //e^ln(x)
        if (a.eq(cons.E) && b.type == types.ln)
        {
            return b.a;
        }
        //System.out.println("hh="+this);
        if (Config.pow.simpCons && a.isCons0() && b.isCons0())
        {

            if (Config.useBigDecimal)
            {
                return this.evalc();
            }
            return new cons(this.eval());
            
        }
        if (a.isConstant() && b.isConstant())
        {
            return new cons(this);
        }
        if (b.is(1))
        {
            return a.sign(sign);
        }
		if (a.isPow())
        {
			return a.a.pow(a.b.mul(b));
		}
		if (a.sign == -1 && b.isConstant())
        {
			if (b.eval() % 2 == 0)
            {
				a.sign = 1;
			}
            else
            {
				sign *= a.sign;
				a.sign = 1;
			}
		}
		//System.out.println("a2="+a+" b2="+b);
        return this;
    }

	@Override
	public boolean eq2(func f)
	{
		if (a.eq(f.a) && b.eq(f.b))
        {
			return true;
		}
		return false;
	}

    @Override
    public func substitude0(var v, func p)
    {
        return a.substitude(v, p).pow(b.substitude(v, p));
    }

    @Override
    public func copy0()
    {
        return new pow(a.copy(), b.copy());
    }
}
