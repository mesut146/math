package math.integral;
import math.*;
import math.core.*;
import math.funcs.*;

public class laplace extends Integral
{
    var dv;//integration variable
    var s;//output variable
    func p;//function to laplace

    /*
    laplace("sin(x)","x","s")=
     */
    
    public laplace(Object f,Object ov,Object os){
        //u(t)*e^(-t*s) dt s==1/s
        p=Util.cast(f);
        dv=Util.var(ov);
        s=Util.var(os);
        a=p.mul(new exp(dv.mul(s).negate()));
        
        a1=cons.ZERO;
        a2=cons.INF;
    }

    public laplace(func f,func ov,func os){
        //u(t)*e^(-t*s) dt s==1/s
        p=f;
        dv= (var) ov;
        s= (var) os;
        a=p.mul(new exp(dv.mul(s).negate()));

        a1=cons.ZERO;
        a2=cons.INF;
    }

    //output var will be decided out of f
    /*public laplace(func f){

    }*/

    @Override
    public String toString2()
    {
        return "laplace("+p+",d"+dv+","+s+")";
    }
    
    
}
