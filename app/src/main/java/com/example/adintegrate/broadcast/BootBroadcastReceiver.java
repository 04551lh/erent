package com.example.adintegrate.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.adintegrate.MainActivity;
import com.example.adintegrate.network.Constant;
import com.example.adintegrate.service.RequestService;
import com.example.adintegrate.utils.CommonUtil;
import com.example.adintegrate.utils.NetWorkUtils;
import com.example.adintegrate.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2019/11/28 10:18
 * Description:
 * Emain: 1187278976@qq.com
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    private Map<String,String> mParams;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG","机显示的界面"+ CommonUtil.getIMEI(context));
        if (intent.getAction().equals(ACTION)) {
            mParams = new HashMap<>();
            mParams.put("cid",CommonUtil.getIMEI(context));
            mParams.put("ip",NetWorkUtils.getIPAddress(context));
//            if(NetWorkUtils.checkNet(context)) new CommonUtil().Request(Constant.URL,mParams,context);
             new CommonUtil().Request(Constant.URL,mParams,context);
//            else ToastUtil.showShort(context,"网络不可用");
            //请求
//            Intent mMyServiceIntent = new Intent(context, RequestService.class);
//            context.startService(mMyServiceIntent);
//            Intent mMainActivityIntent = new Intent(context, MainActivity.class);
//            mMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mMainActivityIntent.setAction("android.intent.action.MAIN");
//            mMainActivityIntent.addCategory("android.intent.category.LAUNCHER");
//            context.startActivity(mMainActivityIntent);
//            HelperUtil.setParams(OkHttpHelper.getHttpHelper().setParams(mParams));
//            HelperUtil.executeRequest(HelperUtil.get(300,900));
        }
    }

}
