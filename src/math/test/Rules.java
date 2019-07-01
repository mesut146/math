package math.test;
import java.util.*;

public class Rules
{
    static String li="1/ln(x)=Li(x)";
    static String ei="e^x/x=Ei(x)";
    static String sin1="1/sinx(x)=-ln(csc(x)+cot(x))";
    static String cos1="1/cosx(x)=ln(sec(x)+tan(x))";
    static HashMap<String,String> h;
    static HashMap<String,String> func=new HashMap<>();


    static {
        func.put("sin","sin");
        func.put("cos","cos");
        func.put("ln","ln");
    }

    public Rules(){
        h=new HashMap<>();
    }
    
    public void add(String a,String b){
        h.put(a,b);
    }
}

