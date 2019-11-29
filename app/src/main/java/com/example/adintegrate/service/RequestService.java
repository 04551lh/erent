package com.example.adintegrate.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.adintegrate.utils.CommonUtil;

import androidx.annotation.Nullable;

/**
 * Created by dell on 2019/11/28 10:27
 * Description:
 * Emain: 1187278976@qq.com
 */
public class RequestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       new CommonUtil().executeRequest();
        return super.onStartCommand(intent, flags, startId);
    }
}
