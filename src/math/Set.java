package math;
import math.core.*;
import java.util.*;

public class Set extends func
{

    String name="c";
    int start,end;
    var v=var.n;
    List<cons> list=new ArrayList<>();
    //List<func> list=new ArrayList<>();
    public static boolean print=true;
    
    public Set(func cn){
        a=cn;
        name="c";
    }
    public Set(int...l){
        name="c";
        start=1;
        end=l.length;
        for(int i:l){
            list.add(new cons(i));
        }
    }
    
    //is the Set has general term cn
    public boolean isGeneral(){
        return a!=null;
    }
    
    public Set sort(){
        Collections.sort(list, new Comparator<cons>(){

                @Override
                public int compare(cons p1, cons p2)
                {
                    
                    return Double.compare(p1.val,p2.val);
                }

            
        });
        return this;
    }

    @Override
    public func add(double d)
    {
        Set s=new Set();
        
        for(cons c:list){
            s.list.add((cons)c.add(d));
        }
        return s;
    }

    @Override
    public func add(func f)
    {
        if(f instanceof Set){
            Set sf=(Set)f;
            Set ns=new Set();
            for(cons c1:list){
                for(cons c2:sf.list){
                    ns.list.add((cons)c1.add(c2));
                }
            }
            return ns;
        }
        throw new RuntimeException("cannot add set");
        //return null;
    }
    
    
    @Override
    public func sub(double d)
    {
        Set s=new Set();

        for(cons c:list){
            s.list.add((cons)c.sub(d));
        }
        return s;
    }
    
    @Override
    public func sub(func f)
    {
        if(f instanceof Set){
            Set sf=(Set)f;
            Set ns=new Set();
            for(cons c1:list){
                for(cons c2:sf.list){
                    ns.list.add((cons)c1.sub(c2));
                }
            }
            return ns;
        }
        throw new RuntimeException("cannot sub set");
        //return null;
    }
    
    
    @Override
    public func mul(double d)
    {
        Set s=new Set();

        for(cons c:list){
            s.list.add((cons)c.mul(d));
        }
        return s;
    }
    @Override
    public func mul(func f)
    {
        if(f instanceof Set){
            Set sf=(Set)f;
            Set ns=new Set();
            for(cons c1:list){
                for(cons c2:sf.list){
                    ns.list.add((cons)c1.mul(c2));
                }
            }
            return ns;
        }
        throw new RuntimeException("cannot mul set");
        //return null;
    }
    
    
    @Override
    public func get(var[] v, cons[] c)
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        // TODO: Implement this method
        return 0;
    }

    @Override
    public cons evalc(var[] v, double[] d)
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toLatex()
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func derivative(var v)
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func integrate(var v)
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func copy0()
    {
        Set s=new Set();
        s.name=name;
        s.a=a;
        s.list.addAll(list);
        return s;
    }

    @Override
    public String toString2()
    {
        if(isGeneral()){
            return String.format("%s{n}",name);
        }
        if(print){
            return print();
        }
        return String.format("%s{n}",name);
    }
    public String print(){
        StringBuilder s=new StringBuilder();
        s.append(name);
        s.append("{");
        for(int i=0;i<list.size();i++){
            s.append(list.get(i));
            if(i<list.size()-1){
                s.append(",");
            }
        }
        s.append("}");
        return s.toString();
    }

    @Override
    public boolean eq2(func f)
    {
        // TODO: Implement this method
        return false;
    }

    @Override
    public func substitude0(var v, func p)
    {
        // TODO: Implement this method
        return null;
    }
    
    
}
