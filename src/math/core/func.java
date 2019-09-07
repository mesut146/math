package math.core;

import math.Config;
import math.operator.*;
import java.util.*;

import math.parser.Parser;
import math.funcs.*;
import math.sigma;
import math.*;
import java.util.regex.*;
import javax.xml.validation.*;
import java.security.spec.*;

public abstract class func
{

    public enum types
    {
		func,fx,constant,variable,add,sub,mul,div,pow,ln,sin,cos;

	}
    public types type=types.func;
    public int sign=1;
	public boolean fx=false;
    public func a=null,b=null,last=this;
	public List<func> f=new LinkedList<>();
	public List<func> alter=new LinkedList<>();
	public static HashMap<func,func> rules=new HashMap<>();
	//public HashMap<func,func> rules=new HashMap<>();
    //static boolean simplifyAdd=false,simplifyMul=false;

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

    public String d()
    {
        return getClass() + " " + toString();
    }

	//public abstract fx find(String name);
	/*protected String ss(){
     return sign==-1?"-":"";
     }*/

    public void set(List<func> l)
    {
        f.clear();
        f.addAll(l);
    }


    public final func get(cons c)
    { 
		//System.out.println("fget(c)="+c);
		return get(var.x, c); 
	}
    public final func get(double d)
    {
		//System.out.println("fget(d)="+d);
        return get(var.x, new cons(d));
    }
    public final func get(var v, double d)
    {
        //System.out.println("fget(v,d)="+v+","+d);
        return get(v, new cons(d));
    }
    public final func get(var[] v, double[] d)
    {
        cons[] c=new cons[d.length];
        for (int i=0;i < c.length;i++)
        {
            c[i] = new cons(d[i]);
        }
        return get(v, c);
    }
    public final func get(String s)
    {
        String[] sp=s.split(",");
        var[] va=new var[sp.length];
        cons[] ca=new cons[sp.length];
        int i=0;
        for (String eq:sp)
        {
            String[] lr=eq.split("=");
            va[i] = new var(lr[0]);
            ca[i] = new cons(Double.parseDouble(lr[1]));
            i++;
        }
        return get(va, ca);
    }

	public func get(var v, cons c)
    {
    	return get(new var[]{v}, new cons[]{c});
	}


	public double eval()
	{
		//System.out.println("fget this="+this);
		return eval(var.x, 0);
	}

	public final double eval(String s, double...d)
    {
        if (d.length == 0)
        {
            Pattern p=Pattern.compile("(\\w)+=(\\d+(\\.\\d+)?)");
            Matcher m=p.matcher(s);
            List<var> vl=new ArrayList<>();
            List<Double> dl=new ArrayList<>();
            while (m.find())
            {
                vl.add(new var(m.group(1)));
                dl.add(Double.parseDouble(m.group(2)));
                //System.out.println(m.group(1));
                //System.out.println(m.group(2));

            }
            return eval(vl.toArray(new var[0]), cast(dl));
        }
        var[] v=new var[d.length];
        String[] sp=s.split(",");
        for (int i=0;i < d.length;i++)
        {
            v[i] = new var(sp[i]);
        }
		return eval(v, d);
	}

    double[] cast(List<Double> list)
    {
        double[] r=new double[list.size()];

        for (int i=0;i < r.length;i++)
        {
            r[i] = list.get(i);
        }
        return r;
    }
    public abstract func get(var[] v, cons[] c);
	public abstract double eval(var[] v, double[] d);
    public abstract cons evalc(var[] v, double[] d);

    public cons evalc()
    {
        return evalc(new var[]{}, new double[]{});
    }
	public double eval(var v, double d)
    {
		return eval(new var[]{v}, new double[]{d});
	}

	public double eval(double d)
    {
		return eval(var.x, d);
	}

    public abstract String toLatex();

    public final func derivative()
    {return derivative(var.x);}
    public abstract func derivative(var v);
    public final func derivative(String s)
    {return derivative(new var(s));}

    public final func integrate()
    {return integrate(var.x);}
	public abstract func integrate(var v);

