package com.example.adintegrate;

import android.app.Application;

import com.example.adintegrate.utils.CrashHandler;

/**
 * Created by dell on 2019/11/27 20:49
 * Description:
 * Emain: 1187278976@qq.com
 */
public class MyApplication extends Application {

    private volatile static MyApplication instance=null;//防止多个线程同时访问

    public static MyApplication getInstance() {
        if (instance==null){
            synchronized (MyApplication.class){
                if (instance==null){
                    instance=new MyApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance(this);
    }
}
