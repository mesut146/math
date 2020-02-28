package com.mesut.math.prime;
import java.util.*;
import com.mesut.math.core.*;

public class factor
{
    List<Integer> base=new ArrayList<>(),
                  pow=new ArrayList<>();
                  int x;
    
                  
                  
    public static List<factor> factorize(set s){
        List<factor> l=new ArrayList<>();
        for(cons c:s.list){
            l.add(factorize((int)c.eval()));
        }
        return l;
    }
    public static factor factorize(int x){
        factor f=new factor();
        f.x=x;
        int p=0;
        while(x%2==0){
            p++;
            x=x/2;
        }
        if(p>0){
            f.base.add(2);
            f.pow.add(p);
        }
        for(int i=3;i<=x;i+=2){
            p=0;
            while(x%i==0){
                p++;
                x=x/i;
            }
            if(p>0){
                f.base.add(i);
                f.pow.add(p);
            }
        }
        return f;
    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<base.size();i++){
            s.append(base.get(i));
            if(pow.get(i)>1){
                s.append("^");
                s.append(pow.get(i));
            }
            if(i<base.size()-1){
                s.append("*");
            }
        }
        return s.toString();
    }
    
    
}
