package math.parser;


import math.core.func;

import java.util.*;
import math.funcs.*;

public class Parser {
    String s;
    int i,len;
	Token last=null;

    private Parser(String s){
        this.s=s;
        i=0;
        len=s.length();
    }
    public static func parse(String s){
        return new Parser(s).parse();
    }
    private func parse(){
        Token tmp;
        LinkedList<Token> l=new LinkedList<>();
        
		Token u=new Token("#",TokenType.Operator);
		//Token m=new Token("*",TokenType.Operator);
        while((tmp=next())!=null){
			if(tmp.type==TokenType.Operator&&tmp.name.equals("-")){
				if(last==null||last.type==TokenType.Operator||last.type==TokenType.Open){
					l.add(u);
				}else{
					l.add(tmp);
				}
			}else{
				l.add(tmp);
			}
			last=tmp;
        }

        List<Token> rpn=ShuntingYard.infixToRPN(l);
        //System.out.println("rpn="+rpn);

        LinkedList<Token> stack = new LinkedList<>();
		//int i=0;
        for(Token t:rpn) {
            if (t.type==TokenType.Operator) {
                if(t.name.equals("#")){
                    stack.push(new Token(stack.pop().f.negate()));
                }else if(t.name.equals("!")){
                    stack.push(new Token(new fac(stack.pop().f)));
                }
                else{
                    func v=OPS.get(t.name).eval(stack.pop().f,stack.pop().f);
                    stack.push(new Token(v));
                }
            }
            else {
                stack.push(t);
            }
			i++;
        }
        return stack.pop().f;
    }
    public interface Operation {
         func eval(func e1, func e2);
    }

    public static Map<String, Operation> OPS = new HashMap<String, Operation>();

    static {
        OPS.put("+", new Operation(){ public func eval(func e1, func e2){ return e2.add(e1); }});
        OPS.put("-", new Operation(){ public func eval(func e1, func e2){ return e2.sub(e1); }});
        OPS.put("*", new Operation(){ public func eval(func e1, func e2){ return e2.mul(e1); }});
        OPS.put("/", new Operation(){ public func eval(func e1, func e2){ return e2.div(e1); }});
        OPS.put("^", new Operation(){ public func eval(func e1, func e2){ return e2.pow(e1); }});
		//OPS.put("#", new Operation(){ public func eval(func e1){}});
    };
	
    Token next(){
        if (i < len) {
            char c=s.charAt(i++);
            //System.out.println("c="+c);
            if (c==' '||c=='\t') return next();
            if (isDigit(c)){
                int j=i-1;
                while (i<len&&(isDigit((c=s.charAt(i)))||c=='.')) i++;
                //if (i<len) i--;
                return new Token(s.substring(j,i),TokenType.Constant);
            }else if(isLetter(c)){
                int j=i-1;
                while (i<len&&isAlpha(s.charAt(i))) i++;
                String name=s.substring(j,i);
                //System.out.println("name="+name);
                if (i<len&&s.charAt(i)=='('){//funcs
                    int p=0;
                    j=++i;
                    while (i<len){
                        if ((c=s.charAt(i))=='(') p++;
                        else if(c==')'){
                            if (p==0)
                                break;
                            p--;
                        }
                        i++;
                    }
                    i++;
                    //System.out.println("s="+s.substring(j,i-1));
                    return new Token(name,TokenType.Function,s.substring(j,i-1));
                }
                return new Token(name,TokenType.Variable);
            }else if(isOperator(c)){
                return new Token(String.valueOf(c),TokenType.Operator);
            }else if(c=='('){
                return new Token("(",TokenType.Open);
            }else if(c==')'){
                //System.out.println("i="+i);
                return new Token(")",TokenType.Close);
            }else if(c=='!'){
                return new Token("!",TokenType.Operator);
            }
            System.out.println("unexpected char '"+c+"'");
        }

        return null;
    }
    boolean isDigit(char c){
        return c>='0'&&c<='9';
    }
    boolean isLetter(char c){
        return (c>='a'&&c<='z')||((c>='A'&&c<='Z'));
    }
    boolean isAlpha(char c){
        return isLetter(c)||isDigit(c)||c=='\'';
    }
    boolean isOperator(char c){
        return "+-*/^".indexOf(c)!=-1;
    }



}
