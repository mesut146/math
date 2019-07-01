package math;

public class sigma extends func
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

    int start,end;
    func x;
    Variable v;

    public sigma(func f, Variable v, int s, int e){
        this.x=f;
        this.v=v;
        this.start=s;
        this.end=e;
    }
    public sigma(func f, String v, int s, int e){
        this(f,new Variable(v),s,e);
    }

    public func sum(){
        func d=Constant.ZERO;
		System.out.println("f="+x);
		//System.out.println(x.get(v,1));
        for (int i=start;i<=end;i++){
            //System.out.println(x.get(i));
            d=d.add(x.get(v,i));
			//System.out.println(d);
        }
        return d;
    }

    public double get(){
        double d=0;
        for (int i=start;i<=end;i++){
            d+=x.get2(v,i);
			System.out.println(d);
        }
        return d;
    }
    @Override
    public func get(Variable v, Constant c) {
        if(this.v.eq2(v)){

        }
        return this;
    }

	@Override
	public double get2(Variable v, double d)
	{
		// TODO: Implement this method
		return 0;
	}

	
	

    @Override
    public func derivative(Variable v) {
        return new sigma(x.derivative(),this.v,start,end);
    }

    @Override
    public func integrate(Variable v) {
        return new sigma(x.integrate(v),v,start,end);
    }

    @Override
    public String toString2() {
        return "sum("+v+"="+start+" to "+end+","+x+")";
    }

    @Override
    public boolean eq2(func f) {
        return false;
    }

    @Override
    public func substitude0(Variable v, func p) {
        return null;
    }

    @Override
    public func copy0() {
        return new sigma(x,v,start,end);
    }
}
