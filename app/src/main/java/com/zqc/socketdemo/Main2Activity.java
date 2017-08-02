package com.zqc.socketdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends Activity implements View.OnClickListener {

    private TextView tv;
    private Button bt;
    public static Main2Activity act;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv = (TextView) findViewById(R.id.textView);
        bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(this);
        act = this;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                new Thread(){
                    @Override
                    public void run() {
                        SocketJNI.connect("10.18.73.62", 6868);
                    }
                }.start();
                break;
        }
    }

    public void updateTv(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(msg)) {
                    tv.setText(msg);
                }
            }
        });
    }
}
