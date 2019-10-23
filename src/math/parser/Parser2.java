package math.parser;
import math.core.*;
import java.util.*;
import java.nio.*;

public class Parser2
{
    String s;
    int i,len;
    char c;
    public Parser2(String f)
    {
        s = f;
        i = 0;
        len = f.length();
    }


    public static func parse(String str)
    {
        Parser2 p=new Parser2(str);
        Token t;
        Stack<Token> s=new Stack<>();
        List<Token> list=new ArrayList<>();
        List<Token> ops=new ArrayList<>();
        int c=0;
        while ((t = p.next()) != null)
        {
            list.add(t);
            if(t.type==TokenType.Operator){
                ops.add(t);
            }
            System.out.println("token="+t);
            
        }
        
        if (list.size() == 1)
        {
            return list.get(0).f;
        }
        return null;
    }
    

    Token next()
    {
        
        if (i < len)
        {
            chr();
            
            i++;
            //System.out.println("c="+c);
            if (c == ' ' || c == '\t')
            {
                return next();
            }
            else if (isDigit(c))
            {
                StringBuilder sb=new StringBuilder();
                sb.append(c);                
                while (i < len && (isDigit(c = chr()) || c == '.'))
                {
                    sb.append(c);
                    i++;
                }               
                return new Token(sb.toString(), TokenType.Constant);
            }
            else if (isLetter(c))
            {
                StringBuilder sb=new StringBuilder();
                sb.append(c);

                while (i < len && isAlpha(chr()))
                {
                    sb.append(c);
                    i++;
                }
                String name=sb.toString();

                //System.out.println("name="+name);
                if (i < len && chr() == '(')
                {//funcs

                    String param=readPar();
                    //System.out.println("n"+name);
                    return new Token(name, TokenType.Function, param);
                }
                return new Token(name, TokenType.Variable);
            }
            else if (isOperator(c))
            {
                return new Token(String.valueOf(c), TokenType.Operator);
            }
            else if (c == '(')
            {
                i--;
                String p=readPar();
                return new Token(Parser2.parse(p));

            }
            else if (c == '!')
            {
                return new Token("!", TokenType.Operator);
            }
            else if (c == ',')
            {
                return Token.COMMA;
            }
            System.out.println("unexpected char '" + c + "'");
        }//outer
        //System.out.println("err");
        return null;
    }
    
    char chr()
    {
        return c = s.charAt(i);
    }

    String readPar()
    {
        StringBuilder sb=new StringBuilder();
        if (i < len && chr() == '(')
        {
            i++;
            int p=0;

            while (i < len)
            {

                chr();
                if (c == '(')
                {
                    p++;

                }
                else if (c == ')')
                {
                    if (p == 0)
                        break;
                    p--;
                }
                sb.append(c);
                i++;
            }
            i++;

        }
        return sb.toString();
    }
    
    boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }
    boolean isLetter(char c)
    {
        return (c >= 'a' && c <= 'z') || ((c >= 'A' && c <= 'Z'));
    }
    boolean isAlpha(char c)
    {
        return isLetter(c) || isDigit(c) || c == '\'';
    }
    boolean isOperator(char c)
    {
        return "+-*/^".indexOf(c) != -1;
    }
}
