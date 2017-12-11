package com.sheygam.masa_2017_11_12;

import android.os.Handler;
import android.os.Message;

/**
 * Created by gregorysheygam on 11/12/2017.
 */

public class MyThread extends Thread {
    private Handler handler;
    public MyThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        int count = 0;
        handler.sendEmptyMessage(1);
        while(count < 100 && !isInterrupted()) {

//            try {
//                sleep(3000);
                Message msg = handler.obtainMessage(3);
                msg.arg1 = count;
//                handler.sendMessage(msg);
            handler.sendMessageDelayed(msg,1000 * count);
                count++;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                break;
//            }


        }
        handler.sendEmptyMessage(2);
    }
}
