package com.example.administrator.soundanalysis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.administrator.soundanalysis.dataclass.DataArrayList;

import java.util.ArrayList;

import static com.example.administrator.soundanalysis.dataclass.DataArrayList.mArrayListf;

/**
 * Created by Administrator on 2017/4/23 0023.
 */

public class AnalysisView extends SurfaceView implements SurfaceHolder.Callback {
    private  Paint mPaint;
    private  int mBaseLine=5;
    private  int mWidth;
    private  int mHeigth;
    private  int mFrequence;
    private DataArrayList mDataArrayList;
    public  SurfaceHolder mHolder;
    private  ArrayList<float[]> mArrayList;
    private void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setTextSize(30);
        mPaint.setColor(Color.WHITE);
        mDataArrayList = new DataArrayList();
        /*mWidth = getWidth();
        mHeigth = getHeight();*/
        /*String d= String.valueOf(mWidth);
        Log.d("width1",d);*/

        //doDraw(c);
        Log.d("ee","hhhhhh");
    }

    public AnalysisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /*public AnalysisView(Context context) {
        super(context);
        *//*mFrequence = pFrequence;
        mArrayList = pArrayList;*//*
        init();
    }*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Log.d("ddd","jjj");
        /*Canvas c =new Canvas();
            doDraw(c);*/
       new Thread( new playThread()).start();
        Log.d("ssss","kkkkk");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
       /* mWidth = getWidth();
        mHeigth = getHeight();
        String d= String.valueOf(mWidth);
        Log.d("width",d);*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    class playThread extends Thread{
        Context mContext;
        SurfaceView pSurfaceView;
       /* Handler _handler = new Handler();
        Runnable _runnable = new Runnable() {
            @Override
            public void run() {
                run();
            }
        };*/
        @Override
        public void run() {
            ArrayList<float[]> a = DataArrayList.mArrayListf;
             int b = a.size();
            Log.d("b",String.valueOf(b));
            for(int i=0;i<b;i++){
                float[] num = a.get(i);
                Canvas c = new Canvas();
                doDraw(c,num);
            }
        }
        public void playThread(SurfaceView _surfaceView,Context _context){
              mContext = _context;
              pSurfaceView = _surfaceView;
        }
        /*public void doDraw(Canvas pCanvas,float[] pFftnum){
            pCanvas.drawColor(Color.BLACK);
            pCanvas.drawLine(mBaseLine,mBaseLine,mWidth,mBaseLine,mPaint);
            pCanvas.drawLine(mBaseLine,mBaseLine,mBaseLine,mHeigth,mPaint);
            pCanvas.drawText("原点（0,0）",mBaseLine,mBaseLine,mPaint);
            pCanvas.drawText("幅值",mBaseLine,mHeigth,mPaint);
            pCanvas.drawText("频率",mWidth,mBaseLine,mPaint);
            int  ver = mWidth-mBaseLine;
            int  length = pFftnum.length;
            for(int i=1;i<4;i++){
                int lineplace = ver*i/4;
                String textPlace = String.valueOf(mFrequence*i/4);
                        pCanvas.drawText(textPlace,lineplace,mBaseLine,mPaint);
            }
            for(int i=0;i<pFftnum.length-1;i++){
                int xplace1 = i*mFrequence/length;
                int yplace1 = (int) pFftnum[i];
                int xplace2 = (i+1)*mFrequence/length;
                int yplace2 = (int) pFftnum[i+1];
                pCanvas.drawLine(xplace1,yplace1,xplace2,yplace2,mPaint);

            }
        }*/
    }
    public void doDraw(Canvas pCanvas,float[] pNum){
        //pCanvas.drawColor(Color.BLACK);
        pCanvas = mHolder.lockCanvas();
        mWidth =getWidth();
        mHeigth = getHeight();
        String d= String.valueOf(mWidth);
        String d1=  String.valueOf(mHeigth);
        Log.d("hegth",d1);
        Log.d("width",d);
        pCanvas.drawLine(mBaseLine,mHeigth-mBaseLine,mWidth,mHeigth-mBaseLine,mPaint);
        pCanvas.drawLine(mBaseLine,mBaseLine,mBaseLine,mHeigth-mBaseLine,mPaint);
        pCanvas.drawText("原点（0,0）",mBaseLine,mHeigth,mPaint);
        pCanvas.drawText("幅值",0,mBaseLine,mPaint);
        Log.d("tt","gggggg");
        pCanvas.drawText("频率",mWidth-mBaseLine,mHeigth,mPaint);
        mHolder.unlockCanvasAndPost(pCanvas);
        /*int  ver = mWidth-mBaseLine;
        int  length = pFftnum.length;
        for(int i=1;i<4;i++){
            int lineplace = ver*i/4;
            String textPlace = String.valueOf(mFrequence*i/4);
            pCanvas.drawText(textPlace,lineplace,mBaseLine,mPaint);
        }
        for(int i=0;i<pFftnum.length-1;i++){
            int xplace1 = i*mFrequence/length;
            int yplace1 = (int) pFftnum[i];
            int xplace2 = (i+1)*mFrequence/length;
            int yplace2 = (int) pFftnum[i+1];
            pCanvas.drawLine(xplace1,yplace1,xplace2,yplace2,mPaint);

        }*/
    }

}
