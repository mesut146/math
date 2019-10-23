package math;
import math.funcs.*;
import math.integral.*;
import math.prime.*;
import math.trigonometry.*;

import static math.core.func.*;

public abstract class Config {
    public static boolean useBigDecimal=false;
    public static int precision=25;
    public static int maxIteration=100000;//sigma
    public static int digits=5;//sigma
    
    public abstract static class add{
        public static boolean simplify=true;
    }
    public abstract static class mul{
        public static boolean simplify=false;
        public static boolean distributeCons=true;
    }
    public abstract static class div{
        public static boolean simplify=true;
        public static boolean distributeCons=true;
    }
    public abstract static class pow{
        public static boolean 
        simplify=true,
        simpCons=true;//2^10 becomes 1024
        
    }
    public abstract static class integral{
        public static int interval=10000;
        public static boolean converge=false;
    }
    public abstract static class trigonomety{
        public static boolean stay=true;
    }
    
    public static void init(){
        register("exp",exp.class);
        register("ln",ln.class);
        register("log10",log10.class);
        register("log",loga_b.class);//
        register("fac",fac.class);
        register("floor",floor.class);
        register("sqrt",sqrt.class);
        register("inv",inv.class);
        register("step",step.class);
        
        register("gamma",gamma.class);
        register("zeta",zeta.class);
        register("li",li.class);
        register("Ei",Ei.class);
        register("ei",Ei.class);
        register("erf",erf.class);
        
        register("sin",sin.class);
        register("cos",cos.class);
        register("tan",tan.class);
        register("cot",cot.class);
        register("sec",sec.class);
        register("csc",csc.class);
        register("asin",asin.class);
        register("acos",acos.class);
        register("atan",atan.class);
        register("acot",acot.class);
        
        register("sinh",sinh.class);
        register("cosh",cosh.class);
        
        register("pi",pi.class);
        register("primePi",pi.class);
        register("PrimePi",pi.class);
        register("prime",prime.class);
        register("p",prime.class);
    }
    
}
