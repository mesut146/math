package math;

public class Consfunc extends Constant
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

    String str;
    public Consfunc(String s,double d){
        super(d);
        str=s;
    }

    @Override
    public String toString2() {
        return str;
    }
}
