package math;

import math.core.*;
import java.util.*;
import ar.com.hjg.pngj.*;
import java.io.*;

public class graph
{
    func f;
    double x1,x2;
    List<point> list=new ArrayList<>();
    
    public graph(func f,double d1,double  d2){
        this.f=f;
        x1=d1;
        x2=d2;
    }
    
    public void calc() throws FileNotFoundException, IOException{
        double min=0,max=0;
        double val;
        for(double i=x1;i<=x2;i+=0.1){
            val=f.eval(i);
            if(val>max){
                max=val;
            }
            if(val<min){
                min=val;
            }
            list.add(new point(i,val));
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageInfo ii=new ImageInfo(1000,800,8,false);
        PngWriter pw=new PngWriter(baos,ii);
        //pw.setCompLevel(6);
        ImageLineInt line = new ImageLineInt(ii);
        
        Random r=new Random();
        for(int i=0;i<list.size();i++){
            point p=list.get(i);
            
        }
        for(int j=0;j<ii.rows;j++){
            for (int i = 0; i < ii.cols; i++) {//x axis
                ImageLineHelper.setPixelRGB8(line,i,0,0,0);
            }
            for(int i=0;i<ii.rows;i++){
                //ImageLineHelper.setPixelRGB8(line,ii);
            }
            pw.writeRow(line,j);
        }
        
        
        
        pw.end();
        FileOutputStream fos=new FileOutputStream("/storage/emulated/0/AppProjects/math/img.png");
        baos.writeTo(fos);
        fos.close();
    }
}
class point{
    double x,y;
    public point(double d1,double d2){
        x=d1;
        y=d2;
    }
}
