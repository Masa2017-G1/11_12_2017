package com.sheygam.masa_2017_11_12;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener {

    private Button startBtn, stopBtn, startOtherBtn;
    private ProgressBar myProgress;
    private Handler handler;
    private MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        startOtherBtn = findViewById(R.id.start_other_btn);
        myProgress = findViewById(R.id.my_progress);
        handler = new Handler(this);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        startOtherBtn.setOnClickListener(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                Log.d("MY_TAG", "handleMessage: status 1");
                startBtn.setEnabled(false);
                myProgress.setVisibility(View.VISIBLE);
                break;
            case 2:
                Log.d("MY_TAG", "handleMessage: status 2");
                startBtn.setEnabled(true);
                myProgress.setVisibility(View.INVISIBLE);
                break;
            case 3:
                Log.d("MY_TAG", "handleMessage: count: " + msg.arg1);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
           thread = new MyThread(handler);
           thread.start();
        }else if(v.getId() == R.id.stop_btn){
            thread.interrupt();
        }else if(v.getId() == R.id.start_other_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            startOtherBtn.setEnabled(false);
                        }
                    });
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            startOtherBtn.setEnabled(true);
                        }
                    });


                }
            }).start();
        }
    }
}
