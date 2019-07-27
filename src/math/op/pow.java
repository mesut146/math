package math.op;

import math.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;
import math.funcs.ln;

public class pow extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return "("+a.toLatex()+")"+"^"+b.toLatex();
    }


    public pow(func f1, func f2){
        a=f1;
        b=f2;
		type=types.pow;
    }

    @Override
    public func get(Variable[] v, Constant[] c)
    {
		func p=a.get(v,c);
		func q=b.get(v,c);
        /*if(p.isConstant()&&q.isConstant()){
			//System.out.println("getif "+new Constant(Math.pow(p.eval(),q.eval())).s(sign));
			//System.out.println("s="+sign);
            return new Constant(Math.pow(p.eval(),q.eval())).s(sign);
        }*/
        return new pow(p,q).s(sign);
        //return p.pow(q).s(sign);
    }

	@Override
	public double eval(Variable[] v, double[] d)
	{
		//System.out.println("eval");
		return sign*Math.pow(a.eval(v,d),b.eval(v,d));
	}
	
    @Override
    public func derivative(Variable v)
    {
        /*if(a.isVariable()&&a.eq2(v)&&b.isConstant()){
            //x^n
            return b.mul(a.pow(b.sub(Constant.ONE)));
        }else if (a.isConstant()&&b.isVariable()&&b.eq2(v)){
            //n^x
            return mul(new ln(a).simplify());
        }*/
        if(a.isConstant()){//a^f=a^f*ln(a)*f'
            return a.pow(b).mul(new ln(a).simplify()).mul(b.derivative(v));
        }
        if(b.isConstant()){//f^b=b*f^(b-1)*f'
            return b.mul(a.pow(b.sub(1))).mul(a.derivative(v));
        }
        //f^g
        //System.out.println("a="+a.isConsfunc()+" b="+b+" ,"+new ln(a).simplify());
        /*
        func f=func.parse(String.format("%s*ln(%s)*%s^%s+%s*%s^(%s-1)*%s",b.derivative(),a,a,b,b,a,b,a.derivative()));
        System.out.println("f="+new ln(a).simplify());
        */
        return b.derivative(v).mul(new ln(a).simplify()).mul(this).add(b.mul(a.derivative(v)).mul(a.pow(b.sub(1))));
    }

    @Override
    public func integrate(Variable v)
    {
        if(a.eq(v)&&b.isConstant()){
            //x^n
            func t=b.add(1);
            return a.pow(t).div(t);
        }else if (a.isConstant()&&b.eq(v)){
            //n^x
            return div(new ln(a));
        }
        //f^g
        if(b.isConstant()){
            func du=a.derivative(v);//dx
            Variable u=new Variable("u");
            //System.out.println("du="+du);
            return u.pow(b).div(du).integrate(u).substitude0(u,a);
        }
        return new Anti(this);
    }
    
    @Override
    public String toString2()
    {
        StringBuffer sb=new StringBuffer();
        if ((!a.isConstant()&&!a.isVariable())||a.sign==-1){
            sb.append(a.top());
        }else{
            sb.append(a);
        }

        sb.append("^");

        if (!b.isConstant()&&!b.isVariable()||b.sign==-1){
            sb.append(b.top());
        }else sb.append(b);

        return sb.toString();
    }

    public func simplify(){
		//System.out.println("simp of pow");
        if(a.is(0)){
            return Constant.ZERO;
        }
		//System.out.println("a="+a+" b="+b);
        if(b.is(0)||a.is(1)){
			//System.out.println("y");
            return Constant.ONE.s(sign);
        }
        //e^ln(x)
        if(a.eq(Constant.E)&&b.type==types.ln){
            return b.a;
        }
        if(a.isConstant()&&b.isConstant()&&!a.isConsfunc()&&!b.isConsfunc()){
            return new Constant(this.get(0));
        }
        if (b.is(1)){
            return a.sign(sign);
        }
		if(a.isPow()){
			return a.a.pow(a.b.mul(b));
		}
		if(a.sign==-1&&b.isConstant()){
			if(b.eval()%2==0){
				a.sign=1;
			}else{
				sign*=a.sign;
				a.sign=1;
			}
		}
		//System.out.println("a2="+a+" b2="+b);
        return this;
    }

	@Override
	public boolean eq2(func f)
	{
		if(a.eq(f.a)&&b.eq(f.b)){
			return true;
		}
		return false;
	}

    @Override
    public func substitude0(Variable v, func p)
    {
        return a.substitude(v,p).pow(b.substitude(v,p));
    }

    @Override
    public func copy0() {
        return new pow(a.copy(),b.copy());
    }
}
