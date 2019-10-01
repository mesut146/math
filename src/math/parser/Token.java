package math.parser;
import java.lang.reflect.*;
import math.cons.*;
import math.core.*;

public class Token{
    String name;
    TokenType type;
    public func f;
    func param;
	
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
        try
        {
            //System.out.println("this="+this+" param="+param+" val="+val);
            c=(Class<func>)Class.forName("math.funcs."+name);
            co=c.getDeclaredConstructor(func.class);
            f=co.newInstance(this.param);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            //System.out.println(this);
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
