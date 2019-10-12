package math;
import java.util.*;

public class Rules
{
    static String li="1/ln(fx)=Li(fx)";
    static String ei="e^f(x)/f(x)=Ei(fx)";
    static String sin1="1/sinx(fx)=-ln(csc(fx)+cot(fx))";
    static String cos1="1/cosx(fx)=ln(sec(fx)+tan(fx))";
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

