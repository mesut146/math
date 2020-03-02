package com.mesut.math;

import com.mesut.math.core.*;

public class Util
{
    public static func cast(Object obj){
        //start from subtype
        if(obj instanceof var){
            return (var)obj;
        }else if(obj instanceof String){
            return func.parse((String)obj);
        }else if(obj instanceof Integer){
            return new cons((Integer)obj);
        }else if(obj instanceof func){
            return (func)obj;
        }
        else{
            throw new RuntimeException("unexpected type: "+obj.getClass()+" , "+obj);
        }
    }
    
    public static var var(Object obj){
        if(obj instanceof var){
            return (var)obj;
        }else if(obj instanceof String){
            return new var((String)obj);
        }else if(obj instanceof func){
            return (var)obj;
        }
        else{
            throw new RuntimeException("unexpected type: "+obj.getClass()+" , "+obj);
        }
    }
}
