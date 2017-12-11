package com.sheygam.masa_2017_11_12;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn, stopBtn;
    private TextView countTxt;
    private ProgressBar myProgress;
    private MyTask task;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        countTxt = findViewById(R.id.count_txt);
        myProgress = findViewById(R.id.my_progress);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
            timer = new Timer();
//            timer.schedule(new MyTimerTask(),1000);//run single
            timer.schedule(new MyTimerTask(),1000,1000);
//            task = new MyTask();
//            task.execute(10, 12,134,47);
//            try {
////                String str = task.get();
//                String str = task.get(3, TimeUnit.SECONDS);
//                Log.d("MY_TAG", "onClick: " + str);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            catch (TimeoutException e) {
//                e.printStackTrace();
//            }
        }else if(v.getId() == R.id.stop_btn){
//            task.cancel(true);
            timer.cancel();
        }
    }

    class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            Log.d("MY_TAG", "run: ");
        }
    }

    class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgress.setVisibility(View.VISIBLE);
            startBtn.setEnabled(false);
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            super.onProgressUpdate(args);
            countTxt.setText(String.valueOf(args[0]));
        }

        @Override
        protected String doInBackground(Integer... params) {
            int size = params[0];
            for(int i = 0; i < size; i++) {
                try {
                    Thread.sleep(500);
                    Log.d("MY_TAG", "doInBackground: i = " + i);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if ((isCancelled())){
                    return "Was canceled";
                }
            }
            return "Im done";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            myProgress.setVisibility(View.INVISIBLE);
            startBtn.setEnabled(true);
            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            onPostExecute(s);
        }
    }
}
