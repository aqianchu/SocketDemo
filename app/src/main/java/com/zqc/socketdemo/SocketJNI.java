package com.zqc.socketdemo;

/**
 * Created by zhangqianchu on 2017/8/2.
 */

public class SocketJNI {
    static {
        System.loadLibrary("mysocket");
    }
    public native static void connect(String ip, int port);
}
