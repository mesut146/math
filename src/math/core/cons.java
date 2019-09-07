package math.core;
import math.cons.*;
import java.math.*;
import math.*;

public class cons extends func
{

    public static final cons ZERO=new cons(0);
    public static final cons ONE=new cons(1);
	public static final cons TWO=new cons(2);
	public static final cons NaN;
    public static final cons INF;
    public static final cons E=new e();
    public static final cons PI=new pi();
    public boolean functional=false;
    protected func ff;
    public double val=0;
    public BigDecimal big;
    boolean nan=false,inf=false;
    public static MathContext mc=new MathContext(Config.precision);
	//public static final Constant PI2,PI3,PI4,PI5,PI6;

    static{
        NaN=new cons();
        NaN.nan=true;
        INF=new cons();
        INF.inf=true;
        INF.val=Double.MAX_VALUE;
		/*PI2=(Constant)PI.div(2);
		PI3=(Constant)PI.div(3);
		PI4=(Constant)PI.div(4);
		PI5=(Constant)PI.div(5);
		PI6=(Constant)PI.div(6);
		*/
    }
    
    public cons(double d){
        if(d<0){
            sign=-1;
            d=-d;
        }
        val=d;
        big=new BigDecimal(val);
        type=types.constant;
    }
    public cons(func f){
        ff=f;
        functional=true;
        type=types.constant;
    }
    public cons(BigDecimal b){
        type=types.constant;
        if(b.signum()==-1){
            b=b.negate();
            sign=-1;
        }
        big=b;
        //val=big.doubleValue();
    }

    public cons(){
        type=types.constant;
    }

    @Override
    public func copy0() {
        /*if (functional){
            return ff.copy0();
        }*/
        return new cons(val);
    }

    @Override
    public String toLatex()
    {
        if (functional){
            return a.toLatex();
        }
        if(nan){
            return "NaN";
        }
        if(inf){
            return "inf";
        }
        if(Config.useBigDecimal){
            return big.toString();
        }
        if(isInteger()){
            return Integer.toString((int)val);
        }
        return Double.toString(val);
    }

    @Override
    public func get(var[] v, cons[] c)
    {
        if(functional){
			if(ff==null){
				return new cons(val).sign(sign);
			}
            //System.out.println("ff="+ff);
            //return ff.get(v, c).s(sign);
        }
        return this;
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		if(functional){//shouldn't happen since ff has eval already
            //System.out.println("ff="+ff);
            return sign*ff.eval(v,d);
        }
        return sign*val;
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        if(functional){//shouldn't happen since ff has eval already
            //System.out.println("ff="+ff);
            return (cons)signto(ff.evalc(v,d));
        }
        return this;
    }

    @Override
    public func add(func f)
    {
        if(f.isCons0()&&Config.useBigDecimal){
            BigDecimal bd=decimal().add(f.cons().decimal(),mc);
            return new cons(bd);
        }
        return super.add(f);
    }

    @Override
    public func mul(func f)
    {
        //(6+x)*4567
        if(f.isCons0()&&Config.useBigDecimal){
            BigDecimal bd=decimal().multiply(f.cons().decimal(),mc);
            return new cons(bd);
        }
        return super.mul(f);
    }

    @Override
    public func pow(func f)
    {
        if(f.isCons0()&&Config.useBigDecimal){
            
            BigDecimal bd=decimal().pow(f.cons().decimal().intValue(),mc);
            //System.out.println("in cpow="+this+" f="+f);
            //System.out.println("bd="+new Constant(bd));
            return new cons(bd);
        }
        return super.pow(f);
    }
    
    
    
    public BigDecimal decimal(){
        if(sign==-1){
            return big.negate();
        }
        return big;
    }
    
    
	public boolean isInteger(){
        if (!functional){
            return val==(int)val;
        }
        return false;
    }

    public boolean isInf(){
        return inf;
    }
    public boolean isBig(){
        return big!=null;
    }

    @Override
    public func derivative(var v)
    {
        return ZERO;
    }

    @Override
    public func integrate(var v)
    {
        return mul(v);
    } 

    @Override
    public String toString2()
    {
        if(nan){
            return "NaN";
        }
        if(inf){
            return "inf";
        }
        if(functional){
            return ff.toString();
        }
        if(Config.useBigDecimal){
            return big.toString();
        }
        if(isInteger()){
            return Integer.toString((int)val);
        }
        
        return Double.toString(val);
    }

    public cons from(double d){
        return new cons(d);
    }
	
	public void a(){
		System.out.println(getClass());
		System.out.println(getClass().getSuperclass());
	}

	@Override
	public boolean eq2(func f)
	{
		/*if(getClass().getSuperclass()!=func.class){
			
		}*/
        //System.out.println("this="+this+" that="+f);
        if(functional||((cons)f).functional){
            //return ff.eq(((Constant)f).ff);
            //System.out.println(getClass()==f.getClass());
			return getClass()==f.getClass();
        }
		if(val==f.eval()){
			return true;
		}
		return false;
	}

    @Override
    public func substitude0(var v, func p)
    {
        return this;
    }

    
    
}
