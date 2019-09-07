package math.core;

import math.core.cons;

public class Consfunc extends cons
{

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return toString();
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
