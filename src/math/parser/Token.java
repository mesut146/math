package math.parser;
import java.lang.reflect.*;
import math.cons.*;
import math.core.*;
import java.util.*;
import math.*;
import math.funcs.*;

public class Token{
    String name;
    TokenType type;
    public func f;
    func param;
    List<func> params=new ArrayList<>();

    public static Token
        COMMA=new Token(TokenType.Comma),
        OPEN=new Token(TokenType.Open),
        CLOSE=new Token(TokenType.Close);
        
	
    public Token(TokenType t){
        type=t;
        
    }
    
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
                    f=cons.i;
                    type=TokenType.Constant;
                }else if(name.equals("phi")){
                    f=cons.PHI;
                    type=TokenType.Constant;
                }
                else if(name.equals("inf")){
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
        String[] sp=param.split(",");
        for(String p:sp){
            params.add(func.parse(p));
        }
        f=func.makeFunc(s,params);
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
