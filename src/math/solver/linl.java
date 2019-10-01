package math.solver;

import math.core.*;
import math.*;

public class linl
{
    //v=a*n+b
    var v;
    lin l;
    
    public linl(){
        
    }
    
    public linl(Object vo,lin lo){
        v=(var) Util.cast(vo);
        l=lo;
    }

    @Override
    public String toString()
    {
        return v+"="+l;
    }
    
    
    
}
