package com.mesut.math.solver;

import com.mesut.math.core.*;
import com.mesut.math.*;

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
