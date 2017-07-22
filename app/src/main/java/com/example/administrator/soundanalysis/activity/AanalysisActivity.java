package com.example.administrator.soundanalysis.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.administrator.soundanalysis.R;
import com.example.administrator.soundanalysis.dataclass.DataArrayList;
import com.example.administrator.soundanalysis.dataclass.FftFunction;
import com.example.administrator.soundanalysis.dataclass.Sampledata;
import com.example.administrator.soundanalysis.view.AnalysisView;
import com.example.administrator.soundanalysis.view.AnalysisViews;

import java.util.ArrayList;
import java.util.List;

public class AanalysisActivity extends Activity {
     private int mAudioResource = MediaRecorder.AudioSource.MIC;
     private  int mSampleHz = 44100;
     private  int mTimes=5;
     private int mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
    private  int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord mAudioRecord =null;
    private ArrayList<short[]> mInitdata;
    private ArrayList<float[]> mFloatdata;
    private AnalysisViews mAnalysisViews;
    private Button mdButtons;
    private Button mdButtonp;
    private Button mdButtonc;
    private DataArrayList mDataArrayList;
    private int mBufferesize;
    private boolean isFirst=true;
    private Spinner mSpinner;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_aanalysis);
        initView();
        spinneron();
        initListener();
    }

    public void initView(){
        mInitdata = new ArrayList<short[]>();
        mFloatdata = new ArrayList<float[]>();
        mDataArrayList = new DataArrayList();
        mAnalysisViews = (AnalysisViews) findViewById(R.id.analysisView);
        mdButtons = (Button) findViewById(R.id.dbuttons);
        mdButtonp = (Button) findViewById(R.id.dbuttonp);
        mdButtonc = (Button) findViewById(R.id.dbuttonc);
        mSpinner =  (Spinner) findViewById(R.id.spinner_hz);
        mEditText =  (EditText) findViewById(R.id.edittime);
    }

    public void initListener(){
        mdButtons.setOnClickListener(new buttonClickListener());
        mdButtonp.setOnClickListener(new buttonClickListener());
        mdButtonc.setOnClickListener(new buttonClickListener());
    }

    public void spinneron(){
        List<String> _string = new ArrayList<String>();
        _string.add("44100");
        _string.add("4000");
        _string.add("8000");
        _string.add("11025");
        _string.add("22050");
       // _string.add("44100");
        ArrayAdapter _arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,_string);
        //_arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );
        mSpinner.setAdapter(_arrayAdapter);
        mSpinner.setOnItemSelectedListener(new OnselectItemspinner());

    }
    public class OnselectItemspinner implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter _mAdapter  = (ArrayAdapter<String>) parent.getAdapter();
               mSampleHz = Integer.parseInt(_mAdapter.getItem(position).toString());
               Sampledata.mFrequence = mSampleHz;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void getEditText(){
        Log.d("eDIT",mEditText.getText().toString()+"gg");
        if (mEditText.getText().toString().trim().length()==0){
            mTimes = 2;
        }else{
        mTimes = Integer.parseInt(mEditText.getText().toString().trim());}
    }
    public class buttonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.dbuttons:
                    getEditText();
                   new CaculationThread().start();
                      /* while(mAudioRecord!=null){
                                calculation();
                                upAUI();
                        }*/
                    Log.d("ffff","dddddddd");
                    break;
                case R.id.dbuttonp:
                    stopAudioRecoed();
                    break;
                case R.id.dbuttonc:
                    Intent _intent = new Intent(AanalysisActivity.this,NoiseActivity.class);
                    stopAudioRecoed();
                    startActivity(_intent);
                    break;
                default:
                    break;
            }
        }
    }
    public void startRecord(){
        AudioManager _audioReecord = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        _audioReecord.setMicrophoneMute(true);
          mBufferesize = AudioRecord.getMinBufferSize(mSampleHz,
                mChannelConfig,mAudioFormat);//得到最小的缓冲值，读取数组的长度不能超过此值
       // short[] _bufferShort = new short[_bufferesize];
        Log.d("_buffersize",String.valueOf(mBufferesize));
        mAudioRecord = new AudioRecord(mAudioResource,mSampleHz,
                 mChannelConfig,mAudioFormat,mBufferesize);//创建一个AudioRecord

        mAudioRecord.startRecording();}//开始录音

    private void calculation() throws Exception{
            short[] _bufferShort = new short[mBufferesize];
            int _num  = 0;
             while(_num<mSampleHz*mTimes){
             int _result = mAudioRecord.read(_bufferShort,0,mBufferesize);//往_bufferesize中读取数据
         int j=0;
        for(int i=0;i<_result;i++){
            if(_bufferShort[i]==0){
                j++;
            }
        Log.d("_BufferShort",String.valueOf(_bufferShort[i]));}
        Log.d("length",String.valueOf(j));
        synchronized (mDataArrayList){//保存获取的初始数据
                 mDataArrayList.setmArrayLists(_bufferShort);
             }
             _num+=_result;
             }
        //Log.d("_result",String.valueOf(_result));
         short[] _numkeep = new short[_num-3000];
           int kk = 0;
         for(int i =1;i<DataArrayList.mArrayLists.size()&&kk<_num-3000;i++){
             System.arraycopy(DataArrayList.mArrayLists.get(i),0,_numkeep,kk,DataArrayList.mArrayLists.get(i).length);
              kk+=DataArrayList.mArrayLists.get(i).length;
         }
         DataArrayList.mArrayLists.clear();
        FftFunction _fftFun = new FftFunction();//FFT变换函数
          int t = _fftFun.up2power(_num-3000);//得到不超过_result最小的2^m值
        Log.d("t",String.valueOf(t));

          /*short[] _nbufferShort = new short[t];
          System.arraycopy(_bufferShort,750,_nbufferShort,0,t);*/
          float[] _intBufferShort  = new float[t];
          for(int i=0;i<t;i++){
             _intBufferShort[i] = _numkeep[i];
          }
        for(int i=0;i<t;i++){
            Log.d("_intnBufferShort",String.valueOf(_intBufferShort[i]));}
           float[] orderDate = _fftFun.getOrder(_intBufferShort);
        Log.d("order",String.valueOf(orderDate[15]));
          _fftFun.getfft(orderDate,orderDate.length);
          float[] _ordernum = _fftFun.getyh();
        for(int i=0;i<2000;i++){
        Log.d("_ordernum",String.valueOf(_ordernum[i]));}
         /* synchronized (mDataArrayList){*///保存FFT得到的最小值
           DataArrayList.mArrayListf.add(_ordernum);

        /*ArrayList<float[]> _res = DataArrayList.mArrayListf;
        int length = _res.size();
        Log.d("length",String.valueOf(DataArrayList.mArrayListf.size()));
        for(int i=0;i<length;i++){
            float[] _resf = _res.get(i);
        for(int g=0;g<_resf.length;g++){
            Log.d("_res",String.valueOf(_resf[g]));}
        Log.d("_res",String.valueOf(DataArrayList.mArrayListf.size()));*/

    }
    public void stopAudioRecoed(){
        if(mAudioRecord!=null){
            mAudioRecord.stop();
            mAudioRecord = null;
        }
    }

    public void upAUI(){
        Log.d("_res2",String.valueOf(DataArrayList.mArrayListf.size()));
        mAnalysisViews.invalidate();
       // DataArrayList.mArrayLists.clear();
        //DataArrayList.mArrayListf.clear();
        new CaculationThread().start();

        //_handler.postDelayed(_runnable,1000);
    }

    Handler _handler = new Handler();
    Runnable _runnable = new Runnable() {
        @Override
        public void run() {
            upAUI();
        }
    };

    public class CaculationThread extends Thread{
        @Override
        public void run() {
            int m = 3;
            if(mAudioRecord==null){
                startRecord();
                try{calculation();}catch (Exception e){
                    m=3;
                }
               // isFirst = false;
            }else{
                try{calculation();}catch (Exception e){
                    m = 0;
                    DataArrayList.mArrayListf.clear();
                    DataArrayList.mArrayLists.clear();
                }
            }
            Message _message = new Message();
            _message.what = m;
            upHandler.sendMessage(_message);
        }
    }

    Handler upHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 3:
                    upAUI();
                    break;
                case 1:
                    break;
            }
        }
    };
}
