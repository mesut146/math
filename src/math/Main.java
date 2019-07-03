package math;

import math.core.Constant;
import math.core.Variable;
import math.core.func;

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
		//func.addRule("ln(e)=1");
		//func.addRule("ln(f(fx)^g(fx))=g(fx)*ln(f(fx))");
        //func ex=func.parse("e^f(fx)");
		integral();
		//f=func.parse("e^(fx^2)");
        //System.out.println(f.derivative());
        /*ero ee=ero.init();
        ee.next(3);*/
        //System.out.println(ee);
        /*for(int i=1;i<=10;i++){
            f=f.der(1,new Variable("fx"));
            System.out.println("f"+i+"(0)="+f.get2(0));
        }*/
        
		//System.out.println(f.get(Variable.from("b"),1));
                                        
		//a();
	}
	static void integral(){
		//func f=func.parse("2*x");
		//System.out.println(f.integrate(0,1));
		//System.out.println(func.parse("0 +i*0.001").simplify());
		func fx=func.parse("0.002*n");
		System.out.println(fx.substitude(new Variable("n"),func.parse("e^x")));
		System.out.println(fx.get2(new Variable("n"),2));
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