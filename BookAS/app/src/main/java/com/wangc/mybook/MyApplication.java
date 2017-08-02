package com.wangc.mybook;


import android.app.Application;

import tools.MyCrashHandler;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //打开错误日志，保存到sd卡
        MyCrashHandler myCrashHandler = MyCrashHandler.getInstance();
        myCrashHandler.init(getApplicationContext());
    }
}