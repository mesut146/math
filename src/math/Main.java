package math;

import math.core.Constant;
import math.core.Variable;
import math.core.func;
import math.funcs.gamma;

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

		integral();
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
	static void integral(){
		gamma gamma=new gamma(Variable.x);
		func f=func.parse("e^-t*t^5");
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
