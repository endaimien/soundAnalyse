package com.example.administrator.soundanalysis.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.soundanalysis.R;
import com.example.administrator.soundanalysis.dataclass.MediaData;
import com.example.administrator.soundanalysis.view.AnalysisView;
import com.example.administrator.soundanalysis.view.SoundView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NoiseActivity extends Activity {
     public  MediaRecorder mediaRecorder;
     public  Button mButtons;
     public  Button mButtonp;
     public  Button mButtonc;
     private SoundView mImageView;
     private ArrayList mArrayList;
     private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_noise);
        initView();
        buttonCheck();
        //updateUI();
    }

    public void initView(){ //初始化view
        mButtons = (Button) findViewById(R.id.buttons);
        mButtonp = (Button) findViewById(R.id.buttonp);
        mButtonc = (Button) findViewById(R.id.buttonc);
        mTextView = (TextView) findViewById(R.id.messageText);
        mImageView =(SoundView)  findViewById(R.id.imaNoise_disc);
        mArrayList = new ArrayList();
}
    public void buttonCheck(){
        mButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{startRecord();}catch (Exception E){
                   E.printStackTrace();
                   Log.d("ffd","dddd");
                   updateUI();
                   Log.d("ffd","dddd");
               }
            }
        });
        mButtonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
        mButtonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(NoiseActivity.this, AanalysisActivity.class);
                stopRecord();
                startActivity(_intent);
            }
        });
    }
    public void startRecord() {//开始录音
        /*String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){

        }*/
        if (mediaRecorder == null) {
            File soundFile = new File(Environment.getExternalStorageDirectory(), "sound");
            //创建文件夹
            Log.d("dd",soundFile.getAbsolutePath());
            if (!soundFile.exists()) {
                soundFile.mkdirs();//如果不存在文件夹，则创建文件夹及其所有父目录
            }
            File recordFile = new File(soundFile, System.currentTimeMillis() + ".amr");
            Log.d("dd1",recordFile.getAbsolutePath());
            if (!recordFile.exists()) {
                try {
                    recordFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    ;
                }
            }

            mediaRecorder = new MediaRecorder();
            try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

                mediaRecorder.prepare();
                mediaRecorder.start();
                Log.d("NUM","SDDDDF");
                updateUI();
                Log.d("NUM","SDDDDF");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRecord(){
        if(mediaRecorder!=null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public  void updateUI(){
        if(mediaRecorder!=null){
        double time = mediaRecorder.getMaxAmplitude();
        Log.d("F","JJ");
        double baseDB = 0;
            if(time>1) {
                double numdb = 20 * Math.log10(time);
                mArrayList.add(numdb);
                Log.d("media", "分贝值" + numdb);
                //进行UI操作
                MediaData.data = (float) numdb;
                  mImageView.invalidate();
               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                       *//* double time = mediaRecorder.getMaxAmplitude();
                        //double baseDB = 0;
                        if(time>1){
                            double numdb = 20*Math.log10(time);
                            Log.d("media","分贝值"+numdb);*//*
                        mHandler.postDelayed(uiRunnable, 100);
                    }
                }).start();*/
               // mHandler.postDelayed(uiRunnable, 100);
            }
        mHandler.postDelayed(uiRunnable, 100);}
        }

    Handler  mHandler = new Handler();
    Runnable  uiRunnable = new Runnable() {
        @Override
        public void run() {
            if(mArrayList.size()==100){
                double sum = 0;
                for(int i =0;i<100;i++){
                    sum=sum+(double)mArrayList.get(i);
                }
                if(sum/100<50){
                    mTextView.setText(R.string.normal);
                }else if(50<=sum/100&&sum/100<70){
                    mTextView.setTextColor(Color.YELLOW);
                    mTextView.setText(R.string.inormal1);
                }else if(70<=sum/100&&sum/100<90){
                    mTextView.setTextColor(Color.BLUE);
                    mTextView.setText(R.string.inormal2);
                }else if(sum/100>=90){
                    mTextView.setTextColor(Color.RED);
                    mTextView.setText(R.string.inormal3);
                }
                mArrayList.clear();
            }
            updateUI();
        }
    };


}
