package math.core;

import math.op.*;
import java.util.*;

import math.parser.Parser;
import math.funcs.*;
import math.sigma;

public abstract class func
{

    public enum types
    {
		func(0),constant(1),variable(2),add(3),sub(4),mul(5),div(6),pow(7),ln(8),sin(10),cos(11);
		public int val;
		types(int i){
			val=i;
		}
	}
    public types type=types.func;
    public int sign=1;
	public boolean fx=false;
    public func a=null,b=null,last=this;
	public List<func> f=new LinkedList<>();
	public List<func> alter=new LinkedList<>();
	public static HashMap<func,func> rules=new HashMap<>();
	//public HashMap<func,func> rules=new HashMap<>();
    static boolean simplifyAdd=false,simplifyMul=false;

	public static void addRule(func a, func b)
    {
		rules.put(a, b);
	}
	public static void addRule(String s)
    {
		String sp[]=s.split("=");
		func a=parse(sp[0]);
		func b=parse(sp[1]);
		if (b.name().equals("fx"))
        {
			//a=a.substitude();
		}
		addRule(a, b);
	}

	//public abstract fx find(String name);
	/*protected String ss(){
     return sign==-1?"-":"";
     }*/

    public double get()
    {
        //System.out.println("fget this="+this);
        return get2(Variable.x, 0);
    }
    public final func get(Constant c)
    { 
		//System.out.println("fget(c)="+c);
		return get(Variable.x, c); 
	}
    public final func get(double d)
    {
		//System.out.println("fget(d)="+d);
        return get(Variable.x, new Constant(d));
    }
	public final double get2(String s, double d)
    {
		return get2(new Variable(s), d);
	}
    public final func get(Variable v, double d)
    {
		//System.out.println("fget(v,d)="+v+","+d);
        return get(v, new Constant(d));
    }

    public abstract func get(Variable v, Constant c);
	public abstract double get2(Variable v, double d);

	public double get2(double d)
    {
		return get2(Variable.x, d);
	}

    public abstract String toLatex();

    public final func derivative()
    {return derivative(Variable.x);}
    public abstract func derivative(Variable v);
    public final func derivative(String s)
    {return derivative(new Variable(s));}

    public final func integrate() {return integrate(Variable.x);}
	public abstract func integrate(Variable v);

	public double integrate(double a, double b)
    {
		double n=1000.0;
		double h=(b - a) / n;
		double sum=0;
		sigma s=new sigma(this.substitude0(Variable.x, func.parse(a + "+i*" + h).simplify()), new Variable("i"), 0, (int)n);
		sum = s.get() * h;
		return sum;
	}

	public func der(int n)
    {
		return der(n, Variable.x);
	}
    public func der(int n, Variable v)
    {
        if (n < 1)
        {
            return this;
        }
        func d=this;
        for (int i=0;i < n;i++)
        {
            d = d.derivative(v);
        }
		return d;
    }

	public func simplify()
    {return this;}

	//public func(){}

	public static func sign(func f, int s)
    {
		func z=f.copy();
		z.sign *= s;
		return z;
	}
	public func sign(int s)
    {
		return sign(this, s);
	}
	public func s(int s)
    {
		return sign(this, s);
	}
    public func add(func f)
    {
        func x=new add(this, f);
        if (simplifyAdd)
        {
            x = x.simplify();
        }
        return x;
    }
	public func add(double d)
    { 
        return add(new Constant(d));
        //return new add(this,new Constant(d)).simplify(); 
    }
    public func sub(func f)
    {
        func x=new add(this, f.negate());
        if (simplifyAdd)
        {
            x = x.simplify();
        }
        return x;
		//return new add(this, f.negate()).simplify();
	}
	public func sub(double d)
    {
        return sub(new Constant(d));
		//return new add(this, new Constant(-d)).simplify();
	}
    public func mul(func f)
    {
        if (f instanceof poly)
        {
            return f.mul(this);
        }
        func x=new mul(this, f);
        if (simplifyMul)
        {
            x = x.simplify();
        }
        return x;
        //return new mul(this, f).simplify(); 
    }
	public func mul(double d)
    {
        return mul(new Constant(d));
        //return new mul(this, new Constant(d)).simplify();
    }
    public func div(func f)
    {
        return new mul(this, f.pow(-1)).simplify();
    }
	public func div(double d)
    {
		return new mul(this, new Constant(1.0 / d)).simplify();
	}
    public func pow(func f)
    { return new pow(this, f).simplify(); }
	public func pow(double d)
    {
		return new pow(this, new Constant(d)).simplify();
	}

	public func fac()
    {
		return new fac(this);
	}

