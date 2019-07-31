package math;

public abstract class Config {
    public static boolean useBigDecimal=true;
    public abstract static class add{
        public static boolean simplify=true;
    }
    public abstract static class mul{
        public static boolean simplify=true;
        public static boolean distributeCons=true;
    }
    public abstract static class pow{
        public static boolean simplify=true;
    }
    public abstract static class integral{
        public static int interval=1000000;
        public static boolean converge=false;
    }
    public abstract static class trigonomety{
        public static boolean stay=true;
    }
}