	public double integrate(double a, double b, var v)
    {
        return new Integral(this,v,new cons(a),new cons(b)).eval();
	}
	public double integrate(double a, double b)
    {
		return integrate(a, b, var.x);
	}

	public func derivative(int n)
    {
		return derivative(n, var.x);
	}
    public func derivative(int n, String v)
    {
        return derivative(n, var.from(v));
    }
    public func derivative(int n, var v)
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

	/*public static func sign(func f, int s)
     {
     func z=f.copy();
     z.sign *= s;
     return z;
     }*/

	public func sign(int s)
    {
        sign *= s;
        return this;
	}
    public func signget(func f){
        sign*=f.sign;
        return this;
    }
    public func signto(func o)
    {
        o.sign *= sign;
        return o;
    }
    
    
    public cons sc(func o)
    {
        return (cons)o.sign(sign);
    }
    public func add(func f)
    {
        func x=new add(this, f);
        if (Config.add.simplify)
        {
            x = x.simplify();
        }
        return x;
    }
	public func add(double d)
    { 
        return add(new cons(d)); 
    }
    public func sub(func f)
    {
        func x=new add(this, f.negate());
        if (Config.add.simplify)
        {
            x = x.simplify();
        }
        return x;
	}
	public func sub(double d)
    {
        return sub(new cons(d));
		
	}
    public func mul(func f)
    {
        if (f instanceof poly)
        {
            return f.mul(this);
        }
        if (f.is(1))
        {
            return this;
        }
        else if (f.is(0))
        {
            return cons.ZERO;
        }
        func x=new mul(this, f);
        if (Config.mul.simplify)
        {
            x = x.simplify();
        }
        return x;
        //return new mul(this, f).simplify(); 
    }
	public func mul(double d)
    {
        return mul(new cons(d));
    }
    public func div(func f)
    {
        //return new mul(this, f.pow(-1)).simplify();
        return new div(this, f).simplify();
    }
	public func div(double d)
    {
		return div(new cons(d));
	}
    public func pow(func f)
    { return new pow(this, f).simplify(); }
	public func pow(double d)
    {
		return new pow(this, new cons(d)).simplify();
	}

	public func fac()
    {
		return new fac(this);
	}

	public cons cons()
    {
		return (cons)this;
	}

    public boolean is(double d)
    {
		//System.out.println(cons().functional);
        return isConstant() && !cons().functional && eval() == d;
    }

    public String getType()
    {
        return type.toString();
    }

    public func negate()
    {
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
    public String toString()
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

    public int find(types t)
    {
        int i=0;
        for (func p:f)
        {
            if (p.type == t)
            {
                return i;
            }
            i++;
        }
        return -1;
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
    public boolean isCons0()
    {
        return isConstant() && !cons().functional;
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
        return type == types.add ||
            type == types.sub ||
            type == types.mul ||
            type == types.div ||
            type == types.pow;
		//return type.val >= types.add.val && type.val <= types.pow.val;
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
     y=x.sub(p.eval(x.eval()));
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
     //y=x-p.eval(x);
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

		if (f != null && type == f.type)
        {
			return sign == f.sign && eq2(f);
		}
		return false;
	}
    public boolean eqws(func f)
    {

        if (f != null && type == f.type)
        {
            return eq2(f);
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

	public func substitude(var v, func f)
    {
		return signto(substitude0(v, f));
	}
    public abstract func substitude0(var v, func p);

	public String taylor()
    {
		StringBuffer s=new StringBuffer();
		func p=this;
        //func arr=new add();
		s.append(p.eval(0));
        //arr=arr.add(p.eval(0));
		for (int i=1;i < 5;i++)
        {
			p = p.derivative();
			s.append("+" + p.eval(0) + "*x");
            //arr.add(new Constant(p.eval(0)).mul(Variable.x.pow(i)).div(new fac(i)));
			if (i != 1)
            {
				s.append("^" + i);
			}
            s.append("/").append(new fac(i));
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

