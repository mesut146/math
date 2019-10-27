package math;

import java.util.*;
import math.col.*;
import math.core.*;
import math.diff.*;
import math.funcs.*;
import math.prime.*;
import java.io.*;
import math.parser.*;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		cons c1=new cons(1);
		cons c2=new cons(2);
		cons c3=new cons(3);
        var x=var.x;
		var y=var.y;
		var z=var.z;
        cons e=cons.E;
        cons pi=cons.PI;

		func f=null,g=null;
        
        
        f=func.parse("e^x");
        System.out.println(f);
        new graph(f,-5,5).calc();
        
        //matrix();

		//integral();
        /*lineq l=new lineq(10,3,"c");
        System.out.println(l);
        System.out.println(l.solve());*/
        
        //System.out.println(new zeta("2").eval());
        
        //taylor();
        //prime();
        
        //System.out.println(func.parse("x^i").getComplex());
        //zeta();
        //pset.init();
       
        //System.out.println(func.parse("sin(x)").getClass());
        
        //System.out.println(func.parse("inv(x+e^x)").taylor());
        //col();
        //simp();
        //set();
        //der();
        //f=func.parse("x^n*e^x");
        //new Iexp().integ(f);
        //diff();
        //floor();
        //dif1();
        
        /*Config.useBigDecimal=true;
        f=func.parse("x^5-1");
        System.out.println(f);*/
        //Sequence.solve("1","1","x",1);
        //System.out.println(f.derivative());
        //taylor();
        /*for(int i=0;i<1000;i++)
            System.out.println(random(100));*/
		/*f=func.parse("e^(x^2*y)");
        System.out.println(f.derivative());*/
        /*ero ee=ero.init();
        ee.next(3);*/
        //System.out.println(ee);
        /*for(int i=1;i<=10;i++){
            f=f.der(1,new Variable("fx"));
            System.out.println("f"+i+"(0)="+f.eval(0));
        }*/

		//System.out.println(f.eval(Variable.from("b"),1));

		//a();
	}
    static void matrix(){
        matrix m1=new matrix("(1,2,3),(4,9,7)");
        matrix m2=new matrix("(4,7,2),(9,8,0)");
        
        System.out.println(m1.add(m2));
    }
    
    static void set(){
        set s=new set(1,2,3);
        set s2=new set(5,8);
        set s3=new set(func.parse("2*n+1"));
        System.out.println(s3.print(0,22));
    }
    
    static void simp(){
        func f=func.parse("cos(x)");
        
        System.out.println(f.derivative(21));
    }
    
    static void prime(){
        func f;
        /*f=func.parse("1/(x-2)+1/(x-3)+1/(x-5)+1/(x-7)");
        System.out.println(f);
        System.out.println(f.eval("x=53"));*/
        set s=new pset(100);
        System.out.println(s);
        s=(set)s.pow(3).mul(2).add(1);
        System.out.println(s);
        System.out.println(factor.factorize(s));
        System.out.println(new prime(2).eval());
    }
    
    static void zeta(){
        /*prime pn=new prime("n");
        
        sigma s=new sigma("ln(p)/(p^2-1)","n",1,100);
        System.out.println(s);
        s=(sigma)s.substitude(new var("p"),pn);
        System.out.println(s);
        System.out.println(s.eval());*/
        //System.out.println(func.parse("2*0.5"));
        zeta z=new zeta(func.parse("0.5+i*14.13472514173"));
        System.out.println(z.sum.getImaginary().eval());
        
        //System.out.println(func.parse("atan(1+i)").getComplex());
    }
    
    static void dif1(){
        //Config.mul.simplify=false;
        func f;
        //f=func.parse("f'(x)+f(x)*p(x)-q(x)");
        f=func.parse("1/(3*x^2-3)");
        System.out.println(f);
        System.out.println(f.derivative(2));
    }
    
    static void floor(){
        func f=func.parse("floor((n+3)/3)-floor((n+2)/3)");
        System.out.println(f);
        for(int n=0;n<20;n++){
            System.out.printf("n=%d %s%n",n,f.eval("n",n));
        }
    }
    
    static void diff(){
        FirstOrder fo=new FirstOrder();
        fo.p=func.parse("1");
        fo.q=func.parse("2*e^x+e^(x^2)*(2*x+1)");
        fo.solve();
    }
    
    static void col(){
        col.p1();
    }
    static void der(){
        func f=func.parse("e^x*y(x)");
        fx.table.put(func.parse("y''(x)"),func.parse("e^x*y(x)"));
        //f.substitude();
        System.out.println(f);
        for(int i=1;i<10;i++){
            System.out.println("y#"+(i+2)+"="+f.derivative(i,var.x));
        }
    }
    
    static void taylor(){
        func f=func.parse("cos(x)");
        System.out.println(taylor.numeric(f,0,10));
    }
    
    static func random(int max){
        Random r=new Random();
        int n=r.nextInt(5);
        if(max<=1){
            n=4;
        }
        //max--;
        func f=null;
        //add,mul,pow,var,cons
        if(n==0){//add
            //need m random funcs
            f=cons.ZERO;
            int m=r.nextInt(max-2)+2;
            for(int i=0;i<m;i++){
                f=f.add(random(max/2));
            }
        }else if(n==1){//mul
            f=cons.ONE;
            int m=r.nextInt(max-1);
            for(int i=0;i<m;i++){
                f=f.mul(random(max/2));
            }
        }else if(n==2){//pow
            func a=random(max/2);
            func b=random(max/2);
            f=a.pow(b);
        }else if(n==3){//var
            f=getVar();
        }else if(n==4){
            f=getCons();
        }
        return f;
    }
    
    static func getVar(){
        int c=new Random().nextInt('z'-'a');
        return new var((char)('a'+c));
    }
    static func getCons(){
        int len=new Random().nextInt(4)+1;
        return new cons(new Random().nextInt((int)Math.pow(10,len)));
    }
    
	static void integral(){
        func f;
		//gamma gamma=new gamma(var.x);
        //System.out.println(gamma.eval(7));
        
        //li li=new li(func.parse("x"));
        //System.out.println(li.eval("x=5"));
		
        f=func.parse("ln(1-x)/x");
        System.out.println(f.integrate(0,1,var.x));
        
        //f=func.parse("ln(sin(x))");
        //System.out.println(f.integrate(0,Math.PI,var.x));
	}
	static void rule(){
		//func.addRule("ln(e)=1");
		//func.addRule("ln(f(fx)^g(fx))=g(fx)*ln(f(fx))");
		//func ex=func.parse("e^f(fx)");
	}

	static void b(){


		for(int k=4;k<50;k+=4){
			for(int i=1;i<k;i+=2){
				Linear l=new Linear(k,i);
				System.out.println(l+" "+l.col());
			}
			System.out.println("------------");
		}


	}


	static void a(){
		int o=11;
		int p=o-1;
		for(int m=1;m<100;m++){
			int x=(o+1)*m-o-p*(int)Math.floor((m-1)/p);
			System.out.println(x);
		}
	}


}
