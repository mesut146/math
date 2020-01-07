package math.core;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.*;
import math.*;
import math.funcs.*;
import math.operator.*;
import math.parser.*;
import math.trigonometry.*;

public abstract class func
{

    public enum types
    {
		func,fx,constant,variable,add,sub,mul,div,pow,ln,sin,cos;

	}
    public types type=types.func;
    public int sign=1;
	public boolean fx=false;
    public func a=null,b=null;
	public List<func> f=new ArrayList<>();
	public List<func> alter=new ArrayList<>();
    public static Map<String,Class<?>> map=new HashMap<>();
	public static HashMap<func,func> rules=new HashMap<>();
	//public HashMap<func,func> rules=new HashMap<>();

    static{
        /*if(map.size()==0){

        }*/
        Config.init();
    }
    
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

	//TODO make args Object autocast
	public static func makeFunc(String name,List<func> args){
        Class<func> c;
        Constructor<func> co;
        func res=null;
        if(map.containsKey(name)){
            try
            {
                c= (Class<func>) map.get(name);
                if(args.size()==1){
                    co=c.getDeclaredConstructor(func.class);
                    res=co.newInstance(args.get(0));
                }else{
                    //System.out.println("args="+args);
                    Class<?>[] arr= new Class<?>[args.size()];
                    for (int i=0;i<args.size();i++){
                        arr[i]=func.class;
                    }
                    co=c.getDeclaredConstructor(arr);
                    res=co.newInstance(args.toArray(new func[args.size()]));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
                //System.out.println(this);
            }
        }else{
            if(math.core.fx.has(name)){
                res=math.core.fx.getFx(name);
            }else{
                res=new fx(name,args.toArray(new func[args.size()]));
            }
        }
        return res;
    }

    //register func name and its class ex. (sin,math.trigo.sin)
    public static void register(String fname,Class<? extends func> cls){
        //System.out.println(fname);
        map.put(fname,cls);
        
    }
    
    public func sqrt(){
        return new sqrt(this);
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
        l.clear();
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
        return get0(v, c);
    }

    public final func get(var[] v, cons[] c)
    {
        func f=sign(get0(v, c));
        return f.simplify();  
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
        return get0(va, ca);
    }

	public func get(var v, cons c)
    {
    	return get0(new var[]{v}, new cons[]{c});
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
    public abstract func get0(var[] v, cons[] c);
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
        List<var> lv=vars();
        if(lv.size()==0||lv.contains(var.x)){
            return eval(var.x,d);
        }
		return eval(lv.get(0), d);
	}
    
    //multiplication inverse
    public func inv(){
        return cons.ONE.div(this);
    }
    
    //a
    public abstract func getReal();
    //b
    public abstract func getImaginary();
    //a+b*i
    public func getComplex(){
        return getReal().add(cons.i.mul(getImaginary()));
    }
    //r*e^(i*a)
    public func getPolar(){
        func r=getReal();
        func im=getImaginary();
        func abs=r.pow(2).add(im.pow(2)).sqrt();
        func arg=new atan(im.div(r));
        return abs.mul(new exp(cons.i.mul(arg)));
    }// |r|
    public func getAbs(){
        func r=getReal();
        func im=getImaginary();
        return r.pow(2).add(im.pow(2)).sqrt();
    }//theta
    public func getArg(){
        return new atan(getImaginary().div(getReal()));
    }
    public boolean isReal(){
        return getComplex().is(0);
    }
    public boolean isComplex(){
        return !getComplex().is(0);
    }

    public final func derivative()
    {return derivative(var.x);}
    public abstract func derivative(var v);
    public final func derivative(String s)
    {return derivative(new var(s));}
    public func derivative(int n)
    {
        return derivative(n, var.x);
    }
    public func derivative(int n,Object ov)
    {
        var v=Util.var(ov);
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
    

    public final func integrate()
    {return integrate(var.x);}
	public abstract func integrate(var v);

	public double integrate(double a, double b, var v)
    {
        return new Integral(this, v, new cons(a), new cons(b)).eval();
	}
	public double integrate(double a, double b)
    {
		return integrate(a, b, var.x);
	}

	public func simplify(){return this;}

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
    public func sign(func f){
        f.sign*=sign;
        return f;
    }
    public func signf(func f){
        func c=f.copy();
        c.sign*=sign;
        return c;
    }
    public cons sc(func o)
    {
        return (cons)o.sign(sign);
    }

    //if both are cons the result should be boxed
    func makeCons(func x,func f)
    {
        if (isConstant() && f.isConstant())
        {
            //return new cons(x);
        }
        return x;
    }

    public func add(func f)
    {
        func x=new add(this, f);
        if (Config.add.simplify)
        {
            x = x.simplify();
        }
        return makeCons(x,f);
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
        return makeCons(x,f);
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
        return makeCons(x,f);
        //return new mul(this, f).simplify(); 
    }
	public func mul(double d)
    {
        return mul(new cons(d));
    }
    public func div(func f)
    {
        func x=new div(this, f);
        if(Config.div.simplify){
            x=x.simplify();
        }
        return makeCons(x,f);
    }
	public func div(double d)
    {
		return div(new cons(d));
	}
    public func pow(func f)
    { 
        func x=new pow(this, f); 
        if (Config.pow.simplify)
        {
            x = x.simplify();
        }
        return makeCons(x,f);
    }
	public func pow(double d)
    {
		return pow(new cons(d));
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
		return new ArrayList<func>();
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
    public abstract String toLatex();
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

    @Override
    public boolean equals(Object o)
    {
        if(o!=null&&(o instanceof func)){
            return eq((func)o);
        }
        return false;
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
    
    public List<var> vars(){
        Set<var> s=new HashSet<>();
        vars0(s);
        return new ArrayList<var>(s);
    }
    
    public abstract void vars0(Set<var> vars);

	public static func parse(String s)
    {
		return Parser.parse(s).simplify();
	}

	public func substitude(var v, func f)
    {
		return signf(substitude0(v, f));
	}
    public abstract func substitude0(var v, func p);

    //center,terms
	public taylor taylor(double at,int n)
    {
		return taylor.numeric(this,at,n);
	}
    //var,center,terms
    public taylorsym taylorsym(var v,func at,int n){
        return taylorsym.symbol(this,v,at,n);
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

