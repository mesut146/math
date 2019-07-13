package math;
import math.core.*;
import java.util.*;

public class col
{
    
    public static void p1(){
        func f=func.parse("4^(3*n-2)-1");
        func f2=func.parse("2^(2*a)");
        func f3=f.mul(f2).sub(3);
        //mod(f,"n",27);
        //mod(f2,"a",27);
        func ff1=func.parse("(((4^(9*n-5)-1)/3*2^(6*a)-1)/3*2^(2*b)-1)/3");
        //mod2(f3,27);
        //mod3(ff1,3);
        modn(ff1,"n,a,b",3);
        //System.out.println((long)ff1.eval("n,a,b",2,2,1));
        //n=1 3a-2
        //n=2 3a
        //n=3 3a-1
        //n=4 3a-2
        //n=5 3a
        //3n-2 3a-2
        //3n-1 3a
        //3n 3a-1
        /*
        
        */
    }
    static void modn(func f,String vars,int mod){
        String[] v=vars.split(",");
        int n=v.length;
        double[] vi=new double[n];
        Object[] o=new Object[n];
        StringBuilder s=new StringBuilder();
        for(int i=0;i<n;i++){
            s.append(v[i]).append("=%.0f ");
            vi[i]=1;
            o[i]=1.0;
        }
        //s.append("x=%d");
        //s.append("%n");
        int max=5;
        while(true){
            for(int i=n-1;i>0;i--){
                if(vi[i]>max){
                    vi[i]=1;
                    vi[i-1]++;
                    o[i]=1.0;
                    o[i-1]=(double)o[i-1]+1.0;
                }
            }
            if(vi[0]>max){
                break;
            }
            
            long x=(long)f.eval(vars,vi);
            System.out.printf(s.toString(),o);
            System.out.printf("x=%d\n",x);
            //System.out.println(Arrays.toString(o));
            vi[n-1]++;
            o[n-1]=(double)o[n-1]+1.0;
        }
        
    }
    
    static void mod3(func f,int mod){
        for(int n=1;n<7;n++){
            for(int a=1;a<13;a++){
                for(int b=1;b<5;b++){
                    long x=(long)f.eval("n,a,b",n,a,b);
                    System.out.printf("n=%d a=%d b=%d x=%s mod=%s %s\n",n,a,b,x,x%mod,(x%mod)/9);
                }
                
            }
        }
    }
    static void mod2(func f,int mod){
        for(int n=1;n<7;n++){
            for(int a=1;a<13;a++){
                long x=(long)f.eval("n,a",n,a);
                System.out.printf("n=%d a=%d x=%s mod=%s %s\n",n,a,x,x%mod,(x%mod)/9);
            }
        }
    }
    
    static void mod(func f,String var,int mod){
        for(int i=1;i<12;i++){
            int x=(int)f.eval(var,i);
            System.out.printf("%s=%d %d mod=%d %d\n",var,i,x,x%mod,mod);
        }
        
    }
    
    public static void pp(){
        int a1,a2,a3,a4;
        
    }
    
    
}
