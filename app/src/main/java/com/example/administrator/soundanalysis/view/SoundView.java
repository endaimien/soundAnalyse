package com.example.administrator.soundanalysis.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.soundanalysis.R;
import com.example.administrator.soundanalysis.dataclass.MediaData;

/**
 * Created by Administrator on 2017/4/15 0015.
 */

public class SoundView extends ImageView {
    private float mWidth,mHeigth;
    private float mNumwidth,mNumheigth;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private Paint mPaint;
    public SoundView(Context context) {
        super(context);
    }

    public SoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* public SoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView();
        mMatrix.setRotate(getDegrees((new MediaData()).data),mWidth/2,mHeigth/2);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
        canvas.drawText((int)(new MediaData()).data+"DB",mWidth/2-10,mHeigth*36/46,mPaint);
    }
    public void initView(){
        Bitmap _bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noise_index);
        int _bitmapWidth = _bitmap.getWidth();
        int _bitmapHeigth = _bitmap.getHeight();

        mWidth = getWidth();
        mHeigth = getHeight();
        mNumwidth  = mWidth/_bitmapWidth;
        mNumheigth = mHeigth/_bitmapHeigth;
        mMatrix = new Matrix();
        mMatrix.setScale(mNumwidth,mNumheigth);

        mBitmap = Bitmap.createBitmap(_bitmap,0,0,_bitmapWidth,_bitmapHeigth,mMatrix,true);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(20);
    }
    public float getDegrees(float a){
        return 1.8f*a+220;
    }
}

