package math;

public abstract class Config {
    public static boolean useBigDecimal=false;
    public static int precision=25;
    public static int maxIteration=100000;//sigma
    public static int digits=10;//sigma
    
    public abstract static class add{
        public static boolean simplify=true;
    }
    public abstract static class mul{
        public static boolean simplify=true;
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
        public static int interval=100000;
        public static boolean converge=false;
    }
    public abstract static class trigonomety{
        public static boolean stay=true;
    }
}
