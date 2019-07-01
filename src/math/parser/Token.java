package math.parser;
import java.lang.reflect.*;
import math.*;
import math.cons.*;
import math.core.Constant;
import math.core.Variable;
import math.core.func;

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
                f=new Constant(Double.parseDouble(name));
                break;
            }case Variable:{
                if(name.equals("e")){
                    f=Constant.E;
                    type=TokenType.Constant;
                }else if(name.equals("pi")){
                    f=Constant.PI;
                    type=TokenType.Constant;
                }else if(name.equals("i")){
                    f=i.i;
                    type=TokenType.Constant;
                }
				else {
                    f=new Variable(name);
                }
            }
        }
    }
    public Token(String s,TokenType t,String param){
        this(s,t);
        this.param=func.parse(param);
        //this.param=param;
        try
        {
            //System.out.println("this="+this+" param="+param+" val="+val);
			Class<func> c;
			Constructor<func> co;
			if(s.length()==1){
                if(fx.has(s)){
                    f=fx.get(s);
                }else{
                    c=(Class<func>)Class.forName("math.fx");
                    co=c.getDeclaredConstructor(String.class,func.class);
                    f=co.newInstance(s,this.param);
                }
		
			}
            else{
				c=(Class<func>)Class.forName("math.funcs."+name);
				co=c.getDeclaredConstructor(func.class);
				f=co.newInstance(this.param);
                //f=f.simplify();
			}
			
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println(this);
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
