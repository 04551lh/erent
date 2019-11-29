package com.example.adintegrate.network;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.util.MalformedJsonException;

import com.example.adintegrate.utils.ToastUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by dell on 2019/11/27 20:55
 * Description:
 * Emain: 1187278976@qq.com
 */
public class ExceptionUtils {

    private final static String TAG = "ExceptionUtils";

    public static void captureException(Context context, Exception e) {
        Log.e(TAG, "自定义异常捕获：" + e.toString());
        if (e != null) {
            if (e instanceof UnknownHostException || e instanceof ConnectException) {
                ToastUtil.showShort(context, "网络不可用！请检查网络连接！");
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException || e instanceof MalformedJsonException) {
                ToastUtil.showShort(context, "请求错误！");
            } else if (e instanceof SocketTimeoutException) {
                ToastUtil.showShort(context, "网络连接超时！");
            } else if (e instanceof SocketException) {
                ToastUtil.showShort(context, "连接服务器失败！请稍后再试！");
            } else if (e instanceof IOException) {
                ToastUtil.showShort(context, "IOException连接服务器失败！请稍后再试！");
            } else {
                switch (Integer.parseInt(e.getMessage())) {
                    case 0:
//                        ToastUtil.showShort(context, "IOException连接服务器失败！未知错误！");
                        break;
                    case 1:
                        ToastUtil.showShort(context, "流量超限！");
                        break;
                    case 2:
                        ToastUtil.showShort(context, "服务端异常！");
                        break;
                    default:
                        ToastUtil.showShort(context, "IOException连接服务器失败！未知错误！");
                        break;
                }
            }
        }
    }
}
