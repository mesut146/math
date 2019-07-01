package math.collatz;

import math.core.Constant;
import math.core.Variable;
import math.core.func;
import math.op.pow;

public class col {
    static func r1(){
        return func.parse("2*n");
    }
    static func r1(int n){
        return func.parse("2^(2*%d)",n);
    }

    static func r11(){
        //return r1().mul(new pow(new Constant(2),new Variable("a")));
        return func.parse("(%s*2^(a)-1)/3",r1().toString());
    }
}
