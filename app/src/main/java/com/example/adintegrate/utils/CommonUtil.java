package com.example.adintegrate.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.adintegrate.bean.Bean;
import com.example.adintegrate.MainActivity;
import com.example.adintegrate.network.Constant;
import com.example.adintegrate.network.ExceptionUtils;
import com.example.adintegrate.network.MyDataCallBack;
import com.example.adintegrate.network.OkHTTPManger;
import com.example.adintegrate.service.RequestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

import okhttp3.Request;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.pm.PackageManager.GET_ACTIVITIES;

/**
 * Created by dell on 2019/11/28 10:26
 * Description:
 * Emain: 1187278976@qq.com
 */
public class CommonUtil {

    private final static String TAG = "CommonUtil";
    private Handler mHandler;
    private MyRunnable mRunnable;
    private String mUrl;
    private Map<String, String> mParams;
    private Context mContext;


    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            int second = 0;
            second = random.nextInt(Constant.MAX) % (Constant.MAX - Constant.MIN + 1) + Constant.MIN;
            second = second * Constant.MILLISECOND;
            Log.i("TAG", "onStartCommand" + second);
            new CommonUtil().Request(mUrl, mParams, mContext);
            mHandler.postDelayed(this, second);
        }
    }


    public void executeRequest() {
        mHandler = new Handler();
        if (mRunnable == null) {
            mRunnable = new MyRunnable();
            mHandler.post(mRunnable);
        }
    }

    public void Request(String url, Map<String, String> params, final Context context) {
        mUrl = url;
        mParams = params;
        mContext = context;
        OkHTTPManger.getInstance().postAsynRequireJson(mUrl, params, new MyDataCallBack<Bean>() {
            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void requestSuccess(Bean result) {
                Intent mMyServiceIntent = new Intent(context, RequestService.class);
                context.startService(mMyServiceIntent);
                Intent mMainActivityIntent = new Intent(context, MainActivity.class);
                mMainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mMainActivityIntent.setAction("android.intent.action.MAIN");
                mMainActivityIntent.addCategory("android.intent.category.LAUNCHER");
                mMainActivityIntent.putExtra(Constant.FLAG, result.getData());
                context.startActivity(mMainActivityIntent);
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ExceptionUtils.captureException(context, e);
            }

            @Override
            public void onAfter() {

            }
        });
    }

    public static String getIMEI(Context context) {
        String imei = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "需要动态获取权限", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(context, "不需要动态获取权限", Toast.LENGTH_SHORT);
                TelephonyManager mTM = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                imei = mTM.getDeviceId();
            }
        } else {
            TelephonyManager mTM = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            imei = mTM.getDeviceId();
        }
        return imei;
    }

    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getUid(Context mContext ) {
        try {
            PackageManager pm = mContext.getPackageManager();
            @SuppressLint("WrongConstant") ApplicationInfo ai = pm.getApplicationInfo(mContext.getPackageName(), GET_ACTIVITIES);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void getTimer(final Activity activity, int end){
       new CountDownTimer(end*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                activity.finish();
            }
        }.start();
    }
    private static String is2String(InputStream is) {
        String response = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            response = stringBuilder.toString().trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "kB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


}