	public Constant cons()
    {
		return (Constant)this;
	}

    public boolean is(double d)
    {
		//System.out.println(cons().functional);
        return isConstant() && !cons().functional && get() == d;
    }

    public String getType()
    {
        return type.toString();
    }

    public func negate()
    {
		//System.out.println("negate func");
        func c=copy();
        c.sign = -c.sign;
        return c;
    }

	public final func copy()
    {
		func f=copy0();
		f.sign = sign;
		return f;
	}

    public abstract func copy0();

	public static List<func> getFree()
    {
		return new LinkedList<func>();
	}

    @Override
    public final String toString()
    {
        String s=toString2();
        if (sign == -1)
        {
            s = "-" + s;
        }
        return s;
    }
	public abstract String toString2();
    public String top()
    {
		if (fx)
        {
			return toString();
		}
        return "(" + toString() + ")";
    }

    public boolean isNumber()
    {
        return isConstant() && !isConsfunc();
    }
	public boolean isConstant()
    {
		return type == types.constant;
	}
    public boolean isConsfunc()
    {
        return isConstant() && cons().functional;
    }
	public boolean isVariable()
    {
		return type == types.variable;
	}
	public boolean isAdd()
    {
		return type == types.add;
	}
	public boolean isSub()
    {
		return type == types.sub;
	}
	public boolean isMul()
    {
		return type == types.mul;
	}
	public boolean isDiv()
    {
		return type == types.div;
	}
	public boolean isPow()
    {
		return type == types.pow;
	}
	public boolean isOperator()
    {
		return type.val >= types.add.val && type.val <= types.pow.val;
	}
    public boolean isPolinom()
    {
        return isPow() && a.isVariable() && b.isConstant();
    }
    public boolean isPower()
    {
        return isPow() && b.isVariable() && a.isConstant();
    }

    /*public funcs root(){
     funcs x=Constant.ONE,y;
     funcs p=this.div(this.derivative());
     int i=0,max=1000;
     double eps=0.000000000000001;
     while(i<max){
     y=x.sub(p.get(x.get()));
     System.out.println(y);
     if(Math.abs(x-y)<=eps){
     break;
     }
     x=y;
     i++;
     }
     return x;
     }

     public BigDecimal root2(int len){
     BigDecimal x=BigDecimal.ONE,y;
     funcs p=this.div(this.derivative());
     int i=0,max=1000;
     BigDecimal eps=BigDecimal.TEN.pow(-len);
     MathContext mc=new MathContext(len);
     while(i<max){
     //y=x-p.get(x);
     y=x.subtract(null,mc);
     if(x.subtract(y,mc).abs(mc).compareTo(eps)<=0){

     break;
     }
     x=y;
     i++;
     }
     return x;
     }*/

	public String name()
    {
		String s=getClass().getName();
		return s.substring(s.lastIndexOf(".") + 1);
	}

	public boolean eq(func f)
	{
		//System.out.println("this="+this+" f="+f);
		if (name().equals("fx"))
        {
			if (f.name().equals("fx"))
            {
				return eq2(f);
			}
            /*b=f;
             return true;*/
		}
		if (f.name().equals("fx"))
        {
			((math.fx)f).b = this;
			//System.out.println("a="+f);
			return true;
		}
		if (f != null && type == f.type)
        {
			return sign == sign && eq2(f);
		}
		return false;
	}
	public abstract boolean eq2(func f);

	public static boolean isEq(List<func> l1, List<func> l2)
    {
		int l;
		if ((l = l1.size()) != l2.size())
        {
			return false;
		}
		boolean b[]=new boolean[l];
		main:
		for (int i=0;i < l;i++)
        {
			func p=l1.get(i);
			for (int j=0;j < l;j++)
            {
				if (!b[j] && p.eq(l2.get(j)))
                {
					b[j] = true;
					continue main;
				}
			}
            return false;
		}
		return true;
	}

	public static func parse(String s)
    {
		//System.out.println("s="+s);
		return Parser.parse(s).simplify();
	}

	public func substitude(Variable v, func f)
    {
		return substitude0(v, f).s(sign);
	}
    public abstract func substitude0(Variable v, func p);

	public String taylor()
    {
		StringBuffer s=new StringBuffer();
		func p=this;
		s.append(p.get(0));
		for (int i=1;i < 5;i++)
        {
			p = p.derivative();
			s.append("+" + p.get(0).div(new fac(i)) + "*x");
			if (i != 1)
            {
				s.append("^" + i);
			}
		}
		return s.toString();
	}

	static int fac(int p)
    {
		int r=1;
		for (int i=2;i <= p;i++)
        {
			r *= i;
		}
		return r;
	}


}

