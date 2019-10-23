package math.funcs;

import math.core.*;
import java.util.*;

public class log10 extends loga_b
{
    
    public log10(func p){
        super(new cons(10),p);
    }

    @Override
    public String toString2()
    {
        return "log10("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        return a.eq(f.a);
    }
    
}
