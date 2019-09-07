package math.solver;

import math.core.*;

public class Sequence
{
    //a(n)=b(n)*a(n-1)+c(n)
    public static void solve1(func bn,func cn){
        //a(2)=b(2)*a(1)+c(2);
        func ax=cons.ONE;//a0=1
        var vn=new var("n");
        for(int n=2;n<10;n++){
            ax=bn.get(vn,n).mul(ax).add(cn.get(vn,n));
            System.out.println(ax);
        }
    }
    public static void solve(String an,String bn,String cn,int f0){
        solve(func.parse(an),func.parse(bn),func.parse(cn),f0);
    }
    //a(n)f(n)+b(n)f(n-1)=c(n)
    public static void solve(func an,func bn,func cn,int f0){
        //f(n)=[c(n)-b(n)*f(n-1)]/a(n)
        func fx=var.y;//f0=1
        var vn=new var("n");
        func f=func.parse("x-x-1");
        System.out.println(f);
        for(int n=1;n<10;n++){
            //fx=cn.get(vn,n).sub(bn.get(vn,n).mul(fx)).div(an.get(vn,n));
            //System.out.printf("f(%d)=%s%n",n,fx);
        }
    }
}
