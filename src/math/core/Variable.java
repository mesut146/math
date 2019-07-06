package math.core;

public class Variable extends func
{
    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return toString();
    }

    public static final Variable x=new Variable("x");
    public static final Variable y=new Variable("y");
    public static final Variable z=new Variable("z");
	public static final Variable t=new Variable("t");
	public static final Variable u=new Variable("u");
    char c;
    
    public Variable(){
        this('x');
    }
    public Variable(String s){
        this(s.charAt(0));
    }
    public Variable(char s){
        this.type=types.variable;
        c=s;
    }
	
	public static Variable from(String s){
		return new Variable(s);
	}
    
    @Override
    public func get(Variable[] v, Constant[] c)
    {
        for(int i=0;i<v.length;i++){
            if(eq2(v[i])){
                //TODO: mutable or not?
                return new Constant(c[i]).s(sign);
            }
        }

        return Constant.ZERO;
    }
	
	@Override
    public double eval(Variable[] v, double[] d)
    {
        for(int i=0;i<v.length;i++){
            if(eq2(v[i])){
                return d[i]*sign;
            }
        }

        return 0;
    }

    @Override
    public func derivative(Variable v)
    {
        if(eq2(v)){
            return Constant.ONE;
        }
        return Constant.ZERO;
    }

    @Override
    public func integrate(Variable v)
    {
        if (eq2(v)) return this.pow(2).div(2);
        return mul(v);
    }

    @Override
    public String toString2()
    {
        return ""+c;
    }

	@Override
    public func simplify()
	{
        return this;
	}

	@Override
	public boolean eq2(func f)
	{
		return c==((Variable)f).c;
	}

    @Override
    public func substitude0(Variable v, func p)
    {
        return this.eq2(v)?p:this;
    }


    @Override
    public func copy0() {
        return new Variable(c);
    }
}
