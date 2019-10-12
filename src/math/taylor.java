package math;

import java.util.*;
import math.core.*;
import math.funcs.*;

public class taylor
{
    List<term> list=new ArrayList<>();
    
    //maclerien x=0
    public static taylor numeric(func f,int n){
        return numeric(f,0,n);
    }
    //n terms at x=x
    public static taylor numeric(func f,int x,int n){
        taylor t=new taylor();
        double c;
        c=f.eval(x);
        
        t.list.add(new term(c,0));
        
        for(int i=1;i<=n;i++){
            f=f.derivative();
            t.list.add(new term(f.eval(x)/new fac(i).eval(),i));
        }
        return t;
    }
    
    public void put(double coeff,int po){
        list.add(new term(coeff,po));
    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<list.size();i++){
            term t=list.get(i);
            if(t.c!=0){
                s.append(t.c);
                if(t.n!=0){
                    s.append("*x");
                    if(t.n>1){
                        s.append("^");
                        s.append(t.n);
                    }
                }
            }
            if(i<list.size()-1&&list.get(i+1).c>0){
                s.append("+");
            }
        }
        return s.toString();
    }
    
    
    
    public double coeff(func f){
        return 0;
    }
    
    static class term{
        double c;
        int n;
        public term(double c0,int n0){
            c=c0;n=n0;
        }
    }
}
