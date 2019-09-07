package math.diff;

import math.core.*;

public class FirstOrder
{
    public func p;
    public func q;
    
    public void solve(){
        //y'+y*p=q
        double h=0.00001;
        func ah=p.mul(h);
        func ah2=p.substitude(var.x,var.x.add(h)).mul(h);
        func bh=q.substitude(var.x,var.x.add(h));
        System.out.println(bh);
        func rhs=bh.div(ah2).sub(q.div(ah));
        func lhs=cons.ONE.div(ah2).sub(cons.ONE.div(ah)).add(1);
        System.out.println(rhs);
        System.out.println(lhs);
        func yp=rhs.div(lhs);
        System.out.println(yp);
        System.out.println(yp.eval("x",1));
    }
}
