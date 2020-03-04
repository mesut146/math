package com.mesut.math.integral;
import com.mesut.math.core.*;
import java.util.*;

public class Iexp
{
    //x^n*e^x
    public void integ(func f){
        if(f.isMul()){
            sort(f);
            func a=f.a;
            func b=f.b;
            
        }
    }
    
    void sort(func f){
        Collections.sort(f.f, new Comparator<func>(){

                @Override
                public int compare(func p1, func p2)
                {
                    if(p1.isPolynom()){
                        return -1;
                    }
                    if(p1.isPower()){
                        return 1;
                    }
                    return 0;
                }
                
            
        });
    }
    boolean isExp(func f){
        return f.isPow()&&f.eq(func.parse("e^x"));
    }
    boolean isPoly(func f){
        return f.isPolynom();
    }
}
