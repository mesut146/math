package math;

import java.util.*;
import math.core.*;
import math.funcs.*;
import math.op.*;
import math.integral.*;
import math.solver.*;

public class Main
{

	public static void main(String[] args)
	{
		Constant c1=new Constant(1);
		Constant c2=new Constant(2);
		Constant c3=new Constant(3);
        Variable x=Variable.x;
		Variable y=Variable.y;
		Variable z=Variable.z;
        Constant e=Constant.E;
        Constant pi=Constant.PI;

		func f=null,g=null;

		//integral();
        //col();
        //der();
        //f=func.parse("x^n*e^x");
        //new Iexp().integ(f);
        Sequence.solve("1","1","x",1);
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
    
    static void col(){
        col.p1();
    }
    static void der(){
        func f=func.parse("sin(x)^n");
        System.out.println(f);
        for(int i=1;i<10;i++){
            System.out.println(f.der(i,Variable.x));
        }
    }
    
    static void taylor(){
        func f=func.parse("cos(x)");
        System.out.println(f.taylor());
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
            f=Constant.ZERO;
            int m=r.nextInt(max-2)+2;
            for(int i=0;i<m;i++){
                f=f.add(random(max/2));
            }
        }else if(n==1){//mul
            f=Constant.ONE;
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
        return new Variable((char)('a'+c));
    }
    static func getCons(){
        int len=new Random().nextInt(4)+1;
        return new Constant(new Random().nextInt((int)Math.pow(10,len)));
    }
    
	static void integral(){
		gamma gamma=new gamma(Variable.x);
		func f=func.parse("e^-t*t^0.5");
		System.out.println(f.integrate(0,1000,Variable.t));
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
