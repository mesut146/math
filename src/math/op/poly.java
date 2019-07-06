package math.op;
import math.core.Variable;
import math.core.func;

import java.util.*;

public class poly extends add
{
    public poly(String s){
        func p=func.parse(s);
        f=p.f;
    }

    @Override
    public func add(func f)
    {
        // TODO: Implement this method
        return super.add(f);
    }

    @Override
    public func mul(func f)
    {
        //x-2
        //x-1
        //System.out.println(this);
        //System.out.println(f);
        List<func> l=getFree();
        for(func c:this.f){
            for(func d:f.f){
                l.add(c.mul(d));
            }
        }
        this.f=l;
        return this.order().simplify();
    }

    int g(func p){
        if(p.isConstant()){
            return Integer.MAX_VALUE;
        }else if(p.isMul()){
            func pw=p.f.get(1);

            if(pw.eq(Variable.x)){
                return 1;
            }
            return (int)pw.b.eval();
        }else if(p.isPow()){
            return (int)p.b.eval();
        }
        return 0;
    }
    
    poly order(){
        //System.out.println("f1="+f);
        Collections.sort(f, new Comparator<func>(){
                @Override
                public int compare(func p1,func p2)
                {
                    //c1*x^n1  c2*x^n2
                    //System.out.println("p1="+p1+",p2="+p2);
                    return Integer.compare(g(p1),g(p2));
                    
                }
            });
            //System.out.println("f2="+f);
        return this;
    }
    
    
}
