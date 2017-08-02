package com.zqc.socketdemo;

import android.content.Context;

/**
 * Created by zhangqianchu on 2017/8/2.
 */

public class SocketJNI {
    static {
        System.loadLibrary("mysocket");
    }
    public native static void connect(Context mContext, String ip, int port);
}
