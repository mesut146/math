package math;

import java.util.*;
import math.core.*;
import math.funcs.*;

public class taylorsym extends taylor
{
    List<term> list=new ArrayList<>();
    func at;
    boolean sym=false;
    var v;
    
    public static taylorsym symbol(Object fo,Object vo,func at,int n){
        func f=Util.cast(fo);
        var v=Util.var(vo);
        taylorsym t=new taylorsym();
        t.v=v;
        t.sym=true;
        for(int i=0;i<=n;i++){
            func c=f.substitude(v,at).simplify().div(new fac(i).eval());
            t.put(c,i);
            f=f.derivative(v);
        }
        return t;
    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<list.size();i++){
            term t=list.get(i);
            
            if(!t.fc.is(0)){
                s.append(t.fc);
                
                if(t.n!=0){
                    s.append("*x");
                    if(t.n>1){
                        s.append("^");
                        s.append(t.n);
                    }
                }
                if(i<list.size()-1&&list.get(i+1).fc.sign==1){
                    s.append("+");
                }
            }

        }
        return s.toString();
    }

    public func conv(){
        func f=cons.ZERO;
        for(term t:list){
            f=f.add(t.fc.mul(v.pow(t.n)));
        }
        return f;
    }

    public double coeff(func f){
        return 0;
    }

    
}
