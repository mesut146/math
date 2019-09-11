package math.operator;

import math.core.cons;
import math.core.var;
import math.core.func;

import java.util.*;

public class add extends func
{

    @Override
    public String toLatex()
    {
        StringBuilder sb=new StringBuilder();
        for (int i=0;i < f.size();i++)
        {
            sb.append(f.get(i).toLatex());
            if (i < f.size() - 1)
            {
                if (f.get(i + 1).sign == -1)
                {
                    sb.append("-");
                }
                else
                {
                    sb.append("+");
                }

            }
        }
        return sb.toString();
    }


    public add(func...f1)
    {
		for (func f2:f1)
        {
			f.add(f2);
		}
		type = types.add;
    }

	public add(List<func> f1)
    {
		f = f1;
		type = types.add;
    }

    @Override
    public func get(var[] v, cons[] c)
    {
        func t=cons.ZERO;
		for (func f1:f)
        {
			t = t.add(f1.get(v, c));
		}
		return signto(t);
    }

	@Override
	public double eval(var[] v, double[] d)
	{
		double s=0;
		for (func f1:f)
        {
			s += f1.eval(v, d);
		}
		return sign * s;
	}

    @Override
    public cons evalc(var[] v, double[] d)
    {
        cons sum=cons.ZERO;
        for (func f1:f)
        {
            sum = (cons) sum.add(f1.evalc(v, d));
        }
        return sc(sum);
	}

    @Override
    public func derivative(var v)
    {
        func r=cons.ZERO;
		for (func f1:f)
        {
			r = r.add(f1.derivative(v));
		}
		return r;
    }

    @Override
    public func integrate(var v)
    {
        func r=cons.ZERO;
        for (func f1:f)
        {
            r = r.add(f1.integrate(v));
        }
		return r;
    }

    @Override
    public String toString2()
    {
        return toString();
    }


    @Override
    public String toString()
    {
		StringBuffer sb=new StringBuffer();
        //System.out.println("f="+f+" s="+sign);
		for (int i=0;i < f.size();i++)
        {
            func p=f.get(i);
            if (i == 0 && sign * p.sign == -1)
            {
                sb.append("-");
            }
			sb.append(p.toString2());
			if (i < f.size() - 1)
            {
                int s=f.get(i + 1).sign * sign;
				if (s == 1)
                {
					sb.append("+");
				}
                else if (s == -1)
                {
                    sb.append("-");
                }

			}
		}
		return sb.toString();
    }

    public func simplify()
    {
		List<func> l=getFree();
        //System.out.println(f);
		for (func p:f)
        {
			if (p.isAdd())
            {
                //2+-(x+5)
                for (func inner:p.f)
                {

                    l.add(inner.sign(p.sign));
                }
				//l.addAll(p.f);
			}
            else
            {
				l.add(p);
			}
		}
		set(l);
		cons0();
        if (f.size() == 0)
        {
            return cons.ZERO;
		}
        else if (f.size() == 1)
        {
            return f.get(0);
		}
		//System.out.println("f="+f);
		mu();
        //System.out.println("fmu="+f);
		if (f.size() == 1)
        {
			return signto(f.get(0));
		}
        //System.out.println("f2="+f);
        sort();
        //System.out.println("f3="+this);
        return this;
    }

    void sort()
    {
        int i=find(types.constant);
        if (i != -1 && i != f.size() - 1)
        {
            List<func> l=new LinkedList<func>(f);
            func c=l.remove(i);
            l.add(c);
            set(l);
        }
    }

	public void cons0()
    {
		double c=0;
		List<func> l=getFree();
		//System.out.println("fa="+f);
		for (int i=0;i < f.size();i++)
        {		
			func p=f.get(i);
			if (p.isCons0())
            {
				c += p.eval();
			}
            else
            {
				l.add(p);
			}
		}
		if (c != 0) l.add(new cons(c));
		set(l);
	}

    //2x+3x=5x
	public void mu()
    {
		List<func> l=getFree();
		boolean b[]=new boolean[f.size()];

		for (int i=0;i < f.size();i++)
        {

			if (!b[i])
            {
                func v=f.get(i);
				double k=1;
				for (int j=i + 1;j < f.size();j++)
                {
					holder q=e3(v, f.get(j));
					if (q.b)
                    {
						b[j] = true;
						k += q.d;
						v = q.f;
					}
				}
				func n=v.mul(k);

				//System.out.println("v="+v+" ,k="+k+" ,n="+n+" ,"+n.getType());
				//System.out.printf("v=%s k=%s n=%s\n",v,k,v.mul(k));
				l.add(n);
			}
		}
		set(l);
	}

	@Override
	public boolean eq2(func f1)
	{
		return isEq(f, f1.f);
	}

	public static holder e3(func f1, func f2)
    {
		if (f1.eq(f2)){
            return new holder(f1, 1, null, true);
        }

		if (!f1.isMul())
        {
			f1 = new mul(f1, cons.ONE);
		}
		if (!f2.isMul())
        {
			f2 = new mul(f2, cons.ONE);
		}
		holder o1=rm((mul)f1);
		holder o2=rm((mul)f2);

		if (isEq(o1.l, o2.l))
        {
			return new holder(new mul(o1.l), o1.d + o2.d - 1, null, true);
		}

		return new holder(null, 0, null, false);
	}

	public static holder rm(mul t)
    {
		double d=1;
		List<func> l=getFree();
		for (func p:t.f)
        {
			if (p.isConstant())
            {
				d *= p.eval();
			}
            else
            {
				l.add(p);
			}
		}
        d*=t.sign;
		return new holder(null, d, l, false);
	}

    @Override
    public func substitude0(var v, func p)
    {
        List<func> l=getFree();
        for (func u:f)
        {
            l.add(u.substitude0(v, p));
        }
        return new add(l).simplify();
    }

    @Override
    public func copy0()
    {
        return new add(f);
    }
}