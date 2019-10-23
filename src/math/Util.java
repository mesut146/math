package math;

import math.core.*;

public class Util
{
    public static func cast(Object o){
        if(o instanceof var){
            return (var)o;
        }else if(o instanceof String){
            return func.parse((String)o);
        }else if(o instanceof Integer){
            return new cons((Integer)o);
        }else if(o instanceof func){
            return (func)o;
        }
        else{
            throw new RuntimeException("unexpected type: "+o.getClass()+" ,"+o);
        }
    }
    
    public static var var(Object o){
        if(o instanceof var){
            return (var)o;
        }else if(o instanceof String){
            return new var((String)o);
        }else if(o instanceof func){
            return (var)o;
        }
        else{
            throw new RuntimeException("unexpected type: "+o.getClass()+" ,"+o);
        }
    }
}
