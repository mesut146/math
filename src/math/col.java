package math;
import math.core.*;

public class col
{
    
    public static void p1(){
        func f=func.parse("(2^(2*n)-1)/3");
        func f2=f.mul(func.parse("2^m")).sub(1).div(3);
        System.out.println(f2.eval("n,m",2,3));
    }
    
    public static void pp(){
        int a1,a2,a3,a4;
        
    }
}
