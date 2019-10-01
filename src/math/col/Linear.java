package math.col;
import math.operator.*;

public class Linear
{
    public int a,b;
    
     public Linear(int m,int n){
        a=m;
        b=n;
     }
	 
	 boolean odd(){
		 return a%2==0&&b%2==1;
	 }
	 
	 public String col(){
		 if(a%2==0){
			 Linear l;
			 if(b%2==0){
				 l=new Linear(a/2,b/2);
			 }else{
				 l=new Linear(3*a,3*b+1);
			 }
			 return (l.odd()?"("+l.toString()+")":l.toString())+" "+l.col();
		 }
		 return "";
	 }

     @Override
     public String toString()
     {
         return a+"*n"+(b<0?""+b:("+"+b));
     }
    
    
    
}
