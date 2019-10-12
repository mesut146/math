package math.prime;

import java.io.*;
import math.core.*;
import java.nio.*;

public class pset extends set
{

    //public List<Integer> list=new ArrayList<>();
    static String path="/storage/emulated/0/AppProjects/math/primes.txt";
    int n;
    static int lim=10000000;//primes up to lim
    static int[] p;//prime cache
    static int plen;
    
    //up to n
    public pset(int n){
        if(n%2==0){
            n=n-1;
        }
        this.n=n;
        start=1;
        name="p";
        fill();
        end=list.size();
    }
    
    public pset(){
        
    }
    
    public void fill(){
        for(int i=0;i<p.length;i++){
            if(p[i]<=n){
                list.add(new cons(p[i]));
            }else{
                break;
            }
        }
    }
    
    public static int get(int i){
        assert i>=1;
        return p[i];
    }
    
    static void calc(DataOutputStream os) throws IOException{
        int[] arr=new int[lim/2];
        p=new int[lim/2];
        os.writeInt(2);p[0]=2;
        os.writeInt(3);p[1]=3;
        os.writeInt(5);p[2]=5;
        os.writeInt(7);p[3]=7;
        os.writeInt(11);p[4]=11;
        os.writeInt(13);p[5]=13;
        os.writeInt(17);p[6]=17;
        os.writeInt(19);p[7]=19;
        plen=8;
        int len=0;
        for(int i=3;i<=arr.length;i+=2){
            if(i%3!=0&&i%5!=0&&i%7!=0
            &&i%11!=0&&i%13!=0&&i%17!=0&&i%19!=0){
                arr[len++]=i;
                //System.out.println(i);
            }    
        }
        
        for(int i=0;i<len;i++){
            if(arr[i]!=0){
                os.writeInt(arr[i]);
                p[plen++]=arr[i];
                //System.out.println(i);
                for(int k=i+1;k<len;k++){
                    if(arr[k]%arr[i]==0){
                        arr[k]=0;
                    }
                }
            }
            
            //System.out.println(i);
            if(i%(len/10)==0){
                System.out.println(i+"/"+len);
            }
        }
        System.out.println("calc done");
    }
    
    public static void init(){
        try
        {
            File f=new File(path);
            if(!f.exists()||f.length()==0){
                FileOutputStream os=new FileOutputStream(f);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                DataOutputStream dos=new DataOutputStream(baos);

                calc(dos);
                os.write(ByteBuffer.allocate(4).putInt(plen).array());
                baos.writeTo(os);

                dos.close();
                os.close();
            }
            if(p==null){
                FileInputStream fis=new FileInputStream(f);
                DataInputStream dis=new DataInputStream(fis);
                plen=dis.readInt();
                p=new int[plen];

                byte[] buf=new byte[(int)f.length()-4];
                dis.readFully(buf);
                dis.close();
                dis=new DataInputStream(new ByteArrayInputStream(buf));

                for(int i=0;i<plen;i++){
                    p[i]=dis.readInt();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    static{
        init();
    }


}
