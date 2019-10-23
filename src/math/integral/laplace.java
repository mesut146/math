package math.integral;
import math.*;
import math.core.*;
import math.funcs.*;

public class laplace extends Integral
{
    var dv;
    var s;
    func p;
    
    public laplace(Object f,Object ov,Object os){
        //u(t)*e^(-t*s) dt s==1/s
        p=Util.cast(f);
        dv=Util.var(ov);
        s=Util.var(os);
        a=p.mul(new exp(dv.mul(s).negate()));
        
        lim=true;
        a1=cons.ZERO;
        a2=cons.INF;
    }

    public laplace(Object f,Object os){
        
        
    }
    
    @Override
    public String toString2()
    {
        return "laplace("+p+",d"+dv+","+s+")";
    }
    
    
}
