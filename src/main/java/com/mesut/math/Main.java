package com.mesut.math;

import com.mesut.math.core.*;
import com.mesut.math.funcs.zeta;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(random(20));
        }
    }


    static void matrix() {
        matrix m1 = new matrix("(1,2,3),(4,9,7)");
        matrix m2 = new matrix("(4,7,2),(9,8,0)");

        System.out.println(m1.add(m2));
    }

    static void set() {
        set s = new set(1, 2, 3);
        set s2 = new set(5, 8);
        set s3 = new set(func.parse("2*n+1"));
        System.out.println(s3.print(0, 22));
    }

    static void simp() {
        func f = func.parse("cos(x)");

        System.out.println(f.derivative(21));
    }

    static void zeta() {
        /*prime pn=new prime("n");
        
        sigma s=new sigma("ln(p)/(p^2-1)","n",1,100);
        System.out.println(s);
        s=(sigma)s.substitude(new var("p"),pn);
        System.out.println(s);
        System.out.println(s.eval());*/
        //System.out.println(func.parse("2*0.5"));
        zeta z = new zeta(func.parse("0.5+i*14.13472514173"));
        System.out.println(z.sum.getImaginary().eval());

        //System.out.println(func.parse("atan(1+i)").getComplex());
    }


    static void floor() {
        func f = func.parse("floor((n+3)/3)-floor((n+2)/3)");
        System.out.println(f);
        for (int n = 0; n < 20; n++) {
            System.out.printf("n=%d %s%n", n, f.eval("n", n));
        }
    }

    static func random(int max) {
        Random random = new Random();
        int type = random.nextInt(5);
        if (max <= 1) {
            type = 1;
        }
        //max--;
        func func = null;
        if (type == 0) {//var
            func = getVar();
        }
        else if (type == 1) {//cons
            func = getCons();
        }
        else if (type == 2) {//add
            func = random(max / 2).add(random(max / 2));
        }
        else if (type == 3) {//mul
            func = random(max / 2).mul(random(max / 2));
        }
        else if (type == 4) {//pow
            func = random(max / 2).pow(random(max / 2));
        }
        return func;
    }

    static func getVar() {
        int c = new Random().nextInt('z' - 'a');
        return new variable(String.valueOf((char) ('a' + c)));
    }

    static func getCons() {
        int len = new Random().nextInt(4) + 1;
        return new cons(new Random().nextInt((int) Math.pow(10, len)));
    }

    static void integral() {
        func f;
        //gamma gamma=new gamma(var.x);
        //System.out.println(gamma.eval(7));

        //li li=new li(func.parse("x"));
        //System.out.println(li.eval("x=5"));

        f = func.parse("ln(1-x)/x");
        System.out.println(f.integrate(0, 1, variable.x));

        //f=func.parse("ln(sin(x))");
        //System.out.println(f.integrate(0,Math.PI,var.x));
    }


}
