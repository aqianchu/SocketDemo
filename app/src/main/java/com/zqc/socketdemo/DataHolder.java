package com.zqc.socketdemo;

/**
 * Created by zhangqianchu on 2017/8/2.
 */

public class DataHolder {

    public DataHolder(){
    }

    public void putData(String json) {    //Native 回调,显示数据给TextView
        Main2Activity.act.updateTv(json);
    }
}
