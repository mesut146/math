package math.prime;
import java.io.*;
import java.util.*;
import math.*;
import math.core.*;

public class prime extends func
{
    static String path="/storage/emulated/0/AppProjects/math/primes.txt";
    
    public prime(Object o){
        a=Util.cast(o);//a=5,a=x
    }
    
    static{
        
        try
        {
            int lim=1000000;
            File f=new File(path);
            if(!f.exists()||f.length()==0){
                FileOutputStream os=new FileOutputStream(f);
                DataOutputStream dos=new DataOutputStream(os);

                pset p=new pset(lim);
                for(int i=1;i<=p.end;i++){
                    dos.writeInt((int)p.get(i).eval());
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    int read(int n){
        File f=new File(path);
        if(f.exists()&&f.length()>0){
            try
            {
                DataInputStream dis=new DataInputStream(new FileInputStream(f));
                int i=1;//n=2,3
                while(i<n){
                    dis.readInt();
                    i++;
                }
                int p=dis.readInt();
                dis.close();
                return p;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return 0;
    }
    
    @Override
    public func get0(var[] v, cons[] c)
    {
        // TODO: Implement this method
        return this;
    }

    @Override
    public double eval(var[] v, double[] d)
    {
        return read((int)a.eval(v,d));
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
        // TODO: Implement this method
        return new prime(a);
    }

    @Override
    public String toString2()
    {
        // TODO: Implement this method
        return "prime("+a+")";
    }

    @Override
    public boolean eq2(func f)
    {
        // TODO: Implement this method
        return false;
    }

    @Override
    public void vars0(Set<var> vars)
    {
        // TODO: Implement this method
    }

    @Override
    public func substitude0(var v, func p)
    {
        // TODO: Implement this method
        return null;
    }
    
}
