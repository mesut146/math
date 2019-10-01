package math;
import java.util.regex.*;
import java.util.*;

public class matrix
{
    //double[][] arr;
    List<double[]> arr=new ArrayList<>();
    
    public matrix(){
        
    }
    
    //(1,2,3),(5,7,5)
    public matrix(String str){
        for(int i=0;i<str.length();i++){
            char c=str.charAt(i);
            if(c=='('){
                int idx=str.indexOf(')',i+1);
                addRow(str.substring(i+1,idx));
                i=idx;
            }else if(c==','){
                continue;
            }
        }
    }
    
    public void addRow(double[] r){
        arr.add(r);
    }
    
    public void addRow(String r){
        String[] group=r.split(",");
        double[] row=new double[group.length];
        for(int j=0;j<group.length;j++){
            row[j]=Double.parseDouble(group[j]);
        }
        arr.add(row);
    }

    @Override
    public String toString()
    {
        return toString("\n");
    }
  
    public String toString(String del)
    {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<arr.size();i++){
            double[] r=arr.get(i);
            s.append("(");
            for(int j=0;j<r.length;j++){
                if((int)r[j]==r[j]){
                    s.append((int)r[j]);
                }else{
                    s.append(r[j]);
                }
                
                if(j<r.length-1){
                    s.append(",");
                }
            }
            s.append(")");
            if(i<arr.size()-1){
                s.append(del);
            }
        }
        return s.toString();
    }
    
    public int row(){
        return arr.size();
    }
    public int col(){
        if(arr.size()>0){
            return arr.get(0).length;
        }
        return 0;
    }
    
    public double get(int i,int j){
        return arr.get(i)[j];
    }
    
    public matrix add(double d){
        matrix m=new matrix();
        for(double[] r:arr){
            double[] nr=Arrays.copyOf(r,r.length);
            for(int i=0;i<r.length;i++){
                nr[i]+=d;
            }
            m.addRow(nr);
        }  
        return m;
    }
    
    public matrix add(matrix m){
        assert row()==m.row()&&col()==m.col();
        matrix nm=new matrix();
        int ri=0;
        for(double[] r:arr){
            double[] nr=Arrays.copyOf(r,r.length);
            for(int i=0;i<r.length;i++){
                nr[i]+=m.get(ri,i);
            }
            ri++;
            nm.addRow(nr);
        }  
        return nm;
    }
    
    public matrix sub(double d){
        matrix m=new matrix();
        for(double[] r:arr){
            double[] nr=Arrays.copyOf(r,r.length);
            for(int i=0;i<r.length;i++){
                nr[i]-=d;
            }
            m.addRow(nr);
        }  
        return m;
    }

    public matrix sub(matrix m){
        assert row()==m.row()&&col()==m.col();
        matrix nm=new matrix();
        int ri=0;
        for(double[] r:arr){
            double[] nr=Arrays.copyOf(r,r.length);
            for(int i=0;i<r.length;i++){
                nr[i]-=m.get(ri,i);
            }
            ri++;
            nm.addRow(nr);
        }  
        return nm;
    }
    
    public matrix mul(double d){
        matrix m=new matrix();
        for(double[] r:arr){
            double[] nr=Arrays.copyOf(r,r.length);
            for(int i=0;i<r.length;i++){
                nr[i]*=d;
            }
            m.addRow(nr);
        }  
        return m;
    }

    public matrix mul(matrix m){
        //(a,b,c),(d,e,f) 2x3
        //(x,y),(z,t),(p,m) 3x2
        //2x2
        assert col()==m.row();
        matrix nm=new matrix();
        int ri=0;
        for(double[] r:arr){
            double[] nr=new double[m.col()];
            for(int i=0;i<r.length;i++){
                nr[i]+=m.get(ri,i);
            }
            ri++;
            nm.addRow(nr);
        }  
        return nm;
    }
}
