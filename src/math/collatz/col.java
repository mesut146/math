package math.collatz;


import math.func;

public class col {
    static func r1(){
        return func.parse("2*n");
    }
    static func r1(int n){
        return func.parse(String.format("2^(2*%d)",n));
    }

    static func r11(){
        //return r1().mul(new pow(new Constant(2),new Variable("a")));
        return func.parse(String.format("(%s*2^(a)-1)/3",r1().toString()));
    }
}
