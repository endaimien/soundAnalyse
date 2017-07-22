package com.example.administrator.soundanalysis.dataclass;

import android.util.Log;

/**
 * Created by Administrator on 2017/4/23 0023.
 */

public class FftFunction {
    private  float[] mx;
    private  float[] my;


    public float[] getOrder(float[] buffersize) {//倒序算法——雷德算法
        // TODO code application logic here
        int i = 0, j = 0;
        float t;
        int n = buffersize.length;
        //int[] x = new int[]{11,22,44,33};
        Log.d("gg",String.valueOf(n));
        for (; i < n - 1; i++) {
            if (i < j) {
                t = buffersize[j];
                buffersize[j] = buffersize[i];
                buffersize[i] = t;
            }
            int k = n/2;
            while (k <= j) {
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }
        return buffersize;
    }

    public int up2power(int iint){//取得小于iint的最大2^m
        int res = 1;
        while(res<=iint){
            res=res<<1;
        }
        return res>>1;
    }
    public  void getfft(float[] x,int n){//蝶形运算
        float[] y = new float[n];
        for (int i = 0;i<n;i++){
            y[i]=0;
        }
        int m=1,f=n;
        while((f=f/2)!=1){
            m++;

        }
        //System.out.print(m);
        for(int i=1;i<=m;i++){
            int d = (int)Math.pow(2,m);//同一列蝶形运算两组间距
            int t = d/2;//同一组运算两点间距

         /*  float e  = 3.1415926f/t;
           double cos = Math.cos(e);
           double sin = -Math.sin(e);*/
            for(int j=0;j<t;j++){
                float e  = 3.1415f/t;
                float cos = (float)Math.cos(j*e);
                float sin = (float)Math.sin(j*e)*-1;
                for(int k=j;k<n;k+=d){
                    int r =k+t;
                    float zcos = (float)((float)cos*x[r]-(float)sin*y[r]);
                    float zsin = (float)(cos*y[r]+sin*x[r]);
                    x[r] = x[k]-zcos;
                    x[k] = x[k]+zcos;

                    y[r] = y[k]-zsin;
                    y[k] = y[k]+zsin;

                }
            }

        }
        mx = x;
        my = y;//测试  System.out.print(" "+x[0]+" "+x[1]+" "+x[2]+" "+x[3]);
        }

    public float[] getyh (){//得到幅值；
        int h = mx.length;
        float[] amplitude = new float[h];
        for(int i=0;i<h;i++){
            amplitude[i] = 20*(float) Math.log10( Math.sqrt(mx[i]*mx[i]+my[i]*my[i])+1);
        }
        return amplitude;
    }
}
