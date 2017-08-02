package com.zqc.socketdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity {
    private final String DEBUG_TAG = "hty";
    private TextView mTextView = null;
    private EditText mEditText = null;
    private Button mButton = null;
    Socket socket = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.Button01);
        mTextView = (TextView) findViewById(R.id.TextView01);
        mEditText = (EditText) findViewById(R.id.EditText01);

        //登陆
        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        String message = mEditText.getText().toString();
                        try {
                            //创建Socket
                            socket = new Socket("10.18.73.62", 6868); //IP：10.14.114.127，端口54321
                            //向服务器发送消息
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.println(message);
                            //接收来自服务器的消息
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                            String msg = br.readLine();
                            if (msg != null) {
                                mTextView.setText(msg);
                            } else {
                                mTextView.setText("数据错误!");
                            }
                            //关闭流
                            out.close();
                            br.close();
                        } catch (Exception e) {
                            // TODO: handle exception
                            Log.e(DEBUG_TAG, e.toString());
                        } finally {
                            //关闭Socket
                            try {
                                if (socket != null) {
                                    socket.close();
                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                }.start();
            }
        });
    }
}
