package math.parser;
import java.lang.reflect.*;
import math.cons.*;
import math.core.*;
import java.util.*;

public class Token{
    String name;
    TokenType type;
    public func f;
    func param;
    public static Map<String,Class<?>> map=new HashMap<>();
	
    public Token(String s,TokenType t){
        name=s;
        type=t;
        switch (type){
            case Constant:{
                f=new cons(Double.parseDouble(name));
                break;
            }case Variable:{
                if(name.equals("e")){
                    f=cons.E;
                    type=TokenType.Constant;
                }else if(name.equals("pi")){
                    f=cons.PI;
                    type=TokenType.Constant;
                }else if(name.equals("i")){
                    f=i.i;
                    type=TokenType.Constant;
                }else if(name.equals("inf")){
                    f=cons.INF;
                    type=TokenType.Constant;
                }
				else {
                    f=new var(name);
                }
            }
        }
    }
    public Token(String s,TokenType t,String param){
        this(s,t);
        this.param=func.parse(param);
        //this.param=param;
        Class<func> c;
        Constructor<func> co;
        if(map.containsKey(name)){
            try
            {
                c=(Class<func>)map.get(name);
                co=c.getDeclaredConstructor(func.class);
                f=co.newInstance(this.param);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //System.out.println(this);
            }    
        }else{
            if(fx.has(s)){
                f=fx.get(s);
            }else{
                f=new fx(s,this.param);

            }
        }
        
    }

    public Token(func f){
        this.f=f;
        type=TokenType.Function;
    }


    @Override
    public String toString() {
        return "("+name+":"+type+" f="+f+" param="+param+")";
    }
}
