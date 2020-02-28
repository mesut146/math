package com.mesut.math;

import java.util.*;
import com.mesut.math.core.*;
import com.mesut.math.funcs.*;

public class taylor
{
    List<term> list=new ArrayList<>();
    public double at;
    String base;
    //boolean sym=false;
    
    
    //macleurien x=0
    public static taylor numeric(func f,int n){
        return numeric(f,0,n);
    }
    //n terms at x=x
    public static taylor numeric(func f,double at,int n){
        taylor t=new taylor();
        double c;
        c=f.eval(at);
        
        t.list.add(new term(c,0));
        
        for(int i=1;i<=n;i++){
            f=f.derivative();
            t.list.add(new term(f.eval(at)/new fac(i).eval(),i));
        }
        return t;
    }
    
    public void put(double coeff,int po){
        list.add(new term(coeff,po));
    }
    public void put(func c,int p){
        list.add(new term(c,p));
    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder();
        base=var.x.sub(at).toString();
        for(int i=0;i<list.size();i++){
            term t=list.get(i);
            if(t.c!=0){
                s.append(t.c);

                if(t.n!=0){
                    if(at==0){
                        s.append("*x");
                    }else{
                        s.append("*("+base+")");
                    }
                    if(t.n>1){
                        s.append("^");
                        s.append(t.n);
                    }
                }
                if(i<list.size()-1&&list.get(i+1).c>0){
                    s.append("+");
                }
            }
            
        }
        return s.toString();
    }
    
    static class term{
        double c;
        func fc;
        int n;
        public term(double c0,int n0){
            c=c0;n=n0;
        }
        public term(func f0,int n0){
            fc=f0;n=n0;
        }
        
    }
}
