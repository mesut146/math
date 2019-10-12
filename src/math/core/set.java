package math.core;
import math.core.*;
import java.util.*;

public class set extends func
{

    @Override
    public func getReal()
    {
        // TODO: Implement this method
        return null;
    }

    @Override
    public func getImaginary()
    {
        // TODO: Implement this method
        return null;
    }


    public String name="c";
    public int start,end;
    public var v=var.n;
    public List<cons> list=new ArrayList<>();
    //List<func> list=new ArrayList<>();
    public static boolean print=true;

    public set(func cn)
    {
        a = cn;
        name = "c";
    }
    public set(func cn, var v)
    {
        a = cn;
        this.v = v;
        name = "c";
    }
    public set(int...l)
    {
        name = "c";
        start = 1;
        end = l.length;
        for (int i:l)
        {
            list.add(new cons(i));
        }
    }
    
    public int len(){
        if(!isGeneral()){
            return end-start+1;
        }
        return 0;
    }
    public void put(cons c){
        list.add(c);
    }

    //is the Set has general term cn
    public boolean isGeneral()
    {
        return a != null;
    }

    public set sort()
    {
        Comparator<cons> cmp;
        cmp = new Comparator<cons>(){
            @Override
            public int compare(cons p1, cons p2)
            {
                return Double.compare(p1.val, p2.val);
            }
        };
        /*cmp = new Comparator<func>(){
            @Override
            public int compare(func p1, func p2)
            {
                return 0;
            }

        };*/
        Collections.sort(list, cmp);
        return this;
    }

    @Override
    public void vars0(Set<var> vars)
    {
        if(isGeneral()){
            a.vars0(vars);
        }
    }
    
    @Override
    public func add(double d)
    {
        set s=new set();

        for (cons c:list)
        {
            s.list.add((cons)c.add(d));
        }
        return s;
    }

    //linear add {1,2,3}+{4,5}={5,7,3}
    @Override
    public func add(func f)
    {
        if (f instanceof set)
        {
            set sf=(set)f;
            set ns=new set();
            //1,2  6,7,8
            //7,9,8
            for (int i=0;i<Math.max(len(),sf.len());i++)
            {
                if(i<sf.len()){
                    ns.put((cons)list.get(i).add(sf.list.get(i)));
                }else{
                    ns.put(list.get(i));
                }
                
            }
            if(sf.len()>len()){
                for(int i=len();i<sf.len();i++){
                    ns.put(sf.get(i));
                }
            }
            return ns;
        }
        throw new RuntimeException("cannot add set");
        //return null;
    }
    
    //cross add {1,2,3}+{4,5}={5,6,6,7,7,8}
    public func addx(func f)
    {
        if (f instanceof set)
        {
            set sf=(set)f;
            set ns=new set();
            for (cons c1:list)
            {
                for (cons c2:sf.list)
                {
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
        set s=new set();

        for (cons c:list)
        {
            s.list.add((cons)c.sub(d));
        }
        return s;
    }

    @Override
    public func sub(func f)
    {
        if (f instanceof set)
        {
            set sf=(set)f;
            set ns=new set();
            for (cons c1:list)
            {
                for (cons c2:sf.list)
                {
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
        set s=new set();

        for (cons c:list)
        {
            s.list.add((cons)c.mul(d));
        }
        return s;
    }
    
    //linear mul {1,2,3}*{4,5}={4,10,0}
    @Override
    public func mul(func f)
    {
        if (f instanceof set)
        {
            set sf=(set)f;
            set ns=new set();
            
            for (int i=0;i<Math.min(len(),sf.len());i++)
            {
                if(i<sf.len()){
                    ns.put((cons)list.get(i).add(sf.list.get(i)));
                }else{
                    ns.put(list.get(i));
                }

            }
            if(sf.len()>len()){
                for(int i=len();i<sf.len();i++){
                    ns.put(sf.get(i));
                }
            }
            return ns;
        }
        throw new RuntimeException("cannot add set");
        //return null;
    }
    
    public func mulx(func f)
    {
        if (f instanceof set)
        {
            set sf=(set)f;
            set ns=new set();
            for (cons c1:list)
            {
                for (cons c2:sf.list)
                {
                    ns.list.add((cons)c1.mul(c2));
                }
            }
            return ns;
        }
        throw new RuntimeException("cannot mul set");
        //return null;
    }
    
    @Override
    public func pow(double d)
    {
        set s=new set();

        for (cons c:list)
        {
            s.list.add((cons)c.pow(d));
        }
        return s;
    }

    public cons get(int i){
        return list.get(i);
    }

    @Override
    public func get0(var[] v, cons[] c)
    {
        return this;
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
        return toString();
    }

    @Override
    public func derivative(var v)
    {
        if (isGeneral())
        {
            set s=(set) copy();
            s.a = a.derivative(v);
        }
        else
        {
            set s=(set) copy();
            for (func term:list)
            {

            }
        }
        return cons.ZERO;
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
        set s=new set();
        s.name = name;
        s.a = a;
        s.list.addAll(list);
        return s;
    }

    @Override
    public String toString2()
    {
        if (isGeneral())
        {
            return String.format("%s{n}", name);
        }
        if (print)
        {
            return print();
        }
        return String.format("%s{n}", name);
    }
    public String print()
    {
        StringBuilder s=new StringBuilder();
        s.append(name);
        s.append("{");
        for (int i=0;i < list.size();i++)
        {
            s.append(list.get(i));
            if (i < list.size() - 1)
            {
                s.append(",");
            }
        }
        s.append("}");
        return s.toString();
    }

    public String print(int s1, int s2)
    {
        StringBuilder s=new StringBuilder();
        s.append(name);
        s.append("{");
        if (isGeneral())
        {
            for (int i=s1;i <= s2;i++)
            {
                s.append(a.get(v, i));
                if (i < s2)
                {
                    s.append(",");
                }
            }
        }
        else
        {
            for (int i=0;i < list.size();i++)
            {
                s.append(list.get(i));
                if (i < list.size() - 1)
                {
                    s.append(",");
                }
            }
        }

        s.append("}");
        return s.toString();
    }

    @Override
    public boolean eq2(func f)
    {
        return false;
    }

    @Override
    public func substitude0(var v, func p)
    {
        if(isGeneral()){
            a=a.substitude(v,p);
        }
        return this;
    }


}
