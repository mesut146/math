package math.prime;

import math.core.*;
import java.util.*;

public class pset extends set
{

    //public List<Integer> list=new ArrayList<>();
    int n;
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
    
    void fill(){
        int[] arr=new int[n/2];
        list.add(new cons(2));
        for(int i=3,j=0;i<=n;i+=2){
            arr[j++]=i;
        }
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=0)
                list.add(new cons(arr[i]));
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]!=0&&arr[j]%arr[i]==0){
                    arr[j]=0;
                }
            }
        }
    }

}
