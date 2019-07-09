package math.core;
import math.cons.*;

public class Constant extends func
{

    public static final Constant ZERO=new Constant(0);
    public static final Constant ONE=new Constant(1);
	public static final Constant TWO=new Constant(2);
	public static final Constant NaN;
    public static final Constant INF;
    public static final Constant E=new e();
    public static final Constant PI=new pi();
    public boolean functional=false;
    protected func ff;
    public double val=0;
    boolean nan=false,inf=false;
	//public static final Constant PI2,PI3,PI4,PI5,PI6;

    static{
        NaN=new Constant();
        NaN.nan=true;
        INF=new Constant();
        INF.inf=true;
        INF.val=Double.MAX_VALUE;
		/*PI2=(Constant)PI.div(2);
		PI3=(Constant)PI.div(3);
		PI4=(Constant)PI.div(4);
		PI5=(Constant)PI.div(5);
		PI6=(Constant)PI.div(6);
		*/
    }


    
    public Constant(double d){
        if(d<0){
            sign=-sign;
            d=-d;
        }
        val=d;
        type=types.constant;
    }
    public Constant(func f){
        ff=f;
        functional=true;
        type=types.constant;
    }

    public Constant(){
        type=types.constant;
    }

    @Override
    public func copy0() {
        /*if (functional){
            return ff.copy0();
        }*/
        return new Constant(val);
    }

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return toString();
    }

    @Override
    public func get(Variable[] v, Constant[] c)
    {
        if(functional){
			if(ff==null){
				return new Constant(val).s(sign);
			}
            //System.out.println("ff="+ff);
            //return ff.get(v, c).s(sign);
        }
        return this;
    }

	@Override
	public double eval(Variable[] v, double[] d)
	{
		if(functional){
            //System.out.println("ff="+ff);
            return sign*ff.eval(v,d);
        }
        return sign*val;
	}

    public boolean isInf(){
        return inf;
    }

    @Override
    public func derivative(Variable v)
    {
        return ZERO;
    }

    @Override
    public func integrate(Variable v)
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
        if((double)(int)val==val){
            return Integer.toString((int)val);
        }
        return Double.toString(val);
    }

    public Constant from(double d){
        return new Constant(d);
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
        if(functional||((Constant)f).functional){
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
    public func substitude0(Variable v, func p)
    {
        return this;
    }

    
    
}
