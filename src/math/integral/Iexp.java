package math.integral;
import math.core.*;
import java.util.*;

public class Iexp
{
    //x^n*e^x
    public void integ(func f){
        if(f.isMul()){
            sort(f);
            func a=f.a;
            func b=f.b;
            
        }
    }
    
    public  void solve(){
        //a(n)=b(n)*a(n-1)+c(n)
        func bn=func.parse("-n");
        func cn=func.parse("x^n*e^x");
       
        //a(2)=b(2)*a(1)+c(2);
        func ax=Constant.ONE;//a0=1
        Variable vn=new Variable("n");
        for(int n=2;n<10;n++){
            ax=bn.get(vn,n).mul(ax).add(cn.get(vn,n));
            System.out.println(ax);
        }
    }
    
    void sort(func f){
        Collections.sort(f.f, new Comparator<func>(){

                @Override
                public int compare(func p1, func p2)
                {
                    if(p1.isPolinom()){
                        return -1;
                    }
                    if(p1.isPower()){
                        return 1;
                    }
                    return 0;
                }
                
            
        });
    }
    boolean isExp(func f){
        return f.isPow()&&f.eq(func.parse("e^x"));
    }
    boolean isPoly(func f){
        return f.isPolinom();
    }
}
