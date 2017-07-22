package com.example.administrator.soundanalysis.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.soundanalysis.dataclass.DataArrayList;
import com.example.administrator.soundanalysis.dataclass.Sampledata;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class AnalysisViews extends View {
    private  Paint mPaint;
    private  Paint mPaint1;
    private  Paint mPaint2;
    private  Paint mPaint3;
    private  int mBaseLine=30;
    private  int mWidth;
    private  int mHeigth;
    private  int mFrequence=44100;
    private DataArrayList mDataArrayList;
    private ArrayList<float[]> mArrayList;

    public AnalysisViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnalysisViews(Context context) {
        super(context);
    }

    public AnalysisViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* mWidth =getWidth();
        mHeigth = getHeight();*/
        init();
        String d= String.valueOf(mWidth);
        String d1=  String.valueOf(mHeigth);
        Log.d("hegth",d1);
        Log.d("width",d);
        canvas.drawLine(mBaseLine,mHeigth-mBaseLine,mWidth,mHeigth-mBaseLine,mPaint);
        canvas.drawLine(mBaseLine,mBaseLine,mBaseLine,mHeigth-mBaseLine,mPaint);
        canvas.drawText("原点（0,0）",mBaseLine,mHeigth,mPaint1);
        canvas.drawText("幅值",0,mBaseLine+10,mPaint1);
        Log.d("tt","gggggg");
        canvas.drawText("频率",mWidth-mBaseLine-30,mHeigth,mPaint1);

        int  ver = mWidth-mBaseLine;
        //int  length = pFftnum.length;
        for(int i=1;i<4;i++){
            int lineplace = ver*i/4;
            String textPlace = String.valueOf(mFrequence*i/4);
            canvas.drawText(textPlace,lineplace,mHeigth,mPaint1);
        }
        for(int i=0;i<4;i++){
            int _heigth = mHeigth*(i+1)/4;
            String _textHeigth = String.valueOf(160*(i+1)/4);
            canvas.drawText(_textHeigth,0,mHeigth-_heigth,mPaint1);
            canvas.drawLine(0,mHeigth-_heigth,mWidth,mHeigth-_heigth,mPaint2);
        }

        ArrayList<float[]> _res = DataArrayList.mArrayListf;
        int length = _res.size();
        Log.d("length",String.valueOf(DataArrayList.mArrayListf.size()));
        for(int i=0;i<length;i++){
            float[] _resf = _res.get(i);
           // int pFftnum = _resf.length;
           /* for(int g=0;i<_resf.length;i++){
            Log.d("_res",String.valueOf(_resf[g]));}*/
            for(int j=0;j<_resf.length-10;j+=10){
                float xplace1 = j*ver/_resf.length+mBaseLine;
                float yplace1 = mHeigth-_resf[j]-mBaseLine;
                float xplace2 = (j+10)*ver/_resf.length+mBaseLine;
                float yplace2 = mHeigth- _resf[j+10]-mBaseLine;
                canvas.drawLine(xplace1,yplace1,xplace2,yplace2,mPaint3);

            }
        }
         DataArrayList.mArrayListf.clear();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(30);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLACK);
        mPaint1.setTextSize(30);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.GRAY);

        mPaint3 = new Paint();
        mPaint3.setColor(Color.GREEN);

        mDataArrayList = new DataArrayList();
        mWidth =getWidth();
        mHeigth = getHeight();
        mFrequence = Sampledata.mFrequence;
    }
}