package math.prime;
import java.util.*;
import math.col.*;
import math.core.*;

public class ero
{
    List<Linear> l=new ArrayList<>();
    
    static ero init(){
        ero e=new ero();
        e.l.add(new Linear(2,-1));
        return e;
    }

    void next(int p){
        int t=l.size()*(p-1);
        
        //System.out.println(l);
        int co=l.get(0).a;
        for(int i=0;i<l.size();i++){
            int init=co*p-i*p;
            for(int j=0;j<p-1;j++){
                if((co*(p-j)-1)%p==0){
                    continue;
                }
                Linear li=new Linear(co*p,-(co*(p-j)-1));
                System.out.println(li);
            }
        }
    }
    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<l.size();i++){
            s.append(l.get(i));
            if(i<l.size()-1){
                s.append(",");
            }
        }
        return s.toString();
    }
    
    
    
}
